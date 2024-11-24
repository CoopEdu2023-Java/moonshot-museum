package cn.msa.msa_museum_server.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class FileEntity {

    private String fileName;

    private String fileUrl;

}
