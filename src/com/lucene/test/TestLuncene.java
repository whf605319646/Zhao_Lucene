package com.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class TestLuncene {
	/**
	 * 使用IndexWriter 创建索引
	 * @throws IOException
	 */
	@Test
	public void testCreateIndex() throws IOException {
		//索引存放的位置
		Directory directory=FSDirectory.open(new File("indexDir/"));
		//lucene当前匹配的版本
		Version matchVersion=Version.LUCENE_44;
		//分词器
		Analyzer analyzer=new StandardAnalyzer(matchVersion);
		//索引写入的配置
		IndexWriterConfig indexWriterConfig=new IndexWriterConfig(matchVersion, analyzer);
		//构建操作索引的类
		IndexWriter indexWriter=new IndexWriter(directory, indexWriterConfig);
		//索引库里面的要遵守一定的结构，
		Document document=new Document();
		/**
		 * arg0:字段的名称
		 * arg1:字段的值
		 * arg3:该字段是否在索引中存储
		 */
		IndexableField field=new IntField("id", 1, Store.YES);
		IndexableField title=new StringField("title", "Hello", Store.YES);
		IndexableField content=new TextField("content", "woshishisij", Store.YES);
		document.add(field);
		document.add(title);
		document.add(content);
		
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	/**
	 * 使用IndexSearcher进行搜索
	 * @throws IOException
	 */
	@Test
	public void testIndexSeatcher() throws IOException {
		//索引存放的位置
		Directory directory=FSDirectory.open(new File("indexDir/"));
		IndexReader r=DirectoryReader.open(directory);
		//通过indexSearcher 去检索索引目录
		IndexSearcher indexSearcher=new IndexSearcher(r);
		//query是一个搜索条件..，通过定义条件来进行查找...
		//term 我需要根据那个字段进行检索，字段对应的值...
		Query query= new TermQuery(new Term("title", "Hello"));
		//搜索先搜索索引目录..
		//找到符合query 条件的前面N条记录...
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println(topDocs.totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;
			Document doc=indexSearcher.doc(docID);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("title"));
			System.out.println(doc.get("content"));
		}
	}
}
