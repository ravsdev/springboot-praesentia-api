package com.praesentia.praesentiaapi.service;

import com.praesentia.praesentiaapi.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordService {
    List<Record> findAll();
    Page<Record> findAll(Pageable page);
    Optional<Record> findById(Long id);
    Optional<List<Record>> findAllByUserId(Long id);
    Record save(Record record);
    Record update(Record record);
    void deleteById(Long id);
    Optional<Record> findLatestRecord(Long userId);
    List<Record> findAllByDate(LocalDateTime dateFrom, LocalDateTime dateTo);

    Optional<List<Record>> findAllByUserIdAndDate(Long userId, LocalDateTime dateFrom, LocalDateTime dateTo);
}
