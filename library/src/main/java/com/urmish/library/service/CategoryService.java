package com.urmish.library.service;

import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.dto.CategoryRequestDTO;
import com.urmish.library.dto.CategoryResponseDTO;
import com.urmish.library.model.Book;
import com.urmish.library.model.Category;
import com.urmish.library.repository.BookRepository;
import com.urmish.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    BookService bookService;

    public CategoryService(CategoryRepository categoryRepository, BookService bookService){
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
    }

    public List<CategoryResponseDTO> getCategory(){
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new RuntimeException("Category with this name already exists!");
        }
        Category cat = categoryRepository.save(mapToEntity(categoryRequestDTO));
        return mapToDto(cat);
    }

    public List<BookResponseDTO> getBooksByCategory(Long id) {
        Category cat = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Category should not be empty"));
       return cat.getBooks().stream().map(bookService::mapToDto).toList();
    }

   private Category mapToEntity(CategoryRequestDTO categoryRequestDTO){
       Category category = new Category();
       category.setName(categoryRequestDTO.getName());
       return category;
   }

   private CategoryResponseDTO mapToDto(Category category){
       CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
       categoryResponseDTO.setName(category.getName());
       categoryResponseDTO.setId(category.getId());
       return categoryResponseDTO;
   }


}
