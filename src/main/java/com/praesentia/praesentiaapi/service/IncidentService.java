package com.praesentia.praesentiaapi.service;

import com.praesentia.praesentiaapi.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IncidentService {
    List<Incident> findAll();
    //Page<Incident> findAll(Pageable page);
    Optional<Incident> findById(Long id);
    Optional<List<Incident>> findAllByUserId(Long id);
    Incident save(Incident incident);
    List<Incident> findAllByDate(LocalDateTime dateFrom, LocalDateTime dateTo);
}
