package com.praesentia.praesentiaapi.controller;

import com.praesentia.praesentiaapi.dto.RecordDTO;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.service.RecordService;
import com.praesentia.praesentiaapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<RecordDTO>> findAll(){
        List<Record> records = recordService.findAll();
        if (records.isEmpty())
            return ResponseEntity.noContent().build();
        List<RecordDTO> recordsDTO = records.stream()
                .map(record -> modelMapper.map(record, RecordDTO.class))
                .toList();
        return ResponseEntity.ok(recordsDTO);
    }

    @PostMapping()
    public ResponseEntity<String> create(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        Record record = Record.builder()
                .user(user.get())
                .build();
        recordService.save(record);

        return ResponseEntity.ok("Registro de entrada creado con éxito.");
    }

    //TO-DO
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody RecordDTO recordDTO,Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Record> latestRecord = recordService.findLatestRecord(user.get().getId());

        if(latestRecord.isEmpty())
            return ResponseEntity.badRequest().build();

        //latestRecord.get().getRecordOut() != null)
        Record record = Record.builder()
                .user(user.get())
                .build();
        recordService.update(record);

        return ResponseEntity.ok("Registro de entrada creado con éxito.");
    }

}
