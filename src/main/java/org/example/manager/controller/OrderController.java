package org.example.manager.controller;


import org.example.manager.dto.response.OrderRequest;
import org.example.manager.entity.Order;
import org.example.manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    // 1 lấy danh sách all đơn hàng
    @GetMapping
    public List<Order>getAllOrders(){
        return orderService.getAllOrder();
    }
    // lấy đơn hàng theo id
    @GetMapping("/{id}")
    public Order getOne(@PathVariable Long id ){
        return orderService.getOrderById(id);
    }
    // thêm đơn hàng
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request){
        return orderService.createOrder(request);
    }
    // cập nhật trạng thái status
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id , @RequestParam String status){
        return orderService.updateStatus(id, status);
    }
    // xóa đơn hàng
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return " Đã xóa đơn hàng thành công" + id ;
    }

}
