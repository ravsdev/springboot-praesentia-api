package com.praesentia.praesentiaapi.dto;

import java.util.ArrayList;
import java.util.List;

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
   // private List<Record> records = new ArrayList<>();

    //TO-DO remove user field
   // public List<Record> getRecords() {
        //return records.st;
    //}
}
