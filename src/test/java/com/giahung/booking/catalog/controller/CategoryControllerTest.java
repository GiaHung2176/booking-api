package com.giahung.booking.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.*;
import com.giahung.booking.catalog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.*;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)   
public class CategoryControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private CategoryService categoryService;

    @Test 
    public void create_whenNameBlank_returns400() throws Exception{
        mockMvc.perform(post("/api/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

    }
}
