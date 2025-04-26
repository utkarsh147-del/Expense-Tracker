package com.tracker.repository;
import org.bson.types.ObjectId;

import com.tracker.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface UserRepository extends MongoRepository<User,ObjectId> {

	
	User findByuserName(String username);
}
