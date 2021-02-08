package com.vblog.service.impl;

import com.vblog.dao.TypeRepository;
import com.vblog.entity.Type;
import com.vblog.handler.NotFoundException;
import com.vblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> ListType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type one = typeRepository.getOne(id);
        if (one == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,one);
        return typeRepository.save(one);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
}
