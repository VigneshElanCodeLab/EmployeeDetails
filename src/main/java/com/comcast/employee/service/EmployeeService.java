package com.comcast.employee.service;

import com.comcast.employee.model.Employee;
import com.comcast.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    public List<Employee> getEmployee(){
        List<Employee> emp = new ArrayList<Employee>();
        try{
            emp = empRepo.findAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return emp;
    }

    public Employee getEmployeeById(int id){
        Employee emp = null;
        try{
            emp = empRepo.findById(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
       return emp;
    }

    public Employee createEmployee(Employee emp){
        Employee empCreate = null;
        try{
            empCreate = empRepo.saveAndFlush(emp);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return empCreate;
    }

    public String deleteEmployee(int id){
        Employee empDelete = null;
        try{
            empDelete = empRepo.findById(id);
            if(empDelete != null){
                empRepo.delete(empDelete);
                return "Successfully deleted";
            }
            else{
                return "Employee not found";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "Exception";
        }

    }

    public Employee putEmployee(Employee emp, int id){
        Employee empUpdate = null;
        try{
            empUpdate = empRepo.findById(id);
            if(empUpdate != null){
            empUpdate = empRepo.saveAndFlush(emp);
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return empUpdate;
    }
}
