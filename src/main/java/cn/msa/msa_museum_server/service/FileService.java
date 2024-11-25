package cn.msa.msa_museum_server.service;

import org.springframework.core.io.Resource;

import cn.msa.msa_museum_server.dto.FileMetadataDto;
import cn.msa.msa_museum_server.dto.FileRequestTypeDto;

public interface FileService {
    FileMetadataDto getFileMetadata(String id);

    Resource getFileContent(String id);

    boolean supportFileRequestType(String id, FileRequestTypeDto requestType);
}
