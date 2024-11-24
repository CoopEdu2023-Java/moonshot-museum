package cn.msa.msa_museum_server.service;

import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public void upload(MultipartFile file) throws Exception {
        file.transferTo(Paths.get("public", file.getOriginalFilename()).toAbsolutePath());
    }
}