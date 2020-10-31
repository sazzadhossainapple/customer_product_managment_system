package com.example.customerproduct.service;

import com.example.customerproduct.exception.ResourceAlreadyExistException;
import com.example.customerproduct.exception.ResourceDoseNotExistException;
import com.example.customerproduct.model.Product;
import com.example.customerproduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
//    @GetMapping("/{id}")
//    public Product getCountry(@PathVariable long id) throws ResourceDoseNotExistException {
//        return poduct.getCountry(id);
//    }

    public Product findById(@PathVariable long id)throws ResourceDoseNotExistException {

        Optional<Product>optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        else {
            throw new ResourceDoseNotExistException(id +"");
        }

    }

    //findAll method
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }


    //deleteById method
    public boolean deleteById(long id) throws ResourceDoseNotExistException {

        Optional<Product> productOptional = productRepository.findById(id);
       productOptional.ifPresent(event -> productRepository.deleteById(id));
       productOptional.orElseThrow(() -> new ResourceDoseNotExistException(id + ""));
        return true;

    }

    //insert method
    public Product insertProduct(Product product) throws ResourceAlreadyExistException {
        Optional<Product> optionalProduct = productRepository.findById(product.getPId());
        if (optionalProduct.isPresent()) {
            throw new ResourceAlreadyExistException(product.getPId() + "");
        } else {
            return productRepository.save(product);
        }

    }


    //update method

    public Product updateProduct(long id,Product product) throws ResourceDoseNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {

           product.setPId(id);
            return productRepository.save(product);

        } else {
            throw new ResourceDoseNotExistException(id + "");
        }

    }

}
