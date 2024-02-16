package com.praesentia.praesentiaapi.service.Impl;

import com.praesentia.praesentiaapi.entity.Incident;
import com.praesentia.praesentiaapi.repository.IncidentRepository;
import com.praesentia.praesentiaapi.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class IncidentServiceImpl implements IncidentService {
    @Autowired
    IncidentRepository incidentRepository;

    @Override
    public List<Incident> findAll() {
        return null;
    }

    @Override
    public Optional<Incident> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Incident>> findAllByUserId(Long id) {
        return Optional.empty();
    }

    @Override
    public Incident save(Incident incident) {
        return incidentRepository.save(incident);
    }

    @Override
    public List<Incident> findAllByDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return null;
    }
}
