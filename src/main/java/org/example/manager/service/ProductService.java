// bước 3:
package org.example.manager.service;

import org.example.manager.dto.response.ProductResponse;
import org.example.manager.entity.Product;
import org.example.manager.repository.ProductRepository;
import org.example.manager.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired // tự động inject object ( chèn đối tượng )
    private ProductRepository productRepository;

    // 🔥 HÀM PHỤ: Chuyển đổi từ Product (Entity) sang ProductResponse (DTO)
    // Giúp giấu đi importPrice (giá nhập) và các thông tin nhạy cảm khác
    private ProductResponse mapToReponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setQuantity(product.getQuantity());
        response.setCategory(product.getCategory());
        response.setExportPrice(product.getExportPrice());
        return response;
    }



    // 1. Lấy danh sách sản phẩm (Trả về danh sách DTO)

    public List<ProductResponse>getAllProduct(){
        return productRepository.findAll()
                .stream()
                .map(this::mapToReponse) // chuyển từng product thành productResponse
                .collect(Collectors.toList());
    }
    public ProductResponse getById(Long id){

        // tìm sản phẩm theo id
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // convert entity -> response
       return mapToReponse(product);
    }
    // 2  thêm mới hoặc cập nhật thông tin sản phẩm

    public ProductResponse create(Product product){
        // 🔥 Áp dụng Util: Nếu SKU null hoặc rỗng, hệ thống tự động tạo!

        if (product.getSku() == null || product.getSku().trim().isEmpty()){
            product.setSku(GenerateCodeUtil.generateSku());
        } else {
            // kiểm tra sku đã tồn tại hay chưa ( tránh trùng)
            productRepository.findBySku(product.getSku()).ifPresent(p -> {
                throw new RuntimeException("Sku đã tồn tại ");
            });
        }
        // nếu quantity chưa có thì mặc định là 0
        if (product.getQuantity() == null){
            product.setQuantity(0);
        }
        // lưu sản phẩm xuống database
        Product savedProduct =  productRepository.save(product);
        return mapToReponse(savedProduct); // 🔥 Trả về DTO
    }


    // 3. cập nhật sản phẩm
    public ProductResponse update(Long id , Product newProduct){
        // tìm theo id
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Không tìm thấy sản phẩm"));
        // cập nhật lại thông tin ( chỉ update những field cần thiết
        product.setName(newProduct.getName());
        product.setImportPrice(newProduct.getImportPrice());
        product.setExportPrice(newProduct.getExportPrice());
        // cập nhật loại sản phẩm
        product.setCategory(newProduct.getCategory());
        // lưu vào dataabse
        Product savedProduct =   productRepository.save(product);
        return mapToReponse(savedProduct);
    }

    // 4. xóa sản phẩm
    public void delete(long id){
        // kiểm trả tồn tại trước khi xóa ( tránh lỗi)
        if (!productRepository.existsById(id)){
            throw new RuntimeException(" Sản phẩm không tồn tại ");
        }
        // xóa theo id
        productRepository.deleteById(id);
    }

// 5 . nhập kho ( tăng số lượng)

    // logic quan trọng : nhập thêm số lượng vào kho
   public ProductResponse addStock(Long productId , Integer amount){
        // kiểm tra số lượng phải >0
       if (amount == null || amount <=0 ){
           throw new RuntimeException(" Số lượng phải lớn hơn 0 ");
       }
       // tìm sản phẩm theo ìd
       Product product = productRepository.findById(productId)
               .orElseThrow(()-> new RuntimeException(" Không tìm thấy sản phẩm"));
       // cộng thêm số lượng
       product.setQuantity(product.getQuantity()+amount);
       // lưu lại database
      Product savedproduct =  productRepository.save(product);
      return mapToReponse(savedproduct);
    }

    // 6. Xuất kho giảm số lượng

    public ProductResponse removeStock(Long productId , Integer amount){

        // kiểm tra số lượng >0
        if (amount == null || amount <=0){
            throw new RuntimeException(" Số lượng phải lớn hơn 0 ");
        }
        // tìm sản phẩm
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException(" Không tìm thấy sản phẩm"));
        // kiểm tra có đủ hàng hay ko
        if (product.getQuantity()< amount){
            throw new RuntimeException(" Không đủ hàng trong kho");
        }
        // trừ số lượng
        product.setQuantity(product.getQuantity()-amount);
        // lưu lại database
        Product savedProduct = productRepository.save(product);
        return mapToReponse(savedProduct);
    }
    // 7. tìm kiếm sản phẩm search
    public List<ProductResponse>search(String name) {
        // nếu người dùng ko nhập gì thì trả về tất cả
        if (name == null || name.trim().isEmpty()) {
            return getAllProduct(); // gọi lại hàm get all để trả về dto
        }
        // tìm gần đúng (like %name% ) không phân biệt hoa thường
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToReponse) // trả về danh sách dto
                .collect(Collectors.toList());
    }
   }



