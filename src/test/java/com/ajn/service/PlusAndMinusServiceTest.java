package com.ajn.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import com.ajn.model.Account;
import com.ajn.model.Minus;
import com.ajn.model.Plus;
import com.ajn.model.Submission;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * {@link PlusAndMinusService}のテストケース.
 * @author shin1ogawa
 */
public class PlusAndMinusServiceTest extends ControllerTestCase {

	private Account account;

	private List<Submission> discussions;


	/**
	 * @author shin1ogawa
	 */
	@Test
	public void plus() {
		int beforePlusCount = tester.count(Plus.class);
		int beforeMinusCount = tester.count(Minus.class);
		PlusAndMinusService.plus(account.getKey(), discussions.get(0).getKey());
		int afterPlusCount = tester.count(Plus.class);
		int afterMinusCount = tester.count(Minus.class);
		assertThat(afterPlusCount, is(beforePlusCount + 1));
		assertThat(afterMinusCount, is(beforeMinusCount));
	}

	/**
	 * @author shin1ogawa
	 */
	@Test
	public void minus() {
		int beforePlusCount = tester.count(Plus.class);
		int beforeMinusCount = tester.count(Minus.class);
		PlusAndMinusService.minus(account.getKey(), discussions.get(0).getKey());
		int afterPlusCount = tester.count(Plus.class);
		int afterMinusCount = tester.count(Minus.class);
		assertThat(afterPlusCount, is(beforePlusCount));
		assertThat(afterMinusCount, is(beforeMinusCount + 1));
	}

	/**
	 * <p>最初のプラスは取り消されて、マイナスだけが残る</p>
	 * @author shin1ogawa
	 */
	@Test
	public void plusAndMinus() {
		int beforePlusCount = tester.count(Plus.class);
		int beforeMinusCount = tester.count(Minus.class);
		PlusAndMinusService.plus(account.getKey(), discussions.get(0).getKey());
		PlusAndMinusService.minus(account.getKey(), discussions.get(0).getKey());
		int afterPlusCount = tester.count(Plus.class);
		int afterMinusCount = tester.count(Minus.class);
		assertThat(afterPlusCount, is(beforePlusCount));
		assertThat(afterMinusCount, is(beforeMinusCount + 1));
	}

	/**
	 * マイナスしてからプラスする。
	 * <p>最初のマイナスは取り消されて、プラスだけが残る</p>
	 * @author shin1ogawa
	 */
	@Test
	public void minusAndPlus() {
		int beforePlusCount = tester.count(Plus.class);
		int beforeMinusCount = tester.count(Minus.class);
		PlusAndMinusService.minus(account.getKey(), discussions.get(0).getKey());
		PlusAndMinusService.plus(account.getKey(), discussions.get(0).getKey());
		int afterPlusCount = tester.count(Plus.class);
		int afterMinusCount = tester.count(Minus.class);
		assertThat(afterPlusCount, is(beforePlusCount + 1));
		assertThat(afterMinusCount, is(beforeMinusCount));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		account = new Account();
		account.setKey(Datastore.createKey(Account.class, 1));
		Datastore.put(account);

		discussions = new ArrayList<Submission>();
		for (int i = 0; i < 10; i++) {
			Submission submission = new Submission();
			submission.setTitle("title" + i);
			submission.setDescription("description" + i);
			submission.setAuthor(Datastore.createKey(Account.class, i + 1));
			submission.setAuthorName("author" + i);
			discussions.add(submission);
		}
		Datastore.put(discussions);
	}
}
