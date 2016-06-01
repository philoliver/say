package com.say.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.say.domain.service.PostService;
import com.say.persistence.Post;
import com.say.rest.annotation.RequiresPermission;
import com.say.rest.validator.PostValidator;

@RestController
public class PostController {
	@Autowired
	private PostService postService;

	@RequiresPermission
	@RequestMapping(value = "rest/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> listAll() throws Exception {
    	return postService.findAll();
    }

	@RequiresPermission
    @RequestMapping(value = "rest/posts", method = RequestMethod.POST)
    @ResponseBody
    public Post save(@RequestBody String message) {
		Post post = new Post(message);
		if( !PostValidator.validate(post) ){
			throw new IllegalArgumentException("Invalid post");
		}

    	return postService.save(post);
    }
}
