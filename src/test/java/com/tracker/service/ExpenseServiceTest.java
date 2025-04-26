package com.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tracker.entity.Expense;
import com.tracker.repository.ExpenseRepository;

public class ExpenseServiceTest {

	
	@Mock
	private ExpenseRepository expenserepository;
	
	@InjectMocks
	private ExpenseService expenseservice;
	
	@BeforeEach
	private void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    void testGetHighestSpendingCategory() {
		Expense expense1 = new Expense("Coffee", 5.0, "Food", LocalDateTime.now());
        Expense expense2 = new Expense("Bus", 10.0, "Travel", LocalDateTime.now());
        Expense expense3 = new Expense("Lunch", 15.0, "Food", LocalDateTime.now());
        List<Expense>expense=Arrays.asList(expense1,expense2,expense3);
        when(expenserepository.findAll()).thenReturn(expense);
        
        String category = expenseservice.getHighestSpendingCategory(expense);
        assertEquals("Food", category);
		
	}
	
	@Test
	void testGetHighestSpendingCategoryNoExpense()
	{
		when(expenserepository.findAll()).thenReturn(Arrays.asList());
		String category = expenseservice.getHighestSpendingCategory(Arrays.asList());
		assertEquals("No expenses found", category);
		
	}
	
	
}
