package com.ajn.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * ユーザによるトピックへの -1.
 * <p>keyParent=key of {@code Account}, keyName=key id of {@code Submission}</p>
 * @author shin1ogawa
 */
@Model(schemaVersion = 1, schemaVersionName = "sv")
public class Minus implements Serializable {

	private static final long serialVersionUID = 6428599931430234446L;

	@Attribute(primaryKey = true)
	Key key;


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
}
