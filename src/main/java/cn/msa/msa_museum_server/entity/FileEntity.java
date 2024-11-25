package cn.msa.msa_museum_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileEntity {

    private String fileName;

    private String fileUrl;

}
