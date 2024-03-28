package com.project.Product.controller;

import com.project.Product.PasswordHashingService.PasswordHashingService;
import com.project.Product.exception.ResourceNotFoundException;
import com.project.Product.model.Users;
import com.project.Product.repository.ProductRepository;
import com.project.Product.repository.UsersRepository;
import org.apache.catalina.User;
import org.hibernate.usertype.UserTypeSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin(origins ="http://localhost:5173/")
@RequestMapping("/api")
public class UsersController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private PasswordHashingService passwordHashingService;
    @PostMapping("/saveUser")
    public ResponseEntity<Users> saveTheUser(@RequestBody Users users){
        String hashedPassword=passwordHashingService.hashPassword(users.getPassword());
        Users users1=usersRepository.save(new Users(users.getEmail(), users.getUser(),hashedPassword,users.getRole()));
        return  new ResponseEntity<>(users1, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<List<Users>> displayAllUser(@RequestBody Users users)
    {
        List<Users> usersList=usersRepository.findAll();
        return  new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<Users> getByEmailID(@PathVariable("email") String email)
    {
        Users users1=usersRepository.findByEmail(email);
        return new ResponseEntity<>(users1,HttpStatus.OK);
    }
    @PutMapping("/users/{email}")
    public ResponseEntity<Users> updateUser(@PathVariable("email") String email,@RequestBody Users users) throws ResourceNotFoundException
    {
        Users users1=usersRepository.findByEmail(email);
        if(users1==null)
        {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        users1.setEmail(users.getEmail());
        users1.setUser(users.getUser());
        users1.setPassword(users.getPassword());
        users1.setRole(users.getRole());
        return new ResponseEntity<>(usersRepository.save(users1),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginDetails(@RequestBody Users users)
    {

         String email= users.getEmail();
         String password = users.getPassword();
         System.out.println(email+"\n"+password);
         Users user=usersRepository.findByEmail(email);
        if(user==null)
        {
            Map<String, Object> response = new HashMap<>();
            response.put("value", "Not Found");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
         String role=user.getRole();
         String encryptedPassword=user.getPassword();
         boolean matched= passwordHashingService.verifyPassword(password,encryptedPassword);
         if(!matched)
         {
             Map<String, Object> response = new HashMap<>();
             response.put("value", false);
             return new ResponseEntity<>(response,HttpStatus.OK);
         }
        Map<String, Object> response = new HashMap<>();
        response.put("value", matched);
        response.put("role", role);

        return ResponseEntity.ok(response);
    }

}
