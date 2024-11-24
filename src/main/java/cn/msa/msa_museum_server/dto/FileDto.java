package cn.msa.msa_museum_server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {
    private final String fileName;
    private final Long fileSize;
    private final String uploadTime;
}
