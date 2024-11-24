package cn.msa.msa_museum_server.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class FileEntity {

    private String fileName;

    private String fileUrl;

}
