package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blogapp.entities.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category,Integer>{

}
