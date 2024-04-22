package com.myy.service.impl;

import com.myy.dao.entity.DocumentEntity;
import com.myy.dao.repository.DocumentRepository;
import com.myy.service.DocumentService;
import com.myy.service.adapter.DocumentAdapter;
import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.page.PageResult;
import com.myy.util.redis.RedisUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Resource
    private DocumentRepository wordRepository;

    @Transactional
    @Override
    public void createDocument(DocumentDTO dto) {
        wordRepository.save(DocumentAdapter.toEntity(dto));
        updateLastVersion(dto.getTitle());
    }

    @Transactional
    @Override
    public void updateDocument(DocumentDTO dto) {
        Optional<DocumentEntity> optional = wordRepository.findById(dto.getId());
        DocumentEntity entity = optional.orElseThrow(() -> new RuntimeException("Doc not found"));
        entity.setContent(dto.getContent());
        entity.setVersion(dto.getVersion());
        wordRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteDocument(Long wordId) {
        wordRepository.findById(wordId).ifPresent(entity -> {
            wordRepository.deleteById(wordId);
            updateLastVersion(entity.getTitle());
        });
    }

    @Transactional
    @Override
    public void updateLastVersion(String title) {
        // 此处有并发问题
        RedisUtils.lock(title, () -> {
            List<DocumentEntity> entities = wordRepository.findByTitle(title);
            if (CollectionUtils.isEmpty(entities)) {
                return;
            }
            DocumentEntity last = entities.stream().max(Comparator.comparing(DocumentEntity::getCreateTime)).get();
            wordRepository.updateIsLastVersion(Boolean.TRUE, last.getId());

            entities.forEach(entity -> {
                if (Boolean.TRUE.equals(entity.getIsLastVersion()) && last != entity) {
                    wordRepository.updateIsLastVersion(Boolean.FALSE, entity.getId());
                }
            });
        });
    }

    @Override
    public PageResult<DocumentDTO> queryDocs(DocCriteria criteria, Pageable pageable) {
        Page<DocumentEntity> page = wordRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 查询条件
            if (Objects.nonNull(criteria.getTitle())) {
                predicates.add(builder.equal(root.get("title"), criteria.getTitle()));
            }
            if (Objects.nonNull(criteria.getContentLike())) {
                predicates.add(builder.like(root.get("content"), "%" + criteria.getContentLike() + "%"));
            }
            if (Objects.nonNull(criteria.getVersion())) {
                predicates.add(builder.equal(root.get("version"), criteria.getVersion()));
            }
            if (Objects.nonNull(criteria.getIsLastVersion())) {
                predicates.add(builder.equal(root.get("isLastVersion"), criteria.getIsLastVersion()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }, check(pageable));

        return PageResult.of(page, DocumentAdapter::toDTO);
    }

    private static Pageable check(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(pageable.getSort().stream().map(order -> {
                if ("version".equals(order.getProperty())) {
                    return order.withProperty("version");
                }
                if ("createTime".equals(order.getProperty())) {
                    return order.withProperty("createTime");
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList())));
    }
}
