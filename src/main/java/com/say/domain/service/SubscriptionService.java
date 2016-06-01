package com.say.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.say.domain.Email;
import com.say.domain.EmailSender;
import com.say.domain.SubscriptionEmailTemplates;
import com.say.persistence.Subscription;
import com.say.persistence.dao.SubscriptionDAO;

@Component
public class SubscriptionService {
	@Autowired
	private SubscriptionDAO subscriptionDAO;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private SubscriptionEmailTemplates subscriptionEmailTemplates;

	public String subscribe(String email){
		Subscription subscription = subscriptionDAO.findByEmail(email);
		if( subscription == null ){
			subscription = subscriptionDAO.save( new Subscription(email) );
		}

		sendActivationEmail(subscription);

		return subscription.getEmail();
	}

	public String activate(String email, String permissionCode){
		Subscription subscription = unlock(email, permissionCode);
		subscription.setActivated(true);
		subscriptionDAO.save(subscription);

		return subscription.getEmail();
	}

	public String unsubscribe(String email, String permissionCode){
		Subscription subscription = unlock(email, permissionCode);
		subscriptionDAO.delete(subscription);
		return email;
	}

	private Subscription unlock(String email, String permissionCode){
		Subscription subscription = subscriptionDAO.findByEmail(email);
		if(subscription == null){
			throw new IllegalArgumentException("No subscription found for address: " + email);
		}else if( !subscription.getPermissionCode().equals(permissionCode) ){
			throw new IllegalArgumentException("Permission code does not unlock subscription for: " + email);
		}

		return subscription;
	}

	private void sendActivationEmail(Subscription subscription){
		Email email = new Email();
		email.setTo(subscription.getEmail());
		email.setSubject("Subscription Activation");
		email.setMessage(subscriptionEmailTemplates.composeActivationEmail(subscription));

		emailSender.send(email);
	}
}
