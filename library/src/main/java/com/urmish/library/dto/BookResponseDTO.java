package com.urmish.library.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookResponseDTO {
        private Long id;
        private String title;
        private String isbn;
        private LocalDate publishedDate;
        private String authorName;
        private List<String> categories;

}

