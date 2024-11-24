package cn.msa.msa_museum_server.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.entity.FileEntity;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;

@Service
public class FileService {

    private final String uploadDir = ""; // 存储路径

    public FileEntity upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ExceptionEnum.EMPTY_FILE);
        }

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // 文件名
            Path filePath = Paths.get(uploadDir, fileName); // 文件路径

            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath);

            return new FileEntity(fileName, filePath.toString());

        } catch (Exception e) {
            throw new BusinessException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }

    public FileDto getFileDetails(FileEntity fileEntity) {
        String fileUrl = "/files/" + fileEntity.getFileName();
        return new FileDto(fileEntity.getFileName(), fileUrl);
    }
}