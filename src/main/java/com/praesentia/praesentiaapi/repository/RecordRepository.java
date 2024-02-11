package com.praesentia.praesentiaapi.repository;

import com.praesentia.praesentiaapi.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT * FROM records WHERE user_id=:userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Record> findLatestRecord(@Param("userId") Long userId);

    //@Query("SELECT r FROM Record r WHERE user_id=:userId")
    Optional<List<Record>> findAllByUserId(Long id);
    //@Query(value = "SELECT * FROM records WHERE record", nativeQuery = true)
    List<Record> findAllByRecordStartBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

}
