// bước 4 : nhận request ( yêu cầu từ client )

package org.example.manager.controller;


import org.example.manager.entity.Employee;
import org.example.manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   // dùng để tạo api (trả ra json)
@RequestMapping("/api/employees")   // đường dẫn api

public class EmployeeController {
    // kết nối service
    @Autowired // tự chèn inject object ( chèn vào đối tượng)
    private EmployeeService employeeService;    // tự tạo object employeeService
     // API tạo nhân viên

    @PostMapping
    public ResponseEntity<?>addEmployee(@RequestBody Employee employee) { // requestbody nghĩa là nhận dữ liệu json từ client chuyển thành object employee
        try {
            Employee newEmployee = employeeService.createEmployee(employee); // gọi xuống service check email setstatus rồi lưu vào DB
            return ResponseEntity.ok(newEmployee); // trả vể http 200 ok dữ liệu nhân viên vừa tạo
        } catch (RuntimeException e) {
            // trả về lỗi nếu trùng email hoặc logic sai
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // api lấy danh sách
    @GetMapping
    public List<Employee>getAllEmployees(){
        return employeeService.getAllEmployee();
    }
}
