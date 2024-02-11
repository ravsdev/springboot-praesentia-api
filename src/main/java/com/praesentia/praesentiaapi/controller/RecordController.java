package com.praesentia.praesentiaapi.controller;

import com.praesentia.praesentiaapi.dto.RecordDTO;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.service.RecordService;
import com.praesentia.praesentiaapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/records")
public class RecordController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @PreAuthorize("(#userId == authentication.principal.id) or hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<RecordDTO>> findAll(@RequestParam(name = "userid", required = false) Long userId,Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();

        List<Record> records = new ArrayList<>();
        if(userId==null) records = recordService.findAll();
        else recordService.findAllByUserId(userId);

        if (records.isEmpty())
            return ResponseEntity.noContent().build();

        List<RecordDTO> recordsDTO = records.stream()
                .map(record -> modelMapper.map(record, RecordDTO.class))
                .toList();
        return ResponseEntity.ok(recordsDTO);
    }

    @GetMapping("{id}")
    @PreAuthorize("(#id == authentication.principal.id) or hasRole('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<RecordDTO> findById(@PathVariable(name = "id") Long id,
                                                     Authentication authentication) {
        Optional<Record> record = recordService.findById(id);

        if (record.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(modelMapper.map(record, RecordDTO.class));
    }

    @PostMapping("/start")
    public ResponseEntity<String> recordStart(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());
        Optional<Record> lastRecord = recordService.findLatestRecord(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        if (lastRecord.isPresent() && lastRecord.get().getRecordOut() == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Hay un registro sin finalizar.");

        Record record = Record.builder()
                .user(user.get())
                .build();

        recordService.save(record);

        return ResponseEntity.ok("Registro de entrada creado con éxito.");
    }

    @PostMapping("/end")
    public ResponseEntity<String> recordEnd(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());
        Optional<Record> lastRecord = recordService.findLatestRecord(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        if (lastRecord.isPresent() && lastRecord.get().getRecordOut() == null) {
            Record record = lastRecord.get();

            if (!record.getRecordIn().toLocalDate().isEqual(LocalDate.now()))
                record.setRecordOut(LocalDateTime.of(record.getRecordIn().toLocalDate(), LocalTime.MAX));

            else record.setRecordOut(LocalDateTime.now());

            recordService.save(record);

            return ResponseEntity.ok("Registro de salida creado con éxito.");
        } else {
            return ResponseEntity.badRequest().body("No se encuentra registro de entrada previo.");
        }
    }

    //TO-DO
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody RecordDTO recordDTO, Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Record> latestRecord = recordService.findLatestRecord(user.get().getId());

        if (latestRecord.isEmpty())
            return ResponseEntity.badRequest().build();

        //latestRecord.get().getRecordOut() != null)
        Record record = Record.builder()
                .user(user.get())
                .build();
        recordService.update(record);

        return ResponseEntity.ok("Registro de entrada creado con éxito.");
    }

}
