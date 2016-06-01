package com.say.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.domain.Email;
import com.say.domain.EmailSender;
import com.say.domain.SubscriptionEmailTemplates;
import com.say.persistence.Post;
import com.say.persistence.Subscription;
import com.say.persistence.dao.PostDAO;
import com.say.persistence.dao.SubscriptionDAO;

@Component
public class PostService {
	@Autowired
	private PostDAO postDAO;

	@Autowired
	private SubscriptionDAO subscriptionDAO;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private SubscriptionEmailTemplates subscriptionEmailTemplates;

	public Post save(Post post){
		Post sameMessagePost = postDAO.findByMessage(post.getMessage());
		if(sameMessagePost != null){
			throw new IllegalArgumentException("Post with same message already exists.");
		}

		sendNotificationEmails(post);

		return postDAO.save(post);
	}

	public List<Post> findAll(){
		return postDAO.findByCreationDateGreaterThanOrderByCreationDateDesc( new LocalDate().minusMonths(1).toDate() );
	}

	private void sendNotificationEmails(Post post){
		List<Subscription> subscriptions = subscriptionDAO.findByActivatedEquals(true);
		List<Email> emails = new ArrayList<Email>(subscriptions.size());

		for( Subscription subscription : subscriptions ){
			Email email = new Email();
			email.setTo(subscription.getEmail());
			email.setSubject("New post on Say!");
			email.setMessage(subscriptionEmailTemplates.composeNotificationEmail(post, subscription));

			emails.add(email);
		}

		emailSender.bulkSend(emails);
	}
}
