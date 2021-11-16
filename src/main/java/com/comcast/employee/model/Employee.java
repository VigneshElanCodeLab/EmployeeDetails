package com.comcast.employee.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name ="Employee")
@Data
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String location;
    private String password;

}
