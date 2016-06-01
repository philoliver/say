package com.say.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.say.domain.service.SubscriptionService;
import com.say.rest.validator.EmailValidator;

@RestController
public class SubscriptionController {
	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private EmailValidator emailValidator;

	@RequestMapping(value = "rest/subscription/subscribe", method = RequestMethod.GET)
    @ResponseBody
    public String subscribe(@RequestParam String email) throws Exception {
		if( !emailValidator.validate(email) ){
			throw new IllegalArgumentException("Invalid email");
		}

    	return subscriptionService.subscribe(email);
    }

	@RequestMapping(value = "rest/subscription/activate", method = RequestMethod.GET)
    @ResponseBody
    public String activate(@RequestParam String email, @RequestParam String permissionCode) {
		subscriptionService.activate(email, permissionCode);
    	return "Your subscription is now active.";
    }

	@RequestMapping(value = "rest/subscription/unsubscribe", method = RequestMethod.GET)
    @ResponseBody
    public String unsubscribe(@RequestParam String email, @RequestParam String permissionCode) {
		subscriptionService.unsubscribe(email, permissionCode);
    	return "You have been unsubscribed from Say! You will not get any further emails from us.";
    }
}
