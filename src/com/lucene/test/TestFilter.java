package com.lucene.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

import com.lucene.util.ContantsUtil;
import com.lucene.util.LuceneUtils;

public class TestFilter {

	public static void main(String[] args) throws Exception {
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
		 * 根据id过滤 
		 *  iload 3 [minInclusive]是否包含最小值
     	 *	iload 4 [maxInclusive]是否包含最大值
		 */
		Filter filter=NumericRangeFilter.newIntRange("id", 1, 10, true, false);
		TopDocs topDocs = indexSearcher.search(query, filter, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
		}
	}
}
