package com.praesentia.praesentiaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {
    private Long id;
    private LocalDateTime recordIn;
    private LocalDateTime recordOut;
    private Long totalTime;

    public Long getTotalTime() {
        LocalDate day = recordIn.toLocalDate();

        if(recordIn == null || recordOut == null) return Duration.ofSeconds(0).getSeconds();
        return Duration.between(this.recordIn,this.recordOut).getSeconds();
    }
}
