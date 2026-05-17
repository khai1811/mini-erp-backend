// bước 3: tầng xử lí nghiệp vụ

package org.example.manager.service;

import org.example.manager.entity.Employee;
import org.example.manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired   // tự động inject object ( chèn đối tượng )
    private EmployeeRepository employeeRepository;
    // thêm mới nhân viên
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()){ // “Nếu trong database đã tồn tại email → isPresent đúng → báo lỗi không cho tạo , sai thì xuống bước tiếp”
            throw new RuntimeException(" Lỗi : Email này đã được sử dụng !");
        }
        // mặc định trạng thái là active nếu không truyền vào
        if (employee.getStatus() == null){
            employee.setStatus("Active");
        }
        // lưu database
        return employeeRepository.save(employee);

    }
    //lấy danh sách all nhân viên
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

}
