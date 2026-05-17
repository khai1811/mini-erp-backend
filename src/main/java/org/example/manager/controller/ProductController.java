package org.example.manager.controller;

import org.example.manager.dto.response.ProductResponse;
import org.example.manager.entity.Product;
import org.example.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // dùng để tạo api trả ra json
@RequestMapping("/api/products") // đường dẫn api
public class ProductController {

    @Autowired // tự chèn inject object
    private ProductService productService; // biến productService  controller ko xử lí mà đưa xuống service

   // 1. lấy danh sách sản phẩm
    @GetMapping
    public  List<ProductResponse>getAll(){
        return productService.getAllProduct();
    }
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getById(id);
    }
    // 2. thêm sản phẩm
    @PostMapping
    // POST: /api/products
    public ProductResponse create(@RequestBody Product product){
        return productService.create(product);
    }
    // 3 . cập nhật sản phẩm
    // PUT: /api/products/{id}
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id , @RequestBody Product product){
        // @PathVariable: lấy id từ URL
        return productService.update(id, product);
    }
    // 4. xóa sản phẩm
    // DELETE: /api/products/{id}
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return " Xóa sản phẩm thành công " + id;
    }
    // 5. tìm kiếm sản phẩm
    // GET: /api/products/search?name=abc
    @GetMapping("/search")
    public List<ProductResponse>search(@RequestParam String name){
        // @RequestParam: lấy query param từ URL
        return productService.search(name);
    }
    // 6 . nhập kho
    // POST: /api/products/{id}/add-stock?amount=10
    @PatchMapping("/{id}/add-stock")
    public ProductResponse addStock(@PathVariable Long id, @RequestParam Integer amount){
        return productService.addStock(id, amount);
    }
    // 7. xuất kho
// POST: /api/products/{id}/remove-stock?amount=5
    @PatchMapping("/{id}/remove-stock")
    public ProductResponse removeStock(@PathVariable Long id, @RequestParam Integer amount){
        return productService.removeStock(id, amount);
    }
}