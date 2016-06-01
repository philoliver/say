package com.say.rest.validator;

import org.springframework.stereotype.Component;

import com.say.persistence.Post;

/**
 * Validates if a Post is valid.
 *
 * @author philipp
 *
 */

@Component
public class PostValidator {

	/**
	 * Validates if a Post is valid.
	 *
	 * @param post
	 * @return
	 */
	public static boolean validate(Post post){
		return !post.getMessage().trim().isEmpty();
	}
}
