package edu.poly.springshopbe.service;

import edu.poly.springshopbe.entity.Category;
import edu.poly.springshopbe.exception.CategoryException;
import edu.poly.springshopbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Optional<Category> existed = categoryRepository.findById(id);

        // Category khong ton tai
        if (existed.isEmpty()) {
            throw new CategoryException("Category id " + id + " does not exist");
        }
        // Category ton tai
        try {
            Category existedCategory =  existed.get();
            existedCategory.setName(category.getName());
            existedCategory.setStatus(category.getStatus());

            // tra ve thong tin da dc update
            return categoryRepository.save(existedCategory);
        } catch (Exception e) {
            throw new CategoryException("Category is updated fail");
        }

    }
}
