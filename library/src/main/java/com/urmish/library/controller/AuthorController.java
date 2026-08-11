package com.urmish.library.controller;

import com.urmish.library.model.Author;
import com.urmish.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthor(){
       return new ResponseEntity<>(authorService.getAuthor(), HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id){
        Author auth = authorService.getAuthById(id);
        if (auth != null){
            return new ResponseEntity<>(auth, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping ("/authors/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author,@PathVariable Long id){
        Author auth = authorService.updateAuthor(author,id);
        if (auth != null){
            return new ResponseEntity<>(auth, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> create(@RequestBody Author author){
        Author auth = authorService.createAuthor(author);
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id){
        Author delAuth = authorService.deleteAuthor(id);
        if (delAuth != null){
            return new ResponseEntity<>(delAuth, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
