package com.ajn.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.ajn.model.Account;
import com.ajn.model.Submission;
import com.ajn.service.AccountService;
import com.ajn.service.PlusAndMinusService;

/**
 * @author shin1ogawa
 */
public class MinusController extends Controller {

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
		}
		return null;
	}

	Navigation doPost(Account account) throws IOException {
		String submissionString = asString("submission");
		if (StringUtils.isEmpty(submissionString)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "submission must not be null.");
			return null;
		}
		long submissionId;
		try {
			submissionId = Long.parseLong(submissionString);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "submission must be number.");
			return null;
		}
		PlusAndMinusService.minus(account, Datastore.createKey(Submission.class, submissionId));
		return null;
	}
}
