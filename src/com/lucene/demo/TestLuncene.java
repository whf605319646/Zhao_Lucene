package com.lucene.demo;

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

	@Test
	public void testCreateIndex() throws IOException {
		//
		Directory directory=FSDirectory.open(new File("indexDir/"));
		
		Version matchVersion=Version.LUCENE_44;
		Analyzer analyzer=new StandardAnalyzer(matchVersion);
		IndexWriterConfig indexWriterConfig=new IndexWriterConfig(matchVersion, analyzer);
		IndexWriter indexWriter=new IndexWriter(directory, indexWriterConfig);
		
		Document document=new Document();
		IndexableField field=new IntField("id", 1, Store.YES);
		IndexableField title=new StringField("title", "Hello", Store.YES);
		IndexableField content=new TextField("content", "woshishisij", Store.YES);
		document.add(field);
		document.add(title);
		document.add(content);
		
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	@Test
	public void testIndexSeatcher() throws IOException {
		Directory directory=FSDirectory.open(new File("indexDir/"));
		IndexReader r=DirectoryReader.open(directory);
		IndexSearcher indexSearcher=new IndexSearcher(r);
		Query query= new TermQuery(new Term("title", "Hello"));
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
