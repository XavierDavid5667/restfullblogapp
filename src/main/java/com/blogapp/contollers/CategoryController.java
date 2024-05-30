package com.blogapp.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogapp.dto.CategoryDTO;
import com.blogapp.services.CategoryService;

import jakarta.validation.Valid;

/**
 * This class acts as a controller for handling HTTP requests related to blog categories.
 * It maps HTTP requests to the appropriate service methods for CRUD operations on categories.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    // Autowiring the CategoryService to handle business logic related to categories.
    @Autowired
    CategoryService categoryService;

    /**
     * Creates a new category.
     * @param categoryDTO The data transfer object containing category details.
     * @return ResponseEntity containing the created CategoryDTO and HTTP status.
     */
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return new ResponseEntity<CategoryDTO>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing category.
     * @param categoryDTO The data transfer object containing updated category details.
     * @param categoryId The ID of the category to be updated.
     * @return ResponseEntity containing the updated CategoryDTO and HTTP status.
     */
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO, @PathVariable("id") Integer categoryId) {
        return new ResponseEntity<CategoryDTO>(categoryService.updateCategory(categoryDTO, categoryId), HttpStatus.OK);
    }

    /**
     * Deletes a category.
     * @param categoryId The ID of the category to be deleted.
     * @return ResponseEntity containing a success message and HTTP status.
     */
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<String>("Category with Id " + categoryId + " deleted successfully!", HttpStatus.OK);
    }

    /**
     * Retrieves a category by its ID.
     * @param categoryId The ID of the category to be retrieved.
     * @return ResponseEntity containing the CategoryDTO and HTTP status.
     */
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Integer categoryId) {
        return new ResponseEntity<CategoryDTO>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    /**
     * Retrieves all categories.
     * @return ResponseEntity containing a list of all CategoryDTO and HTTP status.
     */
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategories(), HttpStatus.OK);
    }
}
