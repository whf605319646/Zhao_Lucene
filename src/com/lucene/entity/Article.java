package com.lucene.entity;

public class Article {

	private Integer id;
	private String title;
	private String author;
	private String link;
	private String content;

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public Article(Integer id, String title, String author, String link, String content) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.link = link;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
