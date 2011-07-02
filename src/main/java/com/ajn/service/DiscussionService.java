package com.ajn.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.ajn.meta.DiscussionMeta;
import com.ajn.model.Discussion;
import com.google.appengine.api.datastore.Key;

/**
 * @author shin1ogawa
 */
public class DiscussionService {

	static final DiscussionMeta meta = DiscussionMeta.get();


	static Key createKey(long id) {
		return Datastore.createKey(meta, id);
	}

	/**
	 * インスタンスを保存する。
	 * @param discussion
	 * @return {@link Datastore#put(Object)}
	 * @author shin1ogawa
	 */
	public static Key put(Discussion discussion) {
		return Datastore.put(discussion);
	}

	/**
	 * {@link Discussion}を取得する。
	 * @param id 自動採番された{@link Discussion}のid.
	 * @return {@link Discussion}、{@code userId}に対応するエンティティが存在しなければ{@code null}
	 * @author shin1ogawa
	 */
	public static Discussion get(long id) {
		return Datastore.getOrNull(meta, createKey(id));
	}

	/**
	 * @return {@link Discussion}のリスト.
	 * @author shin1ogawa
	 */
	public static List<Discussion> list() {
		return Datastore.query(meta).sort(meta.createdAt.desc).asList();
	}
}
