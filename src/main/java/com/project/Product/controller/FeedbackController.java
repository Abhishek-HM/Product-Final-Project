package com.project.Product.controller;

import com.project.Product.model.Feedback;
import com.project.Product.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="http://localhost:5173/")
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackRepository;

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback)
    {
        Feedback feedback1=feedbackRepository.save(new Feedback(feedback.getEmail(),feedback.getName(),feedback.getFeedback()));
        return  new ResponseEntity<>(feedback1, HttpStatus.CREATED);
    }
    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getFeedback()
    {
        List<Feedback> feedbackList=feedbackRepository.findAll();
        return new ResponseEntity<>(feedbackList,HttpStatus.OK);
    }
}
