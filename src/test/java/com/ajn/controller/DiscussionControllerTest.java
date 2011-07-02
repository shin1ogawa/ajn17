package com.ajn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import com.ajn.meta.DiscussionMeta;
import com.ajn.model.Account;
import com.ajn.model.Discussion;

import static org.hamcrest.Matchers.*;

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
		int beforeCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion01.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_OK));

		int afterCount = tester.count(Discussion.class);
		assertThat(afterCount, is(beforeCount + 1));
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
		int beforeCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		// tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion01.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_UNAUTHORIZED));

		int afterCount = tester.count(Discussion.class);
		assertThat(afterCount, is(beforeCount));
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
		int beforeCount = tester.count(Discussion.class);

		tester.request.setMethod("POST");
		tester.request.getSession().setAttribute("twitterId", 1L);
		tester.request
			.setInputStream(ControllerTestUtil.newJsonInputStream(IOUtils
				.toByteArray(DiscussionControllerTest.class
					.getResourceAsStream("/discussion02.json"))));
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		int afterCount = tester.count(Discussion.class);
		assertThat(afterCount, is(beforeCount));
		assertThat(tester.response.getMessage(), is(notNullValue()));
	}

	/**
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ServletException
	 * @author shin1ogawa
	 */
	@Test
	public void list() throws NullPointerException, IllegalArgumentException, IOException,
			ServletException {
		int beforeCount = tester.count(Discussion.class);
		assertThat(beforeCount, is(greaterThan(0)));

		tester.request.setMethod("GET");
		tester.start(PATH);

		assertThat(tester.getController(), is(instanceOf(DiscussionController.class)));
		assertThat(tester.response.getStatus(), is(HttpServletResponse.SC_OK));

		int afterCount = tester.count(Discussion.class);
		assertThat(afterCount, is(beforeCount));
		System.out.println(tester.response.getOutputAsString());
		Discussion[] discussions =
				DiscussionMeta.get().jsonToModels(tester.response.getOutputAsString());
		assertThat(discussions.length, is(beforeCount));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Account account = new Account();
		account.setKey(Datastore.createKey(Account.class, 1));
		Datastore.put(account);

		List<Discussion> discussions = new ArrayList<Discussion>();
		for (int i = 0; i < 10; i++) {
			Discussion discussion = new Discussion();
			discussion.setTitle("title" + i);
			discussion.setDescription("description" + i);
			discussion.setAuthor(Datastore.createKey(Account.class, i + 1));
			discussion.setAuthorName("author" + i);
			discussions.add(discussion);
		}
		Datastore.put(discussions);
	}
}
