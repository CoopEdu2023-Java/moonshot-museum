package cn.msa.msa_museum_server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;
}
