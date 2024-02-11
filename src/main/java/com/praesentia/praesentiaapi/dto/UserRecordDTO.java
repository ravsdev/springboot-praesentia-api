package com.praesentia.praesentiaapi.dto;

import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String dni;
    private Role role;
    private boolean enabled;
    private List<RecordDTO> records = new ArrayList<>();
/*
    public List<RecordDTO> getRecords() {
        return records.stream().map(
                record->RecordDTO.builder()
                        .id(record.getId())
                        .recordIn(record.getRecordIn())
                        .recordOut(record.getRecordOut())
                        .build())
                .toList();

    }*/
}
