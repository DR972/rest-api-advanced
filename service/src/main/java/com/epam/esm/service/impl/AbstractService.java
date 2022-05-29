package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.AbstractDao;
import com.epam.esm.dto.BaseEntityDto;
import com.epam.esm.dto.ResourceDto;
import com.epam.esm.dto.mapper.EntityMapper;
import com.epam.esm.entity.BaseEntity;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code AbstractService} is designed for basic work with database tables.
 *
 * @param <T> indicates that for this instantiation of the Service, will be used this type of Entity implementation.
 * @param <D> indicates that for this instantiation of the BaseService, will be used this type of EntityDto implementation.
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
@Scope("prototype")
public class AbstractService<T extends BaseEntity<ID>, ID, D extends BaseEntityDto> implements BaseService<T, ID, D> {

    /**
     * AbstractDao<T, ID> dao.
     */
    protected final AbstractDao<T, ID> dao;

    /**
     * EntityMapper<T, D> entityMapper.
     */
    protected final EntityMapper<T, D> entityMapper;

    /**
     * The constructor creates a AbstractService object
     *
     * @param dao          AbstractDao<T, ID> dao
     * @param entityMapper EntityMapper<T, D> entityMapper
     */
    @Autowired
    public AbstractService(AbstractDao<T, ID> dao, EntityMapper<T, D> entityMapper) {
        this.dao = dao;
        this.entityMapper = entityMapper;
    }

    @Override
    public D findEntityById(String id) {
        return entityMapper.convertToDto(dao.findEntityById(Long.parseLong(id)).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", "id = " + id)));
    }

    public ResourceDto<D> findListEntities(int offset, int limit) {
        List<D> entities = dao.findListEntities((offset - 1) * limit, limit)
                .stream().map(entityMapper::convertToDto).collect(Collectors.toList());
        return new ResourceDto<>(entities, offset, entities.size(), dao.countNumberEntityRows());
    }
}

