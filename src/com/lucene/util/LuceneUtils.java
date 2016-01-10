package com.lucene.util;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneUtils {

	public static Directory directory = null;
	public static IndexWriterConfig indexWriterConfig = null;

	private static Version matchVersion = null;
	private static Analyzer analyzer = null;

	static {
		try {
			directory = FSDirectory.open(new File(ContantsUtil.PATH));
			matchVersion = Version.LUCENE_44;
			analyzer = new StandardAnalyzer(matchVersion);
			indexWriterConfig = new IndexWriterConfig(matchVersion, analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取IndexWrite
	public static IndexWriter getIndexWriter() throws IOException {
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		return indexWriter;
	}

	// 获取IndexSearcher
	public static IndexSearcher getIndexSearcher() throws IOException {
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}

	public static Version getMatchVersion() {
		return matchVersion;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	
}
