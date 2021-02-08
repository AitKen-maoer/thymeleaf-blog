package com.vblog.service;

import com.vblog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> ListType(Pageable pageable);
    List<Type> ListType();
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);
}
