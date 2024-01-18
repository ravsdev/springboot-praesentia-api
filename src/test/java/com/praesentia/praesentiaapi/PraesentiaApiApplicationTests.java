package com.praesentia.praesentiaapi;

import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.repository.RecordRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class PraesentiaApiApplicationTests {

	@Test
	void contextLoads() {
	}

}