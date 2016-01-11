package com.lucene.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class TestAnalyzer {
	/**
	 * 
	 * @param args
	 * @throws IOException
	 *             当前使用的分词器：StandardAnalyzer 
	 *             雨 
	 *             纷
	 *             纷
	 *             旧
	 *             故
	 *             里
	 *             草
	 *             木 
	 *             深 
	 *             jay
	 */

	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		String text = "雨纷纷，旧故里草木深。Jay";

		testAnalyzer(analyzer, text);
	}

	public static void testAnalyzer(Analyzer analyzer, String text) throws IOException {
		System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
		tokenStream.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
			System.out.println(new String(charTermAttribute.toString()));
		}
	}
}
