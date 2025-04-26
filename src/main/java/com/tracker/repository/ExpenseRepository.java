package com.tracker.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tracker.entity.Expense;

public interface ExpenseRepository extends MongoRepository<Expense,ObjectId>  {
	
	List<Expense> findByCategory(String category);

}


