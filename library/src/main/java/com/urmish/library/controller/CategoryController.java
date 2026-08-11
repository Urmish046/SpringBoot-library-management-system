package com.urmish.library.controller;

import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.dto.CategoryRequestDTO;
import com.urmish.library.dto.CategoryResponseDTO;
import com.urmish.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategory(), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO cat = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cat);
    }

    @GetMapping("/categories/{id}/book")
    public ResponseEntity<List<BookResponseDTO>> getBooksByCategory(@PathVariable Long id){
       return new ResponseEntity<>(categoryService.getBooksByCategory(id),HttpStatus.OK);
    }

}
