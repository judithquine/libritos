package org.factoriaf5.libritos;

import org.factoriaf5.libritos.repositories.Book;
import org.factoriaf5.libritos.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class ApplicationTest {

        @Autowired
        MockMvc mockMvc;

        @Test
        void loadsTheHomePage() throws Exception {
                mockMvc.perform(get("/"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("home"));
        }


        @Autowired
        BookRepository bookRepository;

        @Test
        void returnsTheExistingBooks() throws Exception {

                Book book = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "fantasy"));

                mockMvc.perform(get("/books"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("books/all"))
                        .andExpect(model().attribute("books", hasItem(book)));
        }

        @BeforeEach
        void setUp() {
                bookRepository.deleteAll();
        }

        @Test
        void returnsAFormToAddNewBooks() throws Exception {
                mockMvc.perform(get("/books/new"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("books/new"));
        }
}