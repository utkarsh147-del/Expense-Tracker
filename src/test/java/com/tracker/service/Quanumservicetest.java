package com.tracker.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.tracker.controler.quantumexpensecontroler;
import com.tracker.entity.Expense;

@WebMvcTest(quantumexpensecontroler.class)
public class Quanumservicetest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;
    
    @Test
    void testGetQuantumSuggestion() throws Exception {
    	Expense expense1 = new Expense("Coffee", 5.0, "Food", LocalDateTime.now());
        Expense expense2 = new Expense("Bus", 10.0, "Travel", LocalDateTime.now());
        Expense expense3 = new Expense("Lunch", 15.0, "Food", LocalDateTime.now());
        List<Expense> expenses = Arrays.asList(expense1, expense2, expense3);
        when(expenseService.getHighestSpendingCategory(expenses)).thenReturn("Food");

        mockMvc.perform(get("/api/quantum/suggestions")
                .header("Authorization", "user1"))
                .andExpect(status().isOk());
    }
    
    

}
