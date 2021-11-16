package com.comcast.employee.repository;

import com.comcast.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

     List<Employee> findAll();
     Employee findById(int id);
}
