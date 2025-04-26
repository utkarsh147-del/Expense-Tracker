package com.tracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.entity.Expense;
import com.tracker.entity.User;
import com.tracker.repository.ExpenseRepository;

@Component
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository expenserepository;
	@Autowired
	    private UserService userService;
	
	@Transactional
	public void saveExpense(Expense expense,String userName) {
		try {
			
            User user = userService.findByUserName(userName);
            expense.setDate(LocalDateTime.now());
            Expense saved = expenserepository.save(expense);
            user.getExpenseList().add(saved);
            userService.saveUser(user);
		}
		catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
        
    }
	
	
	
	public Expense saveExpense(Expense expense) {
        return expenserepository.save(expense);
    }
    public List<Expense> getAllExpenses() {
        return expenserepository.findAll();
    }
    
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getExpenseList().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                expenserepository.deleteById(id);
            }
        } catch (Exception e) {
//            log.error("Error ",e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }
    
    public Optional<Expense> findById(ObjectId id) {
        return expenserepository.findById(id);
    }
    
    public String getHighestSpendingCategory(List<Expense> expenses) {
        
        Map<String, Double> categoryTotals = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
        return categoryTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No expenses found");
    }
    

}
