package com.project.Product.controller;

import com.project.Product.exception.ResourceNotFoundException;
import com.project.Product.model.Products;
import com.project.Product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="http://localhost:5173/")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<Products> createProducts(@RequestBody Products products)
    {
        Products products1=productRepository.save(new Products(products.getName(),products.getDescription(),products.getImagePath(),products.getPrice(),products.getQuantity(),products.getSize(),products.getColorway(),products.getBrand(),products.isCart()));
        return  new ResponseEntity<>(products1, HttpStatus.CREATED);

    }

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts()
    {
        List<Products> productsList= productRepository.findAll();
        return new ResponseEntity<>(productsList,HttpStatus.OK);
    }
    @GetMapping("/products/{brand}")
    public ResponseEntity<List<Products>> getByBrand(@PathVariable("brand") String brand ,@RequestBody Products products)
    {
        List<Products> brandList=productRepository.findByBrand(brand);
        return new ResponseEntity<>(brandList,HttpStatus.OK);
    }
    @GetMapping("/products/{cart}")
    public ResponseEntity<List<Products>> getByCart(@PathVariable("cart") boolean cart,@RequestBody Products products)
    {
        List<Products> cartList=productRepository.findByCart(cart);
        return  new ResponseEntity<>(cartList,HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Map<String,Object>> updateProducts(@PathVariable("id") Integer id,@RequestBody Products products) throws ResourceNotFoundException
    {
        Products products1=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product Id "+id+" Not Found"));
        products1.setBrand(products.getBrand());
        products1.setCart(products.isCart());
        products1.setColorway(products.getColorway());
        products1.setDescription(products.getDescription());
        products1.setImagePath(products.getImagePath());
        products1.setName(products.getName());
        products1.setPrice(products.getPrice());
        products1.setQuantity(products.getQuantity());
        products1.setSize(products.getSize());
        productRepository.save(products1);
        Map<String,Object> response=new HashMap<>();
        response.put("Message","Product Added Successfully ");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/products/addCart/{id}")
    public ResponseEntity<Products> addToCart(@PathVariable("id") Integer id,@RequestBody Products products) throws ResourceNotFoundException
    {
        Products products1=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Stock"));
        products1.setCart(true);
        return new ResponseEntity<>(productRepository.save(products1),HttpStatus.OK);
    }

    @PutMapping("/products/removeCart/{id}")
    public ResponseEntity<Products> removeToCart(@PathVariable("id") Integer id,@RequestBody Products products) throws ResourceNotFoundException
    {
        Products products1=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Stock"));
        products1.setCart(false);
        return new ResponseEntity<>(productRepository.save(products1),HttpStatus.OK);
    }
}
