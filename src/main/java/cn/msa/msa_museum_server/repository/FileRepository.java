package cn.msa.msa_museum_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.msa.msa_museum_server.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Page<FileEntity> findAll(Pageable pageable);
}