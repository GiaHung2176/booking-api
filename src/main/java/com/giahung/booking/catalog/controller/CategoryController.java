package com.giahung.booking.catalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.giahung.booking.catalog.service.CategoryService;
import jakarta.validation.Valid;
import com.giahung.booking.catalog.dto.*;

@RestController 
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping ("/api/categories")
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @PostMapping ("/api/admin/categories")
    @ResponseStatus (HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.create(categoryRequest);
    }
}
