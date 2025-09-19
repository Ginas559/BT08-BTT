package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Category;

public interface CategoryService {

    void deleteAll();

    void delete(Category entity);

    void deleteById(Long id);

    long count();

    Optional<Category> findById(Long id);

    List<Category> findAllById(Iterable<Long> ids);

    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);

    List<Category> findAll(Sort sort);

    <S extends Category> Optional<S> findOne(Example<S> example);

    List<Category> findByNameContaining(String name);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    <S extends Category> S save(S entity);
}