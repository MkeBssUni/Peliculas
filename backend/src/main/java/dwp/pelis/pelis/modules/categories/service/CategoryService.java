package dwp.pelis.pelis.modules.categories.service;

import dwp.pelis.pelis.kernel.ResponseApi;
import dwp.pelis.pelis.modules.categories.model.Category;
import dwp.pelis.pelis.modules.categories.model.CategoryDto;
import dwp.pelis.pelis.modules.categories.model.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {
    private final ICategoryRepository iCategoryRepository;

    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public ResponseApi<Category> save (CategoryDto dto){
        Optional<Category> category = iCategoryRepository.findByNameIgnoreCase(dto.getName());
        if (category.isPresent()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Category already exists");
        Category newCategory = new Category();
        newCategory.save(dto);
        return new ResponseApi<>(iCategoryRepository.saveAndFlush(newCategory), HttpStatus.CREATED, false, "Category created");
    }

    @Transactional(readOnly = true)
    public ResponseApi<List<Category>> getAll(){
        return new ResponseApi<>(iCategoryRepository.findAll(), HttpStatus.OK, false, "Category found");
    }
}
