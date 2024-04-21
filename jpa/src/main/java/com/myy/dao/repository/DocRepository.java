package com.myy.dao.repository;

import com.myy.dao.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocRepository extends JpaRepository<DocumentEntity, Long>, JpaSpecificationExecutor<DocumentEntity> {

    @Modifying
    @Query(
        value = "update t_document A inner join (select max(create_time) as max_create_time from t_document where title = :title) B set A.is_last_version = (A.create_time = B.max_create_time) where A.title = :title",
        nativeQuery = true)
    void updateLastVersion(String title);

}
