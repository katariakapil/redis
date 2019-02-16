package com.kapil.redis.repository;

import com.kapil.redis.entity.Role;
import com.kapil.redis.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

    Student findByName(String name);

    Student findByRefIdAndName(String refid, String name);

    List<Student> findByRefId(String refid);

    List<Student> findByRole_RoleName(String roleName);
}
