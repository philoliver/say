package com.say.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.say.persistence.Subscription;

public interface SubscriptionDAO extends JpaRepository<Subscription, Integer>{
	public Subscription findByEmail(String email);
	public List<Subscription> findByActivatedEquals(Boolean active);
}
