package cn.msa.msa_museum_server.service.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import cn.msa.msa_museum_server.config.FileProperties;
import cn.msa.msa_museum_server.dto.FileMetadataDto;
import cn.msa.msa_museum_server.dto.FileRequestTypeDto;
import cn.msa.msa_museum_server.entity.FileMetadataEntity;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.FileMetadataRepository;
import cn.msa.msa_museum_server.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        FileMetadataEntity fileMetadataEntity = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));

        Path filePath = storageLocation.resolve(fileMetadataEntity.getPath());

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new BusinessException(ExceptionEnum.FILE_NOT_FOUND);
        }

        long size = fileMetadataEntity.getSize();

        return new FileMetadataDto(
                id,
                fileMetadataEntity.getName(),
                fileMetadataEntity.getType(),
                formatFileSize(size),
                "/files/" + id + "/content");
    }

    @Override
    public Resource getFileContent(String id) {
        FileMetadataEntity fileMetadataEntity = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));

        Path filePath = storageLocation.resolve(fileMetadataEntity.getPath());

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.isReadable()) {
                throw new BusinessException(ExceptionEnum.FILE_NOT_FOUND);
            }

            return resource;
        } catch (MalformedURLException e) {
            log.error("Error retrieving file content: {}", e.getMessage());
            throw new BusinessException(ExceptionEnum.MALFORMED_FILE_PATH);
        }
    }

    @Override
    public boolean supportFileRequestType(String id, FileRequestTypeDto requestType) {
        FileMetadataEntity fileMetadataEntity = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));

        switch (fileMetadataEntity.getType()) {
            case "image/jpeg":
            case "image/png":
            case "image/webp":
            case "application/pdf":
                return fileMetadataEntity.getSize() <= 10485760 && requestType == FileRequestTypeDto.FULL;
            case "audio/mpeg":
            case "audio/ogg":
            case "audio/wav":
            case "video/mp4":
                return requestType == FileRequestTypeDto.RANGE;
            default:
                return false;
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