package cn.msa.msa_museum_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.entity.FileEntity;
import cn.msa.msa_museum_server.repository.FileRepository;
import cn.msa.msa_museum_server.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public ResponseDto<Page<FileDto>> getFileList(Pageable pageable) {
        // 获取分页数据
        Page<FileEntity> fileEntities = fileRepository.findAll(pageable);

        // 转换为 FileDto 并包装在 ResponseDto 中
        Page<FileDto> fileDto = fileEntities.map(fileEntity -> new FileDto(
                fileEntity.getFileName(),
                fileEntity.getFileSize(),
                fileEntity.getUploadTime().toString()
        ));

        // 返回 ResponseDto
        return new ResponseDto<>(0, "success", fileDto);
    }
}