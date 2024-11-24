package cn.msa.msa_museum_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseDto<FileDto> upload(@RequestBody MultipartFile file) throws Exception {
        fileService.upload(file);
    }
}