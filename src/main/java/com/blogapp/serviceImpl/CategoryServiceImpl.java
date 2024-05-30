package com.blogapp.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.dto.CategoryDTO;
import com.blogapp.entities.Category;
import com.blogapp.exception.ResouceNotFoundException;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the CategoryService interface to provide the business
 * logic for CRUD operations on categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    // Autowiring the CategoryRepository to interact with the database.
    @Autowired
    private CategoryRepository categoryRepository;

    // Autowiring the ModelMapper to convert between Category and CategoryDTO.
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new category.
     * 
     * @param categoryDTO The data transfer object containing category details.
     * @return The created CategoryDTO.
     */
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        logger.info("Creating new category with title: {}", categoryDTO.getCategoryTitle());
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        logger.info("Category created successfully with ID: {}", savedCategory.getCategoryId());
        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    /**
     * Updates an existing category.
     * 
     * @param categoryDTO The data transfer object containing updated category
     *                    details.
     * @param categoryId  The ID of the category to be updated.
     * @return The updated CategoryDTO.
     * @throws ResouceNotFoundException if the category is not found.
     */
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        logger.info("Updating category with ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResouceNotFoundException("Category with given id " + categoryId + " does not exists!"));
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryDescription());
        categoryRepository.save(category);
        logger.info("Category with ID: {} updated successfully", categoryId);
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param categoryId The ID of the category to be deleted.
     * @throws ResouceNotFoundException if the category is not found.
     */
    @Override
    public void deleteCategory(Integer categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            logger.error("Category with ID: {} not found", categoryId);
            throw new ResouceNotFoundException("Category with the given id " + categoryId + " not found!");
        }
        categoryRepository.deleteById(categoryId);
        logger.info("Category with ID: {} deleted successfully", categoryId);
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param categoryId The ID of the category to be retrieved.
     * @return The CategoryDTO.
     * @throws ResouceNotFoundException if the category is not found.
     */
    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        logger.info("Retrieving category with ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResouceNotFoundException("Category with given id " + categoryId + " does not exists!"));
        logger.info("Category with ID: {} retrieved successfully", categoryId);
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Retrieves all categories.
     * 
     * @return A list of all CategoryDTO.
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        logger.info("Retrieving all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.info("All categories retrieved successfully");
        return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }
}
