package com.ajn.service;

import java.util.Date;

import org.slim3.datastore.Datastore;

import twitter4j.User;

import com.ajn.meta.AccountMeta;
import com.ajn.model.Account;
import com.google.appengine.api.datastore.Key;

/**
 * @author shin1ogawa
 */
public class AccountService {

	static final AccountMeta meta = AccountMeta.get();


	static Key createKey(long userId) {
		return Datastore.createKey(meta, userId);
	}

	/**
	 * @param userId 
	 * @param userName 
	 * @param accessToken 
	 * @param tokenSecret 
	 * @return 新しく作成した、keyも設定済みのインスタンス
	 * @author shin1ogawa
	 */
	public static Account newInstance(long userId, String userName, String accessToken,
			String tokenSecret) {
		Account account = new Account();
		account.setKey(createKey(userId));
		account.setName(userName);
		account.setAccessToken(accessToken);
		account.setAccessTokenSecret(tokenSecret);
		account.setCreatedAt(new Date());
		return account;
	}

	/**
	 * インスタンスを保存する。
	 * @param account
	 * @return {@link Datastore#put(Object)}
	 * @author shin1ogawa
	 */
	public static Key put(Account account) {
		return Datastore.put(account);
	}

	/**
	 * {@link Account}を取得する。
	 * @param userId {@link User#getId()}
	 * @return {@link Account}、{@code userId}に対応するエンティティが存在しなければ{@code null}
	 * @author shin1ogawa
	 */
	public static Account get(long userId) {
		return Datastore.getOrNull(meta, createKey(userId));
	}
}
