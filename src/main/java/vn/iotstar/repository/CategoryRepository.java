package vn.iotstar.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Category;


import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByNameContaining(String name);
	Page<Category> findByNameContaining(String name,Pageable Pageable);
}
