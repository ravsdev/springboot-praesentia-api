package com.praesentia.praesentiaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {
    private Long id;
    private LocalDateTime originalRecordStart;
    private LocalDateTime originalRecordEnd;
    private String reason;
    //private RecordDTO record;
}