package com.praesentia.praesentiaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {
    private Long id;
    private LocalDateTime recordIn;
    private LocalDateTime recordOut;
    private String totalTime;

    public String getTotalTime() {
        //LocalDate in = recordIn.toLocalDate();
        if(recordOut == null) return String.valueOf(Duration.ofSeconds(0).toSeconds());

        Duration result = Duration.between(this.recordIn,this.recordOut);

        return String.format("%02d:%02d:%02d",
                result.toHoursPart(),
                result.toMinutesPart(),
                result.toSecondsPart());

    }
}
