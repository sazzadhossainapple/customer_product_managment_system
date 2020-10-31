package com.example.customerproduct.controller;


import com.example.customerproduct.exception.ResourceAlreadyExistException;
import com.example.customerproduct.exception.ResourceDoseNotExistException;
import com.example.customerproduct.model.Customer;
import com.example.customerproduct.repository.CustomerRepository;
import com.example.customerproduct.repository.ProductRepository;
import com.example.customerproduct.service.CustomerService;
import com.example.customerproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "api/v3/customer")
public class CustomerController {


  private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;

    }




    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {

        try {
           Customer customer= customerService.findById(id);
            return ResponseEntity.ok(customer);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Customer>> getCustomer() {
        List<Customer> customerList= customerService.findAll();
        return ResponseEntity.ok(customerList);
    }



    @PostMapping("")
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer) {
        try {
           Customer insertCustomer = customerService.insertCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertCustomer);
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }


    @PutMapping("/{id}")
    public Customer addCity(@PathVariable long id,@RequestBody Customer customer) throws ResourceDoseNotExistException {
        return customerService.updateCustomer(id,customer);
    }


//    @PutMapping(value = "{id}")
//    public ResponseEntity<?> saveAttendance(@RequestBody Customer attendance,
//                                            @PathVariable("id") long id) throws ResourceDoseNotExistException {
//
//       Customer currentUser = customerService.findById(id);
//        if (currentUser == null) {
//            return new ResponseEntity(new ResourceDoseNotExistException("Unable to update. User with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
//        currentUser.setName(attendance.getName());
//        currentUser.setLocalDate(attendance.getLocalDate());
//        attendanceService.updateAttendance(id,currentUser);
//        return new ResponseEntity<Attendance>(currentUser, HttpStatus.OK);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAttendance(@PathVariable long id) {
        try {
            boolean deleted = customerService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();

        }

    }

}
