package cn.msa.msa_museum_server.service.impl;

import cn.msa.msa_museum_server.dto.MultipleFilesDto;
import cn.msa.msa_museum_server.entity.UploadedFile;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.UploadedFileRepository;
import cn.msa.msa_museum_server.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UploadedFileRepository fileRepository;

    public FileUploadServiceImpl(UploadedFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public MultipleFilesDto uploadMultipleFiles(List<MultipartFile> files) {
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // 构建文件路径
                String filePath = uploadDir + File.separator + file.getOriginalFilename();
                File destination = new File(filePath);

                // 检查并创建目标目录
                File parentDir = destination.getParentFile();
                if (!parentDir.exists() && !parentDir.mkdirs()) {
                    throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
                }

                // 写入文件
                file.transferTo(destination);

                // 保存到数据库
                UploadedFile uploadedFile = new UploadedFile();
                uploadedFile.setFileName(file.getOriginalFilename());
                uploadedFile.setFilePath(filePath);
                fileRepository.save(uploadedFile);

                uploadedFiles.add(file.getOriginalFilename());
            } catch (IOException e) {
                System.err.println("Failed to upload file: " + file.getOriginalFilename());
                e.printStackTrace();
                failedFiles.add(file.getOriginalFilename());
            } catch (Exception e) {
                // 捕获任何其他异常
                throw new BusinessException(ExceptionEnum.FILE_UPLOAD_ERROR.getCode(),
                        "Unexpected error while uploading file: " + file.getOriginalFilename());
            }
        }

        if (failedFiles.size() == files.size()) {
            throw new BusinessException(ExceptionEnum.FILE_UPLOAD_ERROR.getCode(),
                    "All files failed to upload. Please try again.");
        }

        MultipleFilesDto result = new MultipleFilesDto();
        result.setUploadedFiles(uploadedFiles);
        result.setFailedFiles(failedFiles);
        return result;
    }
}
