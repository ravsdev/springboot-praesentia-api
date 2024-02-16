package com.praesentia.praesentiaapi.dto;

import com.praesentia.praesentiaapi.entity.Incident;
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
    private LocalDateTime recordStart;
    private LocalDateTime recordEnd;
    private String totalTime;
    private IncidentDTO incident;
    public String getTotalTime() {
        //LocalDate in = recordIn.toLocalDate();
        if(recordEnd == null) return String.valueOf(Duration.ofSeconds(0).toSeconds());

        Duration result = Duration.between(this.recordStart,this.recordEnd);

        return String.format("%02d:%02d:%02d",
                result.toHoursPart(),
                result.toMinutesPart(),
                result.toSecondsPart());

    }
}
