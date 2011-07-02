package com.ajn.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.ajn.meta.SubmissionMeta;
import com.ajn.model.Submission;
import com.google.appengine.api.datastore.Key;

/**
 * @author shin1ogawa
 */
public class SubmissionService {

	static final SubmissionMeta meta = SubmissionMeta.get();


	static Key createKey(long id) {
		return Datastore.createKey(meta, id);
	}

	/**
	 * インスタンスを保存する。
	 * @param discussion
	 * @return {@link Datastore#put(Object)}
	 * @author shin1ogawa
	 */
	public static Key put(Submission discussion) {
		return Datastore.put(discussion);
	}

	/**
	 * {@link Submission}を取得する。
	 * @param id 自動採番された{@link Submission}のid.
	 * @return {@link Submission}、{@code userId}に対応するエンティティが存在しなければ{@code null}
	 * @author shin1ogawa
	 */
	public static Submission get(long id) {
		return Datastore.getOrNull(meta, createKey(id));
	}

	/**
	 * @return {@link Submission}のリスト.
	 * @author shin1ogawa
	 */
	public static List<Submission> list() {
		return Datastore.query(meta).sort(meta.createdAt.desc).asList();
	}
}
