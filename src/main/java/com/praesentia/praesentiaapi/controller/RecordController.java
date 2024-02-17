package com.praesentia.praesentiaapi.controller;

import com.praesentia.praesentiaapi.dto.RecordDTO;
import com.praesentia.praesentiaapi.dto.UserDTO;
import com.praesentia.praesentiaapi.entity.Incident;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;
import com.praesentia.praesentiaapi.entity.User;
import com.praesentia.praesentiaapi.exceptions.ErrorMessage;
import com.praesentia.praesentiaapi.service.IncidentService;
import com.praesentia.praesentiaapi.service.RecordService;
import com.praesentia.praesentiaapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/records")
public class RecordController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private IncidentService incidentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @PreAuthorize("(#userId == authentication.principal.id) or hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<RecordDTO>> findAll(
            @RequestParam(name = "userid", required = false) Long userId,
            @RequestParam(name = "from", required = false) LocalDate from,
            @RequestParam(name = "to", required = false) LocalDate to) {

        List<Record> records;

        if(userId==null){
            records = (from!=null && to!=null)?
                    recordService.findAllByDate(LocalDateTime.of(from,LocalTime.MIN),LocalDateTime.of(to,LocalTime.MIN)):
                    recordService.findAll();
        }else {
            records = (from!=null && to!=null)?
                    recordService.findAllByUserIdAndDate(userId, LocalDateTime.of(from,LocalTime.MIN),LocalDateTime.of(to,LocalTime.MIN)).orElseGet(ArrayList::new):
                    recordService.findAllByUserId(userId).orElseGet(ArrayList::new);
        }

        if (records.isEmpty())
            return ResponseEntity.noContent().build();

        List<RecordDTO> recordsDTO = records.stream()
                .map(record -> modelMapper.map(record, RecordDTO.class))
                .toList();
        return ResponseEntity.ok(recordsDTO);
    }

    @GetMapping("{id}")
    //@PreAuthorize("(#id == authentication.principal.id) or hasRole('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long id,
                                                     Authentication authentication) {
        Optional<Record> record = recordService.findById(id);
        User authUser = (User) authentication.getPrincipal();

        if (record.isEmpty())
            return ResponseEntity.noContent().build();

        if(authUser.getRole() == Role.EMPLOYEE && !Objects.equals(authUser.getId(), record.get().getUser().getId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Permiso denegado.");

        return ResponseEntity.ok(modelMapper.map(record, RecordDTO.class));
    }

    @PostMapping("/start")
    public ResponseEntity<String> recordStart(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findById(authUser.getId());
        Optional<Record> lastRecord = recordService.findLatestRecord(authUser.getId());

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        if (lastRecord.isPresent() && lastRecord.get().getRecordEnd() == null)
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

        if (lastRecord.isPresent() && lastRecord.get().getRecordEnd() == null) {
            Record record = lastRecord.get();

            if (!record.getRecordStart().toLocalDate().isEqual(LocalDate.now()))
                record.setRecordEnd(LocalDateTime.of(record.getRecordStart().toLocalDate(), LocalTime.MAX));

            else record.setRecordEnd(LocalDateTime.now());

            recordService.save(record);

            return ResponseEntity.ok("Registro de salida creado con éxito.");
        } else {
            return ResponseEntity.badRequest().body("No se encuentra registro de entrada previo.");
        }
    }

    //TO-DO
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                         @RequestBody RecordDTO recordDTO,
                                         Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Optional<Record> record = recordService.findById(id);

        if (record.isEmpty())
            return ResponseEntity.notFound().build();

        if(!Objects.equals(authUser.getId(), record.get().getUser().getId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Permiso denegado.");

        if(record.get().getIncident() != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El registro "+record.get().getId()+" ya ha sido modificado previamente.");

        if(recordDTO.getId() == null)
            recordDTO.setId(id);

        Record updateRecord = recordService.update(modelMapper.map(recordDTO, Record.class));

        Incident incident = new Incident();
        incident.setOriginalRecordStart(record.get().getRecordStart());
        incident.setOriginalRecordEnd(record.get().getRecordEnd());
        incident.setRecord(record.get());

        incidentService.save(incident);


        return ResponseEntity.ok(modelMapper.map(updateRecord, RecordDTO.class));
    }

}
