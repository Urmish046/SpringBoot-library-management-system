package com.urmish.library.repository;

import com.urmish.library.model.Book;
import com.urmish.library.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    boolean existsByBookAndReturnedDateIsNull(Book book);
    BorrowRecord findByBookAndReturnedDateIsNull(Book book);
    List<BorrowRecord> findByMemberIdAndReturnedDateIsNull(Long memberId);
    List<BorrowRecord> findByBookId(Long id);
    boolean findByBookAndBookNumberLessThanFour(Book book);

}
