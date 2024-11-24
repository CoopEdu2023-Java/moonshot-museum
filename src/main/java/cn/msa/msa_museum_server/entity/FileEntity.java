package cn.msa.msa_museum_server.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "file_entity")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_size", nullable = false)
    private Long fileSize;
    @Column(name = "upload_time", nullable = false)
    private LocalDateTime uploadTime;
}
