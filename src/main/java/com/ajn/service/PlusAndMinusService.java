package com.ajn.service;

import org.slim3.datastore.Datastore;

import com.ajn.model.Account;
import com.ajn.model.Minus;
import com.ajn.model.Plus;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

/**
 * @author shin1ogawa
 */
public class PlusAndMinusService {

	static Key createPlusKey(Account account, Key submissionKey) {
		return Datastore.createKey(account.getKey(), Plus.class, submissionKey.getId());
	}

	static Key createMinusKey(Account account, Key submissionKey) {
		return Datastore.createKey(account.getKey(), Minus.class, submissionKey.getId());
	}

	/**
	 * +1 する。
	 * <p>同じSubmissionに対する-1があれば消してから+1する。</p>
	 * @param account
	 * @param submissionKey
	 * @author shin1ogawa
	 */
	public static void plus(Account account, Key submissionKey) {
		Key plusKey = createPlusKey(account, submissionKey);
		Minus plus = new Minus();
		plus.setKey(plusKey);
		Key minusKey = createMinusKey(account, submissionKey);
		Transaction tx = Datastore.beginTransaction();
		Datastore.delete(minusKey);
		Datastore.put(plus);
		tx.commit();
	}

	/**
	 * -1 する。
	 * <p>同じSubmissionに対する+1があれば消してから、-1する。</p>
	 * @param account
	 * @param submissionKey
	 * @author shin1ogawa
	 */
	public static void minus(Account account, Key submissionKey) {
		Key minusKey = createMinusKey(account, submissionKey);
		Minus minus = new Minus();
		minus.setKey(minusKey);
		Key plusKey = createPlusKey(account, submissionKey);
		Transaction tx = Datastore.beginTransaction();
		Datastore.delete(plusKey);
		Datastore.put(minus);
		tx.commit();
	}
}
