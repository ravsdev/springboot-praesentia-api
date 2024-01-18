package com.praesentia.praesentiaapi.service.Impl;

import com.praesentia.praesentiaapi.dto.RecordDTO;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.repository.RecordRepository;
import com.praesentia.praesentiaapi.service.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Record> findAll(Pageable page) {
        return recordRepository.findAll(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Record> findById(Long id) {
        return recordRepository.findById(id);
    }

    @Override
    public Record save(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public Record update(Record record) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        recordRepository.deleteById(id);
    }

    @Override
    public Optional<Record> findLatestRecord(Long userId) {
        return recordRepository.findLatestRecord(userId);
    }
}
