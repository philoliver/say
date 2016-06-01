package com.say.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.say.domain.service.PostService;

@RestController
public class PassphraseController {
	@Autowired
	private PostService postService;

	@Value("${passphrase}")
	private String passphrase;

	@RequestMapping(value = "rest/passphrase/check", method = RequestMethod.GET)
    @ResponseBody
    public Boolean check(@RequestParam String passphrase) throws Exception {
    	return passphrase.toLowerCase().equals(this.passphrase.toLowerCase());
    }
}
