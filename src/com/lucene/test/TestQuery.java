package com.lucene.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.lucene.util.LuceneUtils;

public class TestQuery {
	public static void main(String[] args) throws Exception {
		//第一种查询，TermQuery 
		//Query query=new TermQuery(new Term("content","衣带渐宽"));
		
		
		
		//第二种查询：字符串搜索..
		//String fields []={"content"};
		//QueryParser queryParser=new MultiFieldQueryParser(LuceneUtils.getMatchVersion(),fields,LuceneUtils.getAnalyzer());
		//Query query=queryParser.parse("衣带渐宽");
		
		
		//第三种查询：查询所有..
		//Query query=new MatchAllDocsQuery();
		
		//第四种查询：范围查询，可以使用此查询来替代过滤器...
		//.性能比NumericRangeFilter要高
		
		//Query query=NumericRangeQuery.newIntRange("id", 1, 10, true, true);
		
		
		//第五种查询：通配符。。。
		//?代表单个任意字符，* 代表多个任意字符...
		//Query query=new WildcardQuery(new Term("title", "hh*"));
		
		
		//第六种查询：模糊查询..。。。
		//author String 
		/**
		 * 1:需要根据查询的条件
		 * 
		 * 
		 * 2:最大可编辑数  取值范围0,1,2
		 * 允许我的查询条件的值，可以错误几个字符...
		 * 
		 */
		//Query query=new FuzzyQuery(new Term("author", "赵国国"),1);
		/**
		 * 
		 * 第七种查询:
		 * 
		 */
		//短语查询...
		//PhraseQuery query=new PhraseQuery();
		//直接指定角标...
		//query.add(new Term("title","solr"),0);
		//query.add(new Term("title","全"),8);
		//query.add(new Term("title","solr"));
		//query.add(new Term("title","全"));
		//设置两个短语之间的最大间隔数...
		//设置间隔数范围越大，它被匹配的结果就越多，性能也就越慢..
		//query.setSlop(99);
		
		//第八种查询:布尔查询
		BooleanQuery query=new BooleanQuery();
		//id 1~10
		Query query1=NumericRangeQuery.newIntRange("id", 1, 10, true, true);
		
		
		Query query2=NumericRangeQuery.newIntRange("id", 5, 15, true, true);
		
		//select * from table  where title=? or  content=?
		
		
		//必须满足第一个条件...
		query.add(query1, Occur.MUST_NOT);
		
		//query.add(query2, Occur.MUST);
		/**
		 * Occur.MUST Occur.SHOULD Occur.MUST_NOT
		 */
		
		//取交集
		testQuery(query);
	}
	
	public static void testQuery(Query query) throws Exception{
		IndexSearcher indexSearcher=LuceneUtils.getIndexSearcher();
		TopDocs topDocs=indexSearcher.search(query,100);
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			Document document=indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
			System.out.println(document.get("author"));
			System.out.println(document.get("link"));
		}
	}
}
