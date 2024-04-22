package com.myy.dao.repository;

import com.myy.dao.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository
    extends JpaRepository<DocumentEntity, Long>, JpaSpecificationExecutor<DocumentEntity> {

    List<DocumentEntity> findByTitle(String title);

    @Modifying
    @Query("update DocumentEntity d set d.isLastVersion = ?1 where d.id = ?2")
    void updateIsLastVersion(Boolean isLastVersion, Long id);
}
