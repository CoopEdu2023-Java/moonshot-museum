package cn.msa.msa_museum_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.msa.msa_museum_server.entity.FileMetadataEntity;

public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
}
