package com.ajn.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * 質問の集合。
 * <p>keyId: 自動採番</p>
 * @author shin1ogawa
 */
@Model(schemaVersion = 1, schemaVersionName = "sv")
public class Discussion implements Serializable {

	private static final long serialVersionUID = 3575741695766630236L;

	@Attribute(primaryKey = true)
	Key key;

	String title;

	Key author;

	String authorName;

	@Attribute(unindexed = true)
	String description;

	Date createdAt;


	/**
	 * @return the key
	 * @category accessor
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 * @category accessor
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the title
	 * @category accessor
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 * @category accessor
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 * @category accessor
	 */
	public Key getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 * @category accessor
	 */
	public void setAuthor(Key author) {
		this.author = author;
	}

	/**
	 * @return the authorName
	 * @category accessor
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 * @category accessor
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the description
	 * @category accessor
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 * @category accessor
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createdAt
	 * @category accessor
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 * @category accessor
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
