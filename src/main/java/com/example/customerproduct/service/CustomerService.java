package com.example.customerproduct.service;

import com.example.customerproduct.exception.ResourceAlreadyExistException;
import com.example.customerproduct.exception.ResourceDoseNotExistException;
import com.example.customerproduct.model.Customer;
import com.example.customerproduct.model.Product;
import com.example.customerproduct.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    // findById method
    public Customer findById(long id) throws ResourceDoseNotExistException {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new ResourceDoseNotExistException(id + "");
        }
    }

    //findAll method
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customerList::add);
        return customerList;
    }


    //deleteById method
    public boolean deleteById(long id) throws ResourceDoseNotExistException {

        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(event -> customerRepository.deleteById(id));
       customerOptional.orElseThrow(() -> new ResourceDoseNotExistException(id + ""));
        return true;

    }

    //insert method
    public Customer insertCustomer(Customer customer) throws ResourceAlreadyExistException {


        Optional<Customer>optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            throw new ResourceAlreadyExistException(customer.getId() + "");
        } else {
            return customerRepository.save(customer);
        }

    }


    //update method

    public Customer updateCustomer(long id, Customer customer) throws ResourceDoseNotExistException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {

           customer.setId(id);
            return customerRepository.save(customer);

        } else {
            throw new ResourceDoseNotExistException(id + "");
        }

    }
}
