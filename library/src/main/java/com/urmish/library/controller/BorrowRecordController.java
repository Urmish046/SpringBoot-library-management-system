package com.urmish.library.controller;

import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.dto.BorrowResponseDto;
import com.urmish.library.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BorrowRecordController {

    BorrowRecordService borrowRecordService;
    public BorrowRecordController(BorrowRecordService borrowRecordService){
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/borrowRecord/borrow/member/{id}/book/{bookId}")
    public ResponseEntity<BorrowResponseDto> borrowBook(@PathVariable Long id,
                                                        @PathVariable Long bookId){
        BorrowResponseDto borrowResponseDto = borrowRecordService.borrowBook(id, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowResponseDto);
    }

    @PutMapping("/borrowRecord/return/member/{id}/book/{bookId}")
    public ResponseEntity<BorrowResponseDto> returnBook(@PathVariable Long id,
                                                        @PathVariable Long bookId){
        BorrowResponseDto borrowResponseDto = borrowRecordService.returnBook(id, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(borrowResponseDto);
    }

    @GetMapping("/borrowRecord/check/book/{bookId}")
    public ResponseEntity<BookResponseDTO> checkAvailability(@PathVariable Long bookId){
        BookResponseDTO bookResponseDTO = borrowRecordService.checkAvailability(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDTO);
    }

    @GetMapping("/members/{id}/borrowed-books")
    public ResponseEntity<List<BorrowResponseDto>> borrowedBooks(@PathVariable Long id){
        List<BorrowResponseDto> borrowResponseDto = borrowRecordService.borrowedBooks(id);
        return ResponseEntity.status(HttpStatus.OK).body(borrowResponseDto);
    }

    @GetMapping("/book/{id}/history")
    public ResponseEntity<List<BorrowResponseDto>> bookHistory(@PathVariable Long id){
        List<BorrowResponseDto> history = borrowRecordService.bookHistory(id);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }
}
