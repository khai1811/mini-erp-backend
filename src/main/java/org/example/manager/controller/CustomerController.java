package org.example.manager.controller;

import org.example.manager.entity.Customer;
import org.example.manager.repository.CustomerRepository;
import org.example.manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    // lấy danh sách all khách hàng
    @GetMapping
        public List<Customer>getAllCustomers(){
        return customerService.getAllCustomers();
    }

    // lấy chi tiết 1 khách hàng
@GetMapping("/{id}")
public Customer getById(@PathVariable Long id){
        return customerService.getCustomerById(id);
}
    // thêm khách hàng
    @PostMapping
public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }
    // cập nhật thông tin khách hàng
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);
    }

    // xóa khách hàng
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return " Đã xóa khách hàng thành công " + id ;
    }
}
