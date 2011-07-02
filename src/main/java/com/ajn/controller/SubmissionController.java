package com.ajn.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

import com.ajn.meta.SubmissionMeta;
import com.ajn.model.Account;
import com.ajn.model.Submission;
import com.ajn.service.AccountService;
import com.ajn.service.SubmissionService;
import com.google.appengine.api.datastore.Key;

/**
 * Submissionを操作するController.
 * @author shin1ogawa
 */
public class SubmissionController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		if (isPost()) {
			Long twitterId = (Long) request.getSession().getAttribute("twitterId");
			if (twitterId == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			Account account = AccountService.get(twitterId);
			if (account == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			return doPost(account);
		} else if (isGet()) {
			return doGet();
		}
		response.flushBuffer();
		return null;
	}

	Navigation doGet() throws IOException {
		List<Submission> list = SubmissionService.list();
		response.getWriter().println(
				SubmissionMeta.get().modelsToJson(list.toArray(new Submission[0])));
		return null;
	}

	Navigation doPost(Account account) throws UnsupportedEncodingException, IOException {
		Submission discussion;
		try {
			BufferedReader r =
					new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			discussion = SubmissionMeta.get().jsonToModel(IOUtils.toString(r));
		} catch (RuntimeException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"invalid json ? - " + e.toString());
			return null;
		}
		if (StringUtils.isEmpty(discussion.getTitle())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "discussion must not be null.");
			return null;
		}
		Key key = SubmissionService.put(discussion);
		response.getWriter().println(String.format("{\"id\":%d}", key.getId()));
		response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}
}
