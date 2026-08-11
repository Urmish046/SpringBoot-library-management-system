package com.urmish.library.service;
import com.urmish.library.dto.BookResponseDTO;
import com.urmish.library.dto.BorrowResponseDto;
import com.urmish.library.model.Book;
import com.urmish.library.model.BorrowRecord;
import com.urmish.library.model.Category;
import com.urmish.library.model.Member;
import com.urmish.library.repository.BookRepository;
import com.urmish.library.repository.BorrowRecordRepository;
import com.urmish.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRecordService {

    BorrowRecordRepository borrowRecordRepository;
    MemberRepository memberRepository;
    BookRepository bookRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, MemberRepository memberRepository
            , BookRepository bookRepository){
        this.borrowRecordRepository = borrowRecordRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public BorrowResponseDto borrowBook(Long id, Long bookId) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member does not exist"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book does not exist"));

        List<BorrowRecord> books = borrowRecordRepository.findByMemberIdAndReturnedDateIsNull(id);

        if (books.size() >= 3){
            throw new RuntimeException("This book has already been borrowed!");
        }

        if (!borrowRecordRepository.existsByBookAndReturnedDateIsNull(book)){
           BorrowRecord borrowRecord = new BorrowRecord();
           borrowRecord.setBook(book);
           borrowRecord.setMember(member);
           borrowRecord.setBorrowedDate(LocalDate.now());

           BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);
           return mapToDto(savedRecord);
       }else {
           throw new RuntimeException("Book is already borrowed!");
       }
    }

    public BorrowResponseDto returnBook(Long id, Long bookId) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member does not exist"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book does not exist"));

        if (borrowRecordRepository.existsByBookAndReturnedDateIsNull(book)){
          BorrowRecord existingRecord = borrowRecordRepository.findByBookAndReturnedDateIsNull(book);

            if(!existingRecord.getMember().getId().equals(id)) {
                throw new RuntimeException("This book was borrowed by someone else!");
            }

            existingRecord.setReturnedDate(LocalDate.now());
            BorrowRecord updateRecord = borrowRecordRepository.save(existingRecord);
            return mapToDto(updateRecord);

        }
        else {
            throw new RuntimeException("Book has already been returned or was never borrowed!");
        }
    }

    public BookResponseDTO checkAvailability(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book does not exist"));
        if (borrowRecordRepository.existsByBookAndReturnedDateIsNull(book)) {
            throw new RuntimeException("Book is already borrowed");
        }else {
            return mapToDtoBook(book);
        }
    }

    public List<BorrowResponseDto> borrowedBooks(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Member does not exist"));
        List<BorrowResponseDto> books = borrowRecordRepository.findByMemberIdAndReturnedDateIsNull(id).stream().map(this::mapToDto).toList();
        if (!books.isEmpty()){
            return books;
        }else {
            throw new RuntimeException("No books found!");
        }
    }

    public List<BorrowResponseDto> bookHistory(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book does not exist"));
        return borrowRecordRepository.findByBookId(id).stream().map(this::mapToDto).toList();
    }

    public BorrowResponseDto mapToDto(BorrowRecord record){
        BorrowResponseDto dto = new BorrowResponseDto();
        dto.setBookId(record.getId());
        dto.setMemberId(record.getMember().getId());
        dto.setBookId(record.getBook().getId());
        dto.setMemberName(record.getMember().getName());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setBorrowedDate(record.getBorrowedDate());
        dto.setReturnedDate(record.getReturnedDate());

        if (record.getReturnedDate() == null){
            LocalDate dueDate = record.getBorrowedDate().plusDays(14);
            dto.setOverdue(LocalDate.now().isAfter(dueDate));
        }else {
            dto.setOverdue(false);
        }
        return dto;
    }

    public BookResponseDTO mapToDtoBook(Book book) {
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



}
