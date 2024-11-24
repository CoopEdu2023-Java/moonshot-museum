package cn.msa.msa_museum_server.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import cn.msa.msa_museum_server.config.FileProperties;
import cn.msa.msa_museum_server.dto.FileMetadataDto;
import cn.msa.msa_museum_server.entity.FileMetadataEntity;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.FileMetadataRepository;
import cn.msa.msa_museum_server.service.FileService;

public class FileServiceImpl implements FileService {

    // The location where files are stored
    private final Path storageLocation;

    private final FileMetadataRepository fileMetadataRepository;

    public FileServiceImpl(FileProperties properties, FileMetadataRepository fileMetadataRepository) {
        if (properties.getStorageLocation().trim().length() == 0) {
            throw new RuntimeException("File upload location can not be Empty.");
        }

        this.storageLocation = Paths.get(properties.getStorageLocation());
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @Override
    public FileMetadataDto getFileMetadata(String id) {
        try {
            FileMetadataEntity fileMetadataEntity = fileMetadataRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));

            Path filePath = storageLocation.resolve(fileMetadataEntity.getPath());

            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                throw new BusinessException(ExceptionEnum.FILE_NOT_FOUND);
            }

            long size = Files.size(filePath);

            return new FileMetadataDto(
                    id,
                    fileMetadataEntity.getName(),
                    fileMetadataEntity.getType(),
                    formatFileSize(size),
                    "/files/" + id + "/content");
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving file metadata: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource getFileContent(String id) {
        try {
            FileMetadataEntity fileMetadataEntity = fileMetadataRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));

            Path filePath = storageLocation.resolve(fileMetadataEntity.getPath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.isReadable()) {
                throw new BusinessException(ExceptionEnum.FILE_NOT_FOUND);
            }

            return resource;
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving file content: " + e.getMessage(), e);
        }
    }

    private String formatFileSize(long size) {
        if (size < 1024)
            return size + " B";
        int exp = (int) (Math.log(size) / Math.log(1024));
        String prefix = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", size / Math.pow(1024, exp), prefix);
    }

}
