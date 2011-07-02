package com.ajn.controller;

import org.apache.commons.lang.StringUtils;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.AppEngineUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.ajn.model.Account;
import com.ajn.service.AccountService;

/**
 * TwitterのOAuthを使って認証する。
 * @author shin1ogawa
 */
public class LoginController extends Controller {

	static final String CONSUMER_KEY = "DcSwxkuzSkvLAl4ej7dw";

	static final String CONSUMER_SECRET = "21iRmb3wfRbnbmo9uXJLo4OAyfJqzHd5NQVzm6viY";


	@Override
	protected Navigation run() throws Exception {
		String verifier = request.getParameter("oauth_verifier");
		if (StringUtils.isEmpty(verifier)) {
			return phase1();
		}
		Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
		RequestToken requestToken =
				(RequestToken) request.getSession().getAttribute("requestToken");
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
			long userId = accessToken.getUserId();
			Account account = AccountService.get(userId);
			if (account == null) {
				account =
						AccountService.newInstance(userId, accessToken.getScreenName(),
								accessToken.getToken(), accessToken.getTokenSecret());
				AccountService.put(account);
			}
			request.getSession().removeAttribute("requestToken");
			request.getSession().setAttribute("twitterId", userId);
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return redirect("/index.html");
	}

	Navigation phase1() {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			String urlString = callbackURL.toString();
			if (AppEngineUtil.isProduction() && urlString.indexOf("http:") >= 0) {
				urlString = urlString.replaceFirst("http\\:", "https:");
			}
			RequestToken requestToken = twitter.getOAuthRequestToken(urlString);
			request.getSession().setAttribute("requestToken", requestToken);
			return redirect(requestToken.getAuthenticationURL());
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
