package com.myy.service.impl;

import com.myy.dao.entity.DocumentEntity;
import com.myy.dao.repository.DocRepository;
import com.myy.service.DocService;
import com.myy.service.adapter.DocAdapter;
import com.myy.service.dto.DocCriteria;
import com.myy.service.dto.DocumentDTO;
import com.myy.util.page.PageResult;
import com.myy.util.page.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocServiceImpl implements DocService {

    @Resource
    private DocRepository wordRepository;

    @Transactional
    @Override
    public void createDoc(DocumentDTO dto) {
        wordRepository.save(DocAdapter.toEntity(dto));
        wordRepository.updateLastVersion(dto.getTitle());
    }

    @Transactional
    @Override
    public void updateDoc(DocumentDTO dto) {
        Optional<DocumentEntity> optional = wordRepository.findById(dto.getId());
        DocumentEntity entity = optional.orElseThrow(() -> new RuntimeException("Doc not found"));
        entity.setContent(dto.getContent());
        entity.setVersion(dto.getVersion());
        wordRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteDoc(Long wordId) {
        wordRepository.findById(wordId).ifPresent(entity -> {
            wordRepository.deleteById(wordId);
            wordRepository.updateLastVersion(entity.getTitle());
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

        return PageUtils.toResult(page, DocAdapter::toDTO);
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
