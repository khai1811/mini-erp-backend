package org.example.manager.service;

import org.example.manager.entity.Customer;
import org.example.manager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired // chèn đối tượng
    private CustomerRepository customerRepository;

    // 1thêm khách hàng mới
    public Customer createCustomer(Customer customer){
        // gán giá trị mặt định cho khách hàng mới
        // nếu client không gửi lên
        if (customer.getPoints()==null){
            customer.setPoints(0);
        }
        if (customer.getRank() == null){
            customer.setRank("NORMAL");
        }
        return customerRepository.save(customer);
    }
    //2 lấy danh sách tất cả khách hàng
    public List<Customer>getAllCustomers(){
        return customerRepository.findAll();
    }
    // lấy chi tiết 1 khách hàng
    public Customer getCustomerById( Long id){
        return customerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Không tìm thấy thông tin khách hàng "));

    }
    //3 cập nhật thông tin khách hàng
    public  Customer updateCustomer(Long id , Customer updatecustomer){
        // Tìm khách hàng cũ
        Customer existingCustomer = getCustomerById(id);
        // cập nhật thông tin cá nhân
        existingCustomer.setName(updatecustomer.getName());
        existingCustomer.setPhone(updatecustomer.getPhone());
        existingCustomer.setEmail(updatecustomer.getEmail());

        return customerRepository.save(existingCustomer);
    }
    // 4. xóa thông tin khách hàng
    public void  deleteCustomer(Long id){
        Customer customer = getCustomerById(id);
            customerRepository.delete(customer);
    }
}
