package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;


  @Autowired

    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping ("categories")
    @PreAuthorize("permitAll()")
    public List<Category> getAll()
    {
        return categoryDao.getAllCategories();
    }

    @GetMapping ("categories/{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id)
    {
       var categories = categoryDao.getById(id);
        if(categories == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return categories;
    }

    //https://localhost:8080/categories/1/products
    @GetMapping("categories/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        return productDao.listByCategoryId(categoryId);
    }
    @PostMapping ("categories") @ResponseStatus (HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {
        return categoryDao.create(category);

    }
    @PutMapping("categories/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        categoryDao.update(id,category);
    }
    @DeleteMapping("categories/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id)
    {
        categoryDao.delete(id);
    }
}
