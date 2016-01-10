package com.lucene.test;

import java.io.IOException;

import org.junit.Test;

import com.lucene.dao.LuceneDao;
import com.lucene.entity.Article;

public class JunitTest {

	private LuceneDao dao=new LuceneDao();
	@Test
	public void testAddIndex() throws IOException{
		Article article=new Article(1, "我是中国人", "赵国欣", "www.baidu.com", "衣带渐宽终不悔，为伊消得人憔悴");
		dao.addIndex(article);
	}
}
