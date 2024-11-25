package cn.msa.msa_museum_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FileMetadataEntity {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String type;

    private String path;

    private long size;

    public FileMetadataEntity(String name, String type, String path, long size) {
        this.name = name;
        this.type = type;
        this.path = path;
        this.size = size;
    }
}