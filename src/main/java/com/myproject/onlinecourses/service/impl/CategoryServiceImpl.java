package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CategoryConverter;
import com.myproject.onlinecourses.dto.CategoryDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Category;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.CategoryRepository;
import com.myproject.onlinecourses.service.CategoryService;
import com.myproject.onlinecourses.utils.PaginationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    CategoryConverter converter;

    @Override
    public ResponseObject getAll(Optional<Integer> page, Optional<Integer> limit ){
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(20));
        Page<Category> categories = categoryRepo.findAllByActive(pageable, true);

        Page<CategoryDTO> dtos = categories.map(new Function<Category, CategoryDTO>() {
            @Override
            public CategoryDTO apply(Category category) {
                return converter.entityToDTO(category);
            }
        });
        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject getById(String id){
        Optional<Category> category = categoryRepo.findByIdAndActive(id, true);
        if(!category.isPresent() || category.get().isActive() == false)
            throw new NotFoundException("Can not found category with id: " + id);
        return new ResponseObject(converter.entityToDTO(category.get()));
    }

    @Override
    public ResponseObject update(String id, CategoryDTO categoryDTO){
        Optional<Category> category = categoryRepo.findById(id);
        if(!category.isPresent()) throw new NotFoundException("Can not found category with id: " + id);
        category.get().setName(categoryDTO.getName());
        Category res = categoryRepo.save(category.get());
        return new ResponseObject(converter.entityToDTO(res));
    }

    @Override
    public ResponseObject delete(String id){
        Optional<Category> category = categoryRepo.findById(id);
        if(!category.isPresent()) throw new NotFoundException("Can not found category with id: " + id);
        category.get().setActive(false);
        categoryRepo.save(category.get());
        return new ResponseObject("", "200", "Delete successful", null);
    }

    @Override
    public ResponseObject saveNewCategory(CategoryDTO dto){
        Optional<Category> foundExisted = categoryRepo.findFirstByName(dto.getName());
        if(foundExisted.isPresent()) throw new DuplicateException("Name was existed");
        Category category = new Category();
        category.setName(dto.getName());
        category.setActive(true);
        Category res = categoryRepo.save(category);
        return new ResponseObject(converter.entityToDTO(res));
    }
}
