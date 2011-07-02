package com.ajn.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import com.ajn.model.Account;
import com.ajn.model.Discussion;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link DiscussionController}のテストケース。
 * @author shin1ogawa
 */
public class DiscussionControllerTest extends ControllerTestCase {

	static final String PATH = "/discussion";


	/**
	 * @author shin1ogawa
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws NullPointerException 
	 */
	@Test
	public void post() throws NullPointerException, IllegalArgumentException, IOException,
			ServletException {
		int beforeCommentCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion01.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_OK));

		int afterCommentCount = tester.count(Discussion.class);
		assertThat(afterCommentCount, is(beforeCommentCount + 1));
		assertThat(tester.response.getOutputAsString(), is(notNullValue()));
	}

	/**
	 * @author shin1ogawa
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws NullPointerException 
	 */
	@Test
	public void postWithoutSession() throws NullPointerException, IllegalArgumentException,
			IOException, ServletException {
		int beforeCommentCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		// tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion01.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_UNAUTHORIZED));

		int afterCommentCount = tester.count(Discussion.class);
		assertThat(afterCommentCount, is(beforeCommentCount));
		assertThat(tester.response.getMessage(), is(notNullValue()));
	}

	/**
	 * @author shin1ogawa
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws NullPointerException 
	 */
	@Test
	public void postWithoutTitle() throws NullPointerException, IllegalArgumentException,
			IOException, ServletException {
		int beforeCommentCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion02.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		int afterCommentCount = tester.count(Discussion.class);
		assertThat(afterCommentCount, is(beforeCommentCount));
		assertThat(tester.response.getMessage(), is(notNullValue()));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Account account = new Account();
		account.setKey(Datastore.createKey(Account.class, 1));
		Datastore.put(account);
	}
}
