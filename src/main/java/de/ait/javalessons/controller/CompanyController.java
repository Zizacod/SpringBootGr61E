package de.ait.javalessons.controller;

import de.ait.javalessons.model.CompanyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {
    
    private final CompanyProperties companyProperties;
    
    @Autowired
    public CompanyController(CompanyProperties companyProperties) {
        this.companyProperties = companyProperties;
    }

    @GetMapping("/company")
    public ResponseEntity<CompanyProperties> getCompanyInfo() {
        return ResponseEntity.ok(companyProperties);
    }
} 