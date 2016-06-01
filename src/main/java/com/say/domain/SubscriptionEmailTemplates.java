package com.say.domain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.say.persistence.Post;
import com.say.persistence.Subscription;

@Component
public class SubscriptionEmailTemplates {
	@Value("${addr}")
	private String serverAddress;

	@Value("${server.port}")
	private String serverPort;

	private String serverPath;

	@PostConstruct
	private void setServerPath(){
		serverPath = serverAddress + ":" + serverPort + "/";
	}

	public String composeActivationEmail(Subscription subscription){
		String activtionPath = serverPath + "rest/subscription/activate?email="+subscription.getEmail()+"&permissionCode="+subscription.getPermissionCode();
		String unsubscribePath = serverPath + "rest/subscription/unsubscribe?email="+subscription.getEmail()+"&permissionCode="+subscription.getPermissionCode();

		StringBuffer sb = new StringBuffer();
		sb.append("<p>Hi, </p>");

		// Body
		sb.append("<p>");
		sb.append("You subscribed to Say!<br/>");
		sb.append("To activate your subscription please click ");
		sb.append("<a href='"+activtionPath+"'>here.</a>");
		sb.append("</p>");

		// Unsubscribe
		sb.append("<p>");
		sb.append("<br/>");
		sb.append("<small>Note: You'll always find a <a href='"+unsubscribePath+"'>unsubscribe</a> link in the emails we send you.</small>");
		sb.append("</p>");

		return sb.toString();
	}

	public String composeNotificationEmail(Post post, Subscription subscription){
		String unsubscribePath = serverPath + "rest/subscription/unsubscribe?email="+subscription.getEmail()+"&permissionCode="+subscription.getPermissionCode();

		StringBuffer sb = new StringBuffer();
		sb.append("<p>Hi, </p>");

		// Body
		sb.append("<p>");
		sb.append("There is a new post on <a href='"+serverPath+"'>Say! </a><br/>");
		sb.append("</p>");
		sb.append("It says: <i>\"" + post.getMessage() + "\"</i> ");
		sb.append("</p>");

		// Unsubscribe
		sb.append("<p>");
		sb.append("<br/>");
		sb.append("<small>If you don't want to receive any notification from us feel free to <a href='"+unsubscribePath+"'>unsubscribe</a></small>.");
		sb.append("</p>");

		return sb.toString();
	}
}
