package com.tracker.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.entity.User;
import com.tracker.service.ExpenseService;
import com.tracker.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/quantum")
public class quantumexpensecontroler {

	@Autowired
    private ExpenseService expenseservice;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/suggestions")
	public ResponseEntity<String> getQuantumSuggestion() {
        try {
        	org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        	String userName=authentication.getName();
			User user=userService.findByUserName(userName);
			String category = expenseservice.getHighestSpendingCategory(user.getExpenseList());
        	
//        	ProcessBuilder pb = new ProcessBuilder("src\\main\\java\\com\\tracker\\quantumscripts\\project\\Scripts\\python.exe", "src/main/java/com/tracker/quantumscripts/quantumscripts.py",category);
			ProcessBuilder pb = new ProcessBuilder("python3", "/app/Scripts/file/quantumscripts.py", category);
			pb.directory(new File("/app"));
          //  pb.directory(new java.io.File("."));
            Process p = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String suggestion = reader.readLine();

            // Error output capture karo
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder error = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                error.append(errorLine).append("\n");
            }

            int exitCode = p.waitFor();
            if (exitCode == 0 && suggestion != null) {
                return ResponseEntity.ok("Quantum Suggestion for "+userName+" is "+category+": " + suggestion);
            } else {
                return ResponseEntity.status(500).body("Error running quantum script: " + error.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Exception: " + e.getMessage());
        }
    }
}