package com.urmish.library.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowResponseDto {

    private Long memberId;
    private Long bookId;
    private String bookTitle;
    private String memberName;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private boolean overdue;
}
