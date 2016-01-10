package com.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;

import com.lucene.entity.Article;

public class Entity2Document {

	public static Document entity2Document(Article article) {
		Document document = new Document();
		
		IndexableField idField = new IntField("id", article.getId(), Store.YES);
		IndexableField titleField = new StringField("title", article.getTitle(), Store.YES);
		IndexableField authorField = new StringField("author", article.getAuthor(), Store.YES);
		IndexableField linkField = new StringField("link", article.getLink(), Store.YES);
		IndexableField contentField = new TextField("content", article.getContent(), Store.YES);

		document.add(idField);
		document.add(titleField);
		document.add(authorField);
		document.add(linkField);
		document.add(contentField);

		return document;
	}
}
