package de.ait.javalessons.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "company")
public class CompanyProperties {
    
    private String name;
    private String ceo;
    private int employeeCount;
    
    public CompanyProperties() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCeo() {
        return ceo;
    }
    
    public void setCeo(String ceo) {
        this.ceo = ceo;
    }
    
    public int getEmployeeCount() {
        return employeeCount;
    }
    
    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }
} 