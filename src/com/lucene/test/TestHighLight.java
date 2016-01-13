package com.lucene.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.lucene.entity.Article;
import com.lucene.util.LuceneUtils;
/**
 * 1.使用lucene自带的高亮器进行关键词高亮，
 * 2.使用js高亮器对返回结果进行高亮
 * 》》使用lucene的高亮器返回的带格式的字符串，使原有的字符串长度边长会占用传输带宽
 * 返回此格式：<font color='red'>我</font>是中国人11
 * 使用JS则对我是中国人11中的我进行高亮即可
 * 
 * @author zhaoguoxin
 *
 */
public class TestHighLight {

	public static void main(String[] args) throws Exception {
		String fields[]={"title","content"};
		QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(),fields,LuceneUtils.getAnalyzer());
		Query query=queryParser.parse("我");
		Formatter formatter=new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Scorer fragmentScorer=new QueryScorer(query);
		Highlighter highlighter=new Highlighter(formatter, fragmentScorer);
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		TopDocs topDocs=indexSearcher.search(query,100);
		Article article=null;
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			Document document=indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			String title = document.get("title");
			System.out.println(title);
			String content = document.get("content");
			System.out.println(content);
			System.out.println(document.get("author"));
			System.out.println(document.get("link"));
			System.out.println("-------------------------------");
			String bestTitle = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "title", title);
			String bestContent = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "content", content);
			System.out.println(bestTitle);//<font color='red'>我</font>是中国人11
			System.out.println(bestContent==null?content:bestContent);//null 如果搜索中无高亮关键字返回null
			/**
			 * <font color='red'>我</font>是中国人11
				衣带渐宽终不悔，为伊消得人憔悴11
			 */
			article=new Article();
			article.setTitle(bestTitle==null?title:bestTitle);
			article.setContent(bestContent==null?content:bestContent);
		}
	}
}
