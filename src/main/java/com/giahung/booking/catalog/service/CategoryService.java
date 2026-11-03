package com.giahung.booking.catalog.service;

import com.giahung.booking.catalog.repository.CategoryRepository;
import com.giahung.booking.common.exception.ConflictException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.giahung.booking.catalog.entity.*;
import com.giahung.booking.catalog.dto.*;

@Service 
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public List<CategoryResponse> findAll() {
    List<Category> categories = categoryRepository.findAll();
    
    return categories.stream()
            .map(category -> new CategoryResponse(
                    category.getId(),
                    category.getName()
            ))
            .collect(Collectors.toList());
    }

    public CategoryResponse create(CategoryRequest categoryRequest){
        if(categoryRepository.existsByName(categoryRequest.name())){
            throw new ConflictException("Name already exists");
        }
        Category category = new Category(categoryRequest.name());
        Category saved=categoryRepository.save(category);
        return new CategoryResponse(saved.getId(),saved.getName());
    }

}
