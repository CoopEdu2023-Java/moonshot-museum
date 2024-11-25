package cn.msa.msa_museum_server.controller;

import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.service.FileService;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public ResponseDto<Page<FileDto>> getFileList(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        if(page < 0 || size <= 0) {
            throw new BusinessException(ExceptionEnum.INVALID_ENTRY);
        }
        Pageable pageable = PageRequest.of(page, size);
        return fileService.getFileList(pageable);
    }

}