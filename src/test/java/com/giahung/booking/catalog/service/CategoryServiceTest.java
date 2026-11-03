package com.giahung.booking.catalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.giahung.booking.catalog.dto.CategoryRequest;
import com.giahung.booking.catalog.dto.CategoryResponse;
import com.giahung.booking.catalog.repository.CategoryRepository;
import com.giahung.booking.common.exception.ConflictException;
import static org.mockito.Mockito.*;
import com.giahung.booking.catalog.entity.*;


@ExtendWith (MockitoExtension.class)
public class CategoryServiceTest {
    
    @Mock 
    private CategoryRepository categoryRepository;

    @InjectMocks 
    private CategoryService categoryService;

    @Test 
    public void create_whenNameExists_throwsConflict(){
        when(categoryRepository.existsByName("Concert")).thenReturn(true);

        assertThrows(ConflictException.class,()->categoryService.create(new CategoryRequest("Concert")));
        verify(categoryRepository,never()).save(any());
    }

    @Test 
    public void create_whenNameNotExists_returnsCategoryResponse(){
        when(categoryRepository.existsByName("Concert")).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(new Category("Concert"));
        
        CategoryResponse categoryResponse=categoryService.create(new CategoryRequest("Concert"));
        assertEquals("Concert", categoryResponse.name());
    }
}
