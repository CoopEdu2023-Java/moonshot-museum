package cn.msa.msa_museum_server.service;

import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileService {
    ResponseDto<Page<FileDto>> getFileList(Pageable pageable);
}