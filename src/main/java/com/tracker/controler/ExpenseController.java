package com.tracker.controler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.entity.Expense;
import com.tracker.entity.User;
import com.tracker.service.ExpenseService;
import com.tracker.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
	@Autowired
    private ExpenseService expenseservice;
	@Autowired
	private UserService userService;
	
	@PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expenseEntry) {
		try {
			org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			String userName=authentication.getName();
			
			expenseEntry.setDate(LocalDateTime.now());
			expenseservice.saveExpense(expenseEntry,userName);
			return new ResponseEntity<>(expenseEntry,HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
			return new ResponseEntity<>(expenseEntry,HttpStatus.BAD_REQUEST);
		}
       
    }
	
	@GetMapping
    public ResponseEntity<List<Expense>> getExpenses(HttpServletRequest request) {
		try
		{
    	String authHeader = request.getHeader("Authorization");
        System.out.println("Received Authorization header: " + authHeader); // Debug
        System.out.println("Received Authorization header: " + authHeader);
		org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String userName=authentication.getName();
		User user=userService.findByUserName(userName);
		
		
		
		List<Expense>all= user.getExpenseList();

		if(all !=null && !all.isEmpty())
		{
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		
		else {
            System.out.println("No expenses found for user: " + userName); // Debug
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        System.out.println("Exception caught in getExpenses: " + e.getMessage()); // Debug
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<Expense>getExpenseById(@PathVariable ObjectId myId){
		try {
			org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			String userName=authentication.getName();
			User user=userService.findByUserName(userName);
			List<Expense> collect=user.getExpenseList().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
			
			if(!collect.isEmpty())
			{
				Optional<Expense> expenseEntry= expenseservice.findById(myId);
				
				 if(expenseEntry.isPresent())
				 {
					 return new ResponseEntity<>(expenseEntry.get(),HttpStatus.OK);
				 }
			}
			
			}
			catch (Exception e)
			{
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteExpenseEntryById(@PathVariable ObjectId myId) {
		org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String userName=authentication.getName();
		User user=userService.findByUserName(userName);
		List<Expense> collect=user.getExpenseList().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
		boolean removed=expenseservice.deleteById(myId,userName);
		if(removed)
		{		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/id/{Id}")
	public ResponseEntity<?> updateExpenseEntryById(@PathVariable ObjectId Id,@RequestBody Expense newEntry) {
	//	myEntry.setDate(LocalDateTime.now());
		org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String userName=authentication.getName();
		User user=userService.findByUserName(userName);
		List<Expense> collect=user.getExpenseList().stream().filter(x->x.getId().equals(Id)).collect(Collectors.toList());
		//boolean removed=expenseservice.deleteById(Id,userName);
		
		if(!collect.isEmpty())
		{
			Optional<Expense> ExpenseEntry= expenseservice.findById(Id);
			
			
			 if(ExpenseEntry.isPresent())
			 {
				 Expense oldentry=ExpenseEntry.get();
				 oldentry.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().isEmpty()?newEntry.getDescription():oldentry.getDescription());
					oldentry.setCategory(newEntry.getCategory()!=null && !newEntry.equals("")?newEntry.getCategory():oldentry.getCategory());
					oldentry.setAmount(newEntry.getAmount()>=0 && !newEntry.equals("")?newEntry.getAmount():oldentry.getAmount());
				
				expenseservice.saveExpense(oldentry);
				System.out.println("lk");
				 return new ResponseEntity<>(oldentry,HttpStatus.OK);
			 }
		}
		
		System.out.println("mk");
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	

}
