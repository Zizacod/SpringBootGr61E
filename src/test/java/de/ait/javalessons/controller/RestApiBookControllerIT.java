package de.ait.javalessons.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.javalessons.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(RestApiBookController.class)
class RestApiBookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooksShouldReturnListOfBooks() throws Exception {
        //Отправляем HTTP запрос на /books
        var result = mockMvc.perform(get("/books"))
                .andReturn();

        //Проверяем что статус 200 OK
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Book> books = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>(){}
        );
        assertThat(books).hasSize(10);
        assertThat(books.getFirst().getTitle()).isEqualTo("Война и мир");
        assertThat(books.getLast().getTitle()).isEqualTo("Тихий Дон");

    }

    @Test
    void getBookByIdShouldReturnCurrentBook() throws Exception {
        //Отправляем HTTP запрос на /books/1
        var result = mockMvc.perform(get("/books/1"))
                .andReturn();

        //Проверяем что статус 200 OK
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        //Читаем JSON и превращаем в обьект класса Book
        Book book = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);

        //Проверяем поля полученной книги
        assertThat(book.getId()).isEqualTo("1");
        assertThat(book.getTitle()).isEqualTo("Война и мир");
        assertThat(book.getAuthor()).isEqualTo("Лев Толстой");
        assertThat(book.getYear()).isEqualTo(1869);


    }

    @Test
    void getBookByIdShouldReturnEmpty() throws Exception {
        var result = mockMvc.perform(get(("/books/9999")))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }



    @Test
    void postBookShouldAddNewBook() throws Exception{
        //Создаем новую книгу
        Book book = new Book("20", "Test Book", "Test Author", 2025);

        //Post запрос на /books c книгой в теле метода
        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)//Тип содержимого
                        .content(objectMapper.writeValueAsString(book))) //Преобразуем Java обьект в JSON
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        Book bookFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);

        assertThat(bookFromResponse.getId()).isEqualTo(book.getId());
        assertThat(bookFromResponse.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookFromResponse.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(bookFromResponse.getYear()).isEqualTo(book.getYear());
    }

    @Test
    void deleteBookShouldRemoveBook() throws Exception{
        //удаляем существующую книгу
        mockMvc.perform(delete("/books/2"))
                .andReturn();

        //Проверяем удалилась ли книга
        var result = mockMvc.perform(get("/books/2"))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }









@Test
    void postBookWithInvalidDataShouldReturnBadRequest() throws Exception {
        // Создаем книгу с невалидными данными
        Book book = new Book("", "", "", -1);

        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void postBookWithDuplicateIdShouldReturnConflict() throws Exception {
        // Создаем книгу с уже существующим ID
        Book book = new Book("1", "Test Book", "Test Author", 2025);

        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }





    @Test
    void putBookShouldUpdateExistingBook() throws Exception {
        Book updatedBook = new Book("1", "Updated Title", "Updated Author", 2000);

        var result = mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        Book bookFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);
        assertThat(bookFromResponse.getTitle()).isEqualTo("Updated Title");
        assertThat(bookFromResponse.getAuthor()).isEqualTo("Updated Author");
    }

    @Test
    void putBookShouldCreateNewBookIfNotExists() throws Exception {
        Book newBook = new Book("999", "New Book", "New Author", 2025);

        var result = mockMvc.perform(put("/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        Book bookFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);
        assertThat(bookFromResponse.getId()).isEqualTo("999");
        assertThat(bookFromResponse.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookWithNonExistentIdShouldReturnOk() throws Exception {
        var result = mockMvc.perform(delete("/books/9999"))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void postBookWithMinimumValidDataShouldWork() throws Exception {
        Book book = new Book("30", "T", "A", 1);

        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }






}