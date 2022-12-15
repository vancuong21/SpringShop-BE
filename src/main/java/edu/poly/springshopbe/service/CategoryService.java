package edu.poly.springshopbe.service;

import edu.poly.springshopbe.entity.Category;
import edu.poly.springshopbe.exception.CategoryException;
import edu.poly.springshopbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // tim kiem va tra ve All Category in db
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // tim kiem, phan trang, sap xep du lieu
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    // tim Category theo Id
    public Category findById(Long id) {
        Optional<Category> found =  categoryRepository.findById(id);

        if (found.isEmpty()) { // neu ko ton tai thong tin cua Category trong db
            throw new CategoryException("Category with id " + id + " does not exist");
        }

        return found.get();
    }
}
