package com.lucene.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.lucene.dao.LuceneDao;
import com.lucene.entity.Article;

public class JunitTest {

	private LuceneDao dao=new LuceneDao();
	@Test
	public void testUpdateIndex() throws IOException{
		String fld="title";
		String text="我是中国人2";
		Article article=new Article(100, "hhhh", "aa", "mkfsg", "aaaaaaaaaaaaaaaaaa");
		dao.updateIndex(fld, text, article);
	}
	@Test
	public void testDelIndex() throws IOException{
		dao.delIndex("content", "衣");
	}
	@Test
	public void testAddIndex() throws IOException{
		Article article=null;
		for(int i=11;i<12;i++){
			article=new Article(i, "我是中国人"+i, "赵国欣"+i, "www.baidu.com"+i, "衣带渐宽终不悔，为伊消得人憔悴"+i);
			dao.addIndex(article);
		}
	}
	@Test
	public void testQueryIndex() throws Exception{
		String keyword="hhhh";
		List<Article> queryIndex = dao.queryIndex(keyword,0,10);
		for (Article article : queryIndex) {
			System.out.println("Id: "+article.getId());
			System.out.println("title:"+article.getTitle());
			System.out.println("author:"+article.getAuthor());
			System.out.println("link:"+article.getLink());
			System.out.println("content:"+article.getContent());
		}
	}
}
