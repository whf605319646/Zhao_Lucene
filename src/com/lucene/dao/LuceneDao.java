package com.lucene.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.lucene.entity.Article;
import com.lucene.util.Entity2Document;
import com.lucene.util.LuceneUtils;

public class LuceneDao {

	public void addIndex(Article article) throws IOException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		Document doc=Entity2Document.entity2Document(article);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}
	public void updateIndex(String fld,String text,Article article) throws IOException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		Term term=new Term(fld, text);
		Document doc = Entity2Document.entity2Document(article);
		indexWriter.updateDocument(term, doc);
		indexWriter.close();
	}
	public void delIndex(String fld,String text) throws IOException{
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		Term term=new Term(fld, text);
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}
	public List<Article> queryIndex(String keywprd,int start,int rows) throws Exception{
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		String fields[]={"title","content"};
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(), fields, LuceneUtils.getAnalyzer());
		Query query = queryParser.parse(keywprd);
		TopDocs topDocs = indexSearcher.search(query, start+rows);
		int totalHits = topDocs.totalHits;
		System.out.println("总记录数==="+totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		Article article=null;
		List<Article> articles=new ArrayList<Article>();
		for(int i=0;i<Math.min(scoreDocs.length, start+rows);i++){
			int docID = scoreDocs[i].doc;
			System.out.println("编号=="+docID);
			Document doc = indexSearcher.doc(docID);
			article=new Article();
			article.setId(Integer.parseInt(doc.get("id")));
			article.setTitle(doc.get("title"));
			article.setAuthor(doc.get("author"));
			article.setLink(doc.get("link"));
			article.setContent(doc.get("content"));
			articles.add(article);
		}
		return articles;
	}
}
