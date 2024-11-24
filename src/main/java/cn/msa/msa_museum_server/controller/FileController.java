package cn.msa.msa_museum_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.msa.msa_museum_server.dto.FileMetadataDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}/metadata")
    public ResponseDto<FileMetadataDto> getFileMetadata(@PathVariable String id) {
        return new ResponseDto<>(fileService.getFileMetadata(id));
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<Resource> getFileContent(
            // @RequestHeader(value = "Range", required = false) String range,
            @PathVariable String id) {
        FileMetadataDto fileMetadataDto = fileService.getFileMetadata(id);
        Resource resource = fileService.getFileContent(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(fileMetadataDto.getType()))
                .body(resource);
    }
}
