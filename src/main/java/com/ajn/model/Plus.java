package com.ajn.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * ユーザによるトピックへの +1.
 * <p>keyParent=key of {@code Account}, keyName=key id of {@code Submission}</p>
 * @author shin1ogawa
 */
@Model(schemaVersion = 1, schemaVersionName = "sv")
public class Plus implements Serializable {

	private static final long serialVersionUID = 493494327075665757L;

	@Attribute(primaryKey = true)
	Key key;

	Key submission;


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
	 * @param submission the submission to set
	 * @category accessor
	 */
	public void setSubmission(Key submission) {
		this.submission = submission;
	}

	/**
	 * @return the submission
	 * @category accessor
	 */
	public Key getSubmission() {
		return submission;
	}
}
