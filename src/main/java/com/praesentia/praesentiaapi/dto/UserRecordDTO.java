package com.praesentia.praesentiaapi.dto;

import com.praesentia.praesentiaapi.entity.Record;
import com.praesentia.praesentiaapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;
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
    /*private String totalTime;
    public String getTotalTime() {

        Long seconds = records.stream().reduce(0L, (acc, record) -> {
            return acc + Duration.between(record.getRecordStart(), record.getRecordEnd()).toSeconds();
        }, Long::sum);

        Duration result = Duration.ofSeconds(seconds);

        return String.format("%02d:%02d:%02d",
                result.toHoursPart(),
                result.toMinutesPart(),
                result.toSecondsPart());
    }*/
}
