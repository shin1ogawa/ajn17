package com.ajn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import com.ajn.model.Account;
import com.ajn.model.Submission;
import com.ajn.service.PlusAndMinusService;
import com.google.appengine.api.datastore.Key;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

/**
 * {@link VoteController}のテストケース.
 * @author shin1ogawa
 */
public class VoteControllerTest extends ControllerTestCase {

	static final String PATH = "/vote";

	private List<Submission> submissions;


	/**
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ServletException
	 * @author shin1ogawa
	 */
	@Test
	public void get() throws NullPointerException, IllegalArgumentException, IOException,
			ServletException {
		Key[] accounts = new Key[5];
		for (int i = 0; i < 5; i++) {
			accounts[i] = Datastore.createKey(Account.class, i + 1);
		}
		PlusAndMinusService.plus(accounts[0], submissions.get(0).getKey());
		PlusAndMinusService.plus(accounts[1], submissions.get(0).getKey());
		PlusAndMinusService.minus(accounts[2], submissions.get(0).getKey());
		PlusAndMinusService.minus(accounts[3], submissions.get(0).getKey());
		PlusAndMinusService.plus(accounts[4], submissions.get(0).getKey());

		tester.request.setQueryString("submission=" + submissions.get(0).getKey().getId());
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(VoteController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_OK));

		System.out.println(tester.response.getOutputAsString());
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Account account = new Account();
		account.setKey(Datastore.createKey(Account.class, 1));
		Datastore.put(account);

		submissions = new ArrayList<Submission>();
		for (int i = 0; i < 10; i++) {
			Submission submission = new Submission();
			submission.setTitle("title" + i);
			submission.setDescription("description" + i);
			submission.setAuthor(Datastore.createKey(Account.class, i + 1));
			submission.setAuthorName("author" + i);
			submissions.add(submission);
		}
		Datastore.put(submissions);
	}
}
