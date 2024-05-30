package com.blogapp.services;

import java.util.List;
import com.blogapp.dto.CategoryDTO;

public interface CategoryService {

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

	void deleteCategory(Integer categoryId);

	CategoryDTO getCategory(Integer categoryId);

	List<CategoryDTO> getAllCategories();

}
