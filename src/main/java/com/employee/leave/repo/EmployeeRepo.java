package com.employee.leave.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employee.leave.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	@Query("SELECT e FROM Employee e WHERE e.email = :email")
	Optional<Employee> findByEmail(@Param("email") String email);

	@Query("SELECT e FROM Employee e WHERE e.phno = :phno")
	Optional<Employee> findByPhno(@Param("phno") String phno);

}
