 // Bước 2 : cửa sổ database ( tầng truy xuất data)


package org.example.manager.repository;


import org.example.manager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // tìm nhân viên theo email ( kểm tra lúc đăng kí )
    Optional<Employee>findByEmail(String Email);
}
