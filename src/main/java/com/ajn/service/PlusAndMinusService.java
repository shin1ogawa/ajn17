package com.ajn.service;

import org.slim3.datastore.Datastore;

import com.ajn.meta.MinusMeta;
import com.ajn.meta.PlusMeta;
import com.ajn.model.Minus;
import com.ajn.model.Plus;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

/**
 * @author shin1ogawa
 */
public class PlusAndMinusService {

	static Key createPlusKey(Key account, Key submissionKey) {
		return Datastore.createKey(account, Plus.class, submissionKey.getId());
	}

	static Key createMinusKey(Key account, Key submissionKey) {
		return Datastore.createKey(account, Minus.class, submissionKey.getId());
	}

	/**
	 * +1 する。
	 * <p>同じSubmissionに対する-1があれば消してから+1する。</p>
	 * @param account
	 * @param submissionKey
	 * @author shin1ogawa
	 */
	public static void plus(Key account, Key submissionKey) {
		Key plusKey = createPlusKey(account, submissionKey);
		Minus plus = new Minus();
		plus.setKey(plusKey);
		plus.setSubmission(submissionKey);
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
	public static void minus(Key account, Key submissionKey) {
		Key minusKey = createMinusKey(account, submissionKey);
		Minus minus = new Minus();
		minus.setKey(minusKey);
		minus.setSubmission(submissionKey);
		Key plusKey = createPlusKey(account, submissionKey);
		Transaction tx = Datastore.beginTransaction();
		Datastore.delete(plusKey);
		Datastore.put(minus);
		tx.commit();
	}

	/**
	 * @param submissionKey
	 * @return {@code submissionKey}につけられた+1, -1の件数を配列で返す。
	 * @author shin1ogawa
	 */
	public static int[] countVotes(Key submissionKey) {
		PlusMeta plusMeta = PlusMeta.get();
		int plus =
				Datastore.query(plusMeta).filter(plusMeta.submission.equal(submissionKey)).count();
		MinusMeta minusMeta = MinusMeta.get();
		int minus =
				Datastore.query(minusMeta).filter(minusMeta.submission.equal(submissionKey))
					.count();
		return new int[] {
			plus,
			minus
		};
	}
}
