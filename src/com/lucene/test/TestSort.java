package com.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.lucene.util.ContantsUtil;
import com.lucene.util.LuceneUtils;

public class TestSort {

	public static void main(String[] args) throws IOException, ParseException {
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
		/**
		 * 根据id 排序,字段类型,是否反转
		 */
		SortField field=new SortField("id", Type.INT,true);
		Sort sort=new Sort(field);
		TopDocs topDocs = indexSearcher.search(query, 100, sort);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
		}
	}
}
