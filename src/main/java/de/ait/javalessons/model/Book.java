package de.ait.javalessons.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Data
@Entity
public class Book {

    @Id
    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @Min(value = 1)
    private int publishYear;

}


