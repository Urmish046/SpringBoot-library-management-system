package com.urmish.library.controller;

import com.urmish.library.dto.BookRequestDTO;
import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.model.Book;
import com.urmish.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDTO>> getBooks(){
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody BookRequestDTO bookRequestDTO){
        BookResponseDTO book = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id){
        BookResponseDTO bookResponseDTO= bookService.getBookById(id);
        return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookRequestDTO bookRequestDTO, @PathVariable Long id){
        BookResponseDTO book = bookService.updateBook(bookRequestDTO, id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Book delBook = bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/books/{bookId}/categories/{id}")
    public ResponseEntity<BookResponseDTO> assignCategory(@PathVariable Long bookId,
                                                          @PathVariable Long id){
        BookResponseDTO book = bookService.assignCategory(bookId, id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/books/{bookId}/categories/{id}")
    public ResponseEntity<BookResponseDTO> deleteCategory(@PathVariable Long bookId,
                                                          @PathVariable Long id){
        BookResponseDTO book = bookService.deleteBookCategory(bookId, id);
        return ResponseEntity.ok(book);
    }


}
