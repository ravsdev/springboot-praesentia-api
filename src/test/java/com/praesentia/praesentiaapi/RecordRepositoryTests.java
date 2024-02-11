package com.praesentia.praesentiaapi;

import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.repository.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
//@ActiveProfiles("test")
public class RecordRepositoryTests {
    @Autowired
    private RecordRepository recordRepository;


    @Test
    public void shouldReturnRecordsByUserId(){

        Optional<List<Record>> records = recordRepository.findAllByUserId(2L);

        Assertions.assertTrue(records.isPresent());
        Assertions.assertTrue(records.get().size()>0);
		/*
		Optional<List<Record>> records2 = recordRepository.findAllByUserId(1L);
		Assertions.assertEquals(0,records2.get().size());*/
    }
}
