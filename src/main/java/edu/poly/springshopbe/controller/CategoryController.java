package edu.poly.springshopbe.controller;

import edu.poly.springshopbe.dto.CategoryDTO;
import edu.poly.springshopbe.entity.Category;
import edu.poly.springshopbe.service.CategoryService;
import edu.poly.springshopbe.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    /**
     * @RequestBody : nhận các thông tin do client gửi tới, tức là
     * đọc dữ liệu từ phần # của yêu cầu và đưa vào DTO
     * ResponseEntity<?> : chứa thông tin về trạng thái khi phản hồi
     * - Kiểm tra tính hợp lệ dũ liệu, đưa vào :
     * @Valid, BindingResult (ghi nhận thông tin trong quá trình kiểm tra)
     */
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult bindingResult) {
        // nếu có lỗi xảy ra
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if (responseEntity != null)
            return responseEntity;

        Category category = new Category();
        //BeanUtils.copyProperties(categoryDTO, category); // cách 1
        category.setId(categoryDTO.getId()); // cách 2: modelmapper
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());

        category = categoryService.save(category);

        categoryDTO.setId(category.getId()); // cập nhật lại Id cho DTO

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED); // trả thông tin cho client
    }

    /**
     * truyền path id = ? để update Category
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,
                                            @RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category); // cách 1
        category = categoryService.update(id, category);

        categoryDTO.setId(category.getId()); // cập nhật lại Id cho DTO

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED); // trả thông tin cho client
    }

    /**
     * tra ve all Category
     */
    @GetMapping()
    public ResponseEntity<?> getCategory() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    /**
     * tim kiem, phan trang, sap xep du lieu
     *
     * @PageableDefault : neu nguoi dung ko nhap cac gia tri Pageable
     * size : kich thuoc moi trang
     * sort : ten cua truong sap xep
     * direction : sap xep tang dan cua name
     */
    @GetMapping("/page")
    public ResponseEntity<?> getCategory(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * tim Category theo id
     */
    @GetMapping("/{id}/get")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }
}
