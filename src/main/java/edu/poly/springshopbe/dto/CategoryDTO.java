package edu.poly.springshopbe.dto;

import edu.poly.springshopbe.entity.CategoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    @NotEmpty(message = "Category name is required") // message khi error
    private String name;
    private CategoryStatus status;
}
