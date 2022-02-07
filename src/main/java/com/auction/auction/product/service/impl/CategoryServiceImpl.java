package com.auction.auction.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.product.model.Category;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.product.service.CategoryService;
import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.dto.CategoryRequest;
import com.auction.auction.product.dto.CategoryResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CategoryResponse> getAll() {
        var entities = repository.findAll();
        var responses = entities.stream().map(entity -> mapper.map(entity, CategoryResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public CategoryResponse getById(Long id) {
        var entity = repository.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));
        var response = mapper.map(entity, CategoryResponse.class);
        return response;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        var exist = repository.getCategoryByName(request.getName())
                .orElse(null);
        if (exist != null)
            throw new ResourceNotFoundExceptionRequest("Exist category with the same name");
        var entity = mapper.map(request, Category.class);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while saving category");
        }
        var response = mapper.map(entity, CategoryResponse.class);
        return response;
    }

    @Override
    public CategoryResponse updateById(Long id, CategoryRequest request) {
        var exist = repository.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));
        exist.setDescription(request.getDescription());
        exist.setName(request.getName());
        repository.save(exist);
        var response = mapper.map(exist, CategoryResponse.class);
        return response;
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting category");
        }
    }

}
