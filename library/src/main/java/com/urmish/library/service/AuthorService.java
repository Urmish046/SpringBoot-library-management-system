package com.urmish.library.service;
import com.urmish.library.model.Author;
import com.urmish.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAuthor() {
        return authorRepository.findAll();
    }

    public Author getAuthById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author updateAuthor(Author author, Long id) {
        Author author1 = authorRepository.findById(id).orElse(null);
        if (author1 != null) {
            author1.setName(author.getName());
            author1.setBio(author.getBio());
            authorRepository.save(author1);
        } else {
            return null;
        }
        return author1;
    }

    public Author deleteAuthor(Long id) {
        Author auth = authorRepository.findById(id).orElse(null);
        if (auth != null) {
            authorRepository.delete(auth);
            return auth;
        } else {
            return null;
        }
    }


    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}
