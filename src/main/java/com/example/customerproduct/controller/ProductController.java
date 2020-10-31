package com.example.customerproduct.controller;

import com.example.customerproduct.exception.ResourceAlreadyExistException;
import com.example.customerproduct.exception.ResourceDoseNotExistException;
import com.example.customerproduct.model.Customer;
import com.example.customerproduct.model.Product;
import com.example.customerproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v3/product")

public class ProductController {

    private ProductService productService;
   @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long pid) {

        try {
           Product product = productService.findById(pid);
            return ResponseEntity.ok(product);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getCustomer() {
        List<Product> productList= productService.findAll();
        return ResponseEntity.ok(productList);
    }



    @PostMapping("")
    public ResponseEntity<Product> insertCustomer(@RequestBody Product product) {
        try {
            Product insertProduct = productService.insertProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertProduct);
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    @PutMapping("/{id}")
    public Product addCity(@PathVariable long id,@RequestBody Product product) throws ResourceDoseNotExistException {
        return productService.updateProduct(id,product);
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
    public ResponseEntity<Long> deleteAttendance(@PathVariable long pid) {
        try {
            boolean deleted = productService.deleteById(pid);
            return ResponseEntity.ok(pid);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();

        }

    }

}
