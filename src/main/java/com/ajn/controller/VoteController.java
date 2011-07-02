package com.ajn.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.ajn.model.Submission;
import com.ajn.service.PlusAndMinusService;

/**
 * @author shin1ogawa
 */
public class VoteController extends Controller {

	@Override
	protected Navigation run() throws Exception {
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
		int[] votes =
				PlusAndMinusService.countVotes(Datastore.createKey(Submission.class, submissionId));
		response.getWriter().println("{");
		response.getWriter().println("\"plus\":" + votes[0]);
		response.getWriter().println(",\"minus\":" + votes[1]);
		response.getWriter().println("}");
		return null;
	}
}
