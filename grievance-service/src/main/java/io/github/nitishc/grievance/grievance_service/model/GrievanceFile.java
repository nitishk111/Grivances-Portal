package io.github.nitishc.grievance.grievance_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class GrievanceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate uploadedAt;

    private String userEmail;

    private String FileName;

    private String FileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
}
