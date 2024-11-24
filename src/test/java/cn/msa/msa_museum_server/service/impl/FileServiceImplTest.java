package cn.msa.msa_museum_server.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import cn.msa.msa_museum_server.config.FileProperties;
import cn.msa.msa_museum_server.dto.FileMetadataDto;
import cn.msa.msa_museum_server.entity.FileMetadataEntity;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.FileMetadataRepository;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private FileMetadataRepository fileMetadataRepository;

    @TempDir
    private Path storageLocation;

    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        FileProperties fileProperties = new FileProperties();
        fileProperties.setStorageLocation(storageLocation.toString());
        fileService = new FileServiceImpl(fileProperties, fileMetadataRepository);
    }

    private Path createTestFile(FileMetadataEntity fileMetadataEntity, String content) throws IOException {
        Path filePath = storageLocation.resolve(fileMetadataEntity.getPath());
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);
        Files.writeString(filePath, content);
        return filePath;
    }

    @Test
    public void testGetFileMetadata_FileNotFound() {
        String fileId = "nonexistent-file-id";
        when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.getFileMetadata(fileId);
        });

        assertEquals(ExceptionEnum.FILE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testGetFileMetadata_Success() throws IOException {
        String fileId = "existing-file-id";
        FileMetadataEntity fileMetadataEntity = new FileMetadataEntity(
                "test-file",
                "text/plain",
                "test-file.txt");
        fileMetadataEntity.setId(fileId);

        createTestFile(fileMetadataEntity, "test content");

        when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.of(fileMetadataEntity));

        FileMetadataDto fileMetadataDto = fileService.getFileMetadata(fileId);

        assertEquals(fileId, fileMetadataDto.getId());
        assertEquals("test-file", fileMetadataDto.getName());
        assertEquals("text/plain", fileMetadataDto.getType());
        assertEquals("12 B", fileMetadataDto.getSize());
        assertEquals("/files/" + fileId + "/content", fileMetadataDto.getUrl());
    }

    @Test
    public void testGetFileContent_FileNotFound() {
        String fileId = "nonexistent-file-id";
        when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.getFileContent(fileId);
        });

        assertEquals(ExceptionEnum.FILE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testGetFileContent_Success() throws IOException {
        String fileId = "existing-file-id";
        FileMetadataEntity fileMetadataEntity = new FileMetadataEntity(
                "test-file",
                "text/plain",
                "test-file.txt");
        fileMetadataEntity.setId(fileId);

        Path filePath = createTestFile(fileMetadataEntity, "test content");

        when(fileMetadataRepository.findById(fileId)).thenReturn(Optional.of(fileMetadataEntity));

        Resource resource = fileService.getFileContent(fileId);

        assertInstanceOf(UrlResource.class, resource);
        assertEquals(filePath.toUri(), resource.getURI());
    }
}