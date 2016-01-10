package com.lucene.dao;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import com.lucene.entity.Article;
import com.lucene.util.Entity2Document;
import com.lucene.util.LuceneUtil;

public class LuceneDao {

	public void addIndex(Article article) throws IOException{
		IndexWriter indexWriter=LuceneUtil.getIndexWriter();
		Document doc=Entity2Document.entity2Document(article);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}
	public void updateIndex(){
		
	}
	public void delIndex(){
		
	}
	public void queryIndex(){
		
	}
}
