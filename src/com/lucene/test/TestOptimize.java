package com.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import com.lucene.util.ContantsUtil;
import com.lucene.util.LuceneUtils;

public class TestOptimize {
	/**
	 * 1.通过IndexWriteConfig
	 * @throws IOException 
	 */
	public void testOptimize1() throws IOException{
		Directory directory=FSDirectory.open(new File(ContantsUtil.PATH));
		IndexWriterConfig config=new IndexWriterConfig(LuceneUtils.getMatchVersion(), LuceneUtils.getAnalyzer());
		LogDocMergePolicy mergePolicy=new LogDocMergePolicy();
		/**
		 * mergeFactor:
		 * 值越小,创建索引时候用的内存越小,创建越慢,搜索越快;
		 * 值越大,创建索引时候用的内存越大,创建越快,搜索越慢.
		 * 2<mergeFactor<10
		 */
		mergePolicy.setMergeFactor(5);
		config.setMergePolicy(mergePolicy);
		IndexWriter indexWriter=new IndexWriter(directory, config);
	}
	/**
	 * 排除停用词，排除停用，被分词器过滤掉，词就不会建立索引，索引文件就会变小，这样搜索的时候就会变快...
	 */
	public void testOptimize2(){
		
	}
	/**
	 * 将索引数据分区存放
	 */
	public void testOptimize3(){
		
	}
	/**
	 * 将索引放到内存中
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void testOptimize4() throws IOException, ParseException{
		/**
		 * 做单例,使索引读入一次
		 */
		Directory dir=FSDirectory.open(new File(ContantsUtil.PATH));
		IOContext ioContext=new IOContext();
		Directory directory=new RAMDirectory(dir, ioContext);
		IndexReader indexReader=DirectoryReader.open(directory);
		IndexSearcher indexSearcher=new IndexSearcher(indexReader);
		String fields[]={"content"};
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(), fields, LuceneUtils.getAnalyzer());
		Query query = queryParser.parse("衣");
		TopDocs topDocs = indexSearcher.search(query,100);
		System.out.println(topDocs.totalHits);
	}
}
