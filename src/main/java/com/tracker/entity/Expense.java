package com.tracker.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;


@Document(collection="expense_entry")
@Getter
@Setter
@Data
public class Expense {
	
	@JsonSerialize(using = ToStringSerializer.class)
	@Id
	private ObjectId id;
    private String description;
    private double amount;
    private String category;
    private LocalDateTime date;
    
    public Expense() {}
    public Expense(String description, double amount, String category, LocalDateTime date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

}
