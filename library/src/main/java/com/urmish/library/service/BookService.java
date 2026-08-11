package com.urmish.library.service;

import com.urmish.library.dto.BookRequestDTO;
import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.model.Author;
import com.urmish.library.model.Book;
import com.urmish.library.model.Category;
import com.urmish.library.repository.AuthorRepository;
import com.urmish.library.repository.BookRepository;
import com.urmish.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<BookResponseDTO> getBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found!"));
        Book book = Book.builder()
                .title(bookRequestDTO.getTitle())
                .isbn(bookRequestDTO.getIsbn())
                .author(author)
                .build();
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book should not be empty!"));
        return mapToDto(book);
    }

    public BookResponseDTO updateBook(BookRequestDTO bookRequestDTO, Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book cannot be empty"));
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found!"));
        existingBook.setTitle(bookRequestDTO.getTitle());
        existingBook.setIsbn(bookRequestDTO.getIsbn());
        existingBook.setAuthor(author);
        Book updatedBook = bookRepository.save(existingBook);
        return mapToDto(updatedBook);
    }

    public Book deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found!"));
        bookRepository.delete(book);
        return book;
    }

    public BookResponseDTO mapToDto(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedDate(book.getPublishedDate());
        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }
        if (book.getCategories() != null) {
            dto.setCategories(book.getCategories().stream()
                    .map(Category::getName)
                    .toList());
        }
        return dto;
    }

    public BookResponseDTO assignCategory(Long bookId, Long id) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book should not be null!"));
        Category cat = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Category should not be empty"));

        book.getCategories().add(cat);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    public BookResponseDTO deleteBookCategory(Long bookId, Long id) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book should not be null!"));
        Category cat = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Category should not be empty"));
        book.getCategories().remove(cat);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }
}