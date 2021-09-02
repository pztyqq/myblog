package com.pzt.service;

import com.pzt.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 pzt
 * @创建时间 2021/8/2
 * @描述
 */
public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
