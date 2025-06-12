package de.ait.javalessons.controller;

import de.ait.javalessons.model.Book;
import de.ait.javalessons.model.Car;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
public class RestApiBookController {

    private List<Book> bookList = new ArrayList<>();

    public RestApiBookController() {
        bookList.addAll(List.of(

                new Book("1", "Война и мир", "Лев Толстой", 1869),
                new Book("2", "Преступление и наказание", "Фёдор Достоевский", 1866),
                new Book("3", "Мастер и Маргарита", "Михаил Булгаков", 1967),
                new Book("4", "Анна Каренина", "Лев Толстой", 1877),
                new Book("5", "Идиот", "Фёдор Достоевский", 1869),
                new Book("6", "Евгений Онегин", "Александр Пушкин", 1833),
                new Book("7", "Мёртвые души", "Николай Гоголь", 1842),
                new Book("8", "Отцы и дети", "Иван Тургенев", 1862),
                new Book("9", "Доктор Живаго", "Борис Пастернак", 1957),
                new Book("10", "Тихий Дон", "Михаил Шолохов", 1940)

        ));
    }

    @GetMapping
    public Iterable<Book> getBooks() {
        log.info("Getting all books");
        return bookList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                log.info("Book with id {} found", id);
                return ResponseEntity.ok(book);
            }
        }
        log.info("Book with id {} not found", id);
        return ResponseEntity.notFound().build();
    }




  @PostMapping
public ResponseEntity<Book> postBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
    
    if (bindingResult.hasErrors()) {
        log.warn("Validation errors for book: {}", bindingResult.getAllErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
        for (Book existingBook : bookList) {
            if (existingBook.getId().equals(book.getId())) {
                log.warn("Book with id {} already exists", book.getId());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        
        bookList.add(book);
        log.info("Book with id {} posted", book.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }




    @PutMapping("/{id}")
    public ResponseEntity<Book> putBook(@PathVariable String id, @RequestBody Book book) {
        
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId().equals(id)) {
                bookList.set(i, book);
                log.info("Book with id {} updated", id);
                return ResponseEntity.ok(book);
            }
        }
        
        bookList.add(book);
        log.info("Book with id {} created", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        boolean removed = bookList.removeIf(book -> book.getId().equals(id));
        if (removed) {
            log.info("Book with id {} deleted", id);
        } else {
            log.info("Book with id {} not found for deletion", id);
        }
        return ResponseEntity.ok().build();
    }
}

