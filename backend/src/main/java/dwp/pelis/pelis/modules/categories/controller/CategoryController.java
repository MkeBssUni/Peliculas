package dwp.pelis.pelis.modules.categories.controller;

import dwp.pelis.pelis.kernel.ResponseApi;
import dwp.pelis.pelis.modules.categories.model.Category;
import dwp.pelis.pelis.modules.categories.model.CategoryDto;
import dwp.pelis.pelis.modules.categories.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinema/categories")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(value = "/")
    public ResponseEntity<ResponseApi<Category>> save(@Validated(CategoryDto.Save.class) @RequestBody CategoryDto dto){
        ResponseApi<Category> responseApi = categoryService.save(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
    @GetMapping(value = "/")
    public ResponseEntity<ResponseApi<List<Category>>> getOne(){
        ResponseApi<List<Category>> responseApi = categoryService.getAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
