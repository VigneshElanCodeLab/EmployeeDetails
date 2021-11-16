package com.comcast.employee.resources;

import com.comcast.employee.dto.EmployeeDto;
import com.comcast.employee.exception.EmployeeException;
import com.comcast.employee.model.Employee;
import com.comcast.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeResources {

    @Autowired
    EmployeeService empSer;

    ModelMapper modelMapper = new ModelMapper();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<EmployeeDto> getEmployee() {
        List<Employee> emp = new ArrayList<Employee>();
        List<EmployeeDto> empDto = new ArrayList<EmployeeDto>();
        try {
            emp = empSer.getEmployee();
             empDto = emp
                    .stream()
                    .map(user -> modelMapper.map(user, EmployeeDto.class))
                    .collect(Collectors.toList());
//            for (int i = 0; i < emp.size(); i++) {
//                empDto.add(modelMapper.map(emp.get(i), EmployeeDto.class));
//            }
        } catch (Exception e) {
            log.debug("Exception");
            e.printStackTrace();
            throw new EmployeeException(
                    e.getMessage());
        }
        return empDto;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id) {
        Employee emp;
        EmployeeDto empDto = new EmployeeDto();
        try {
            emp = empSer.getEmployeeById(id);
            if (emp != null) {
                empDto = modelMapper.map(emp, EmployeeDto.class);
                return new ResponseEntity<>(empDto, HttpStatus.OK);
            } else {
                throw new EmployeeException(
                        "Sorry! Emp is not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployeeException(
                    e.getMessage());
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody List<Employee> emp) {
        Employee emplo = null;
        List<EmployeeDto> empDto = new ArrayList<EmployeeDto>();
        try {
            for (int i = 0; i < emp.size(); i++) {
                if (emp.get(i).getName() == null) {
                    throw new EmployeeException(
                            "Sorry! Name is mandatory");
                } else {
                    emplo = empSer.createEmployee(emp.get(i));
                    if (emplo != null) {
                        empDto.add(modelMapper.map(emplo, EmployeeDto.class));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployeeException(
                    "Could not store Emp Please try again!", e);
        }
        return new ResponseEntity<>(empDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {
        String empDelete = null;
        try {
            empDelete = empSer.deleteEmployee(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(empDelete, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putEmployee(@RequestBody Employee emp, @PathVariable("id") int id) {
        Employee empUpdate = null;
        EmployeeDto empDto = new EmployeeDto();
        try {
            if (emp.getName() == null) {
                throw new EmployeeException(
                        "Sorry! Name is mandatory");
            } else {
                empUpdate = empSer.putEmployee(emp, id);
                if (empUpdate != null) {
                    empDto = modelMapper.map(empUpdate, EmployeeDto.class);
                    return new ResponseEntity<>(empDto, HttpStatus.OK);
                } else {
                    throw new EmployeeException(
                            "Sorry! Emp is not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployeeException(
                    "Could not update Emp Please try again!", e);
        }

    }
}
