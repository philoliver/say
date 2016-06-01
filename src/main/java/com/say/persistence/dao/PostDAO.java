package com.say.persistence.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.say.persistence.Post;

public interface PostDAO extends JpaRepository<Post, Integer>{
	public Post findByMessage(String message);
	public List<Post> findByCreationDateGreaterThanOrderByCreationDateDesc(Date date);
}
