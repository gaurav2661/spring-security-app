package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class MyController {
    @GetMapping
    public ResponseEntity<String> responseEntity() {
        return ResponseEntity.ok("Hello from Secured Endpoint");
    }
}
