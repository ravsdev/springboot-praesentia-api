package com.praesentia.praesentiaapi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String dni;
    private Role role;
    private boolean enabled;
    private List<Record> records;
    /*
    private List<Map<String, Object>> recordsFiltered;

    //TO-DO remove user field
    public List<Map<String, Object>> getrecordsFiltered() {
        return records.stream().map(record->{
            Map<String, Object> fil = new HashMap<>();
            fil.put("id", record.getUser().getId());
            return fil;
        }).toList();
    }*/
}
