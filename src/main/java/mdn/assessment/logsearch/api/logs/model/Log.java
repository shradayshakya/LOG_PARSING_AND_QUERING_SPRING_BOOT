package mdn.assessment.logsearch.api.logs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity(name = "LOGS")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    private String message;

    @Column(name = "time")
    private LocalDateTime time;
}
