package com.lucene.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.lucene.util.LuceneUtils;

public class TestScore {
	
	public static void main(String[] args) throws Exception {
		testScore("衣");
	}

	public static void testScore(String keyword) throws Exception {
		IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
		String fields[] = { "content" };
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtils.getMatchVersion(), fields,
				LuceneUtils.getAnalyzer());
		Query query = queryParser.parse(keyword);
		TopDocs topDocs = indexSearcher.search(query, 100);
		int totalHits = topDocs.totalHits;
		System.out.println("总记录数===" + totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			// VSM
			Document document = indexSearcher.doc(scoreDoc.doc);

			System.out.println("文档编号，id" + document.get("id") + "====得分====" + scoreDoc.score);

		}

	}
}
