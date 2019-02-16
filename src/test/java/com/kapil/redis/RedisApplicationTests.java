package com.kapil.redis;

import com.kapil.redis.entity.Role;
import com.kapil.redis.entity.Student;
import com.kapil.redis.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
    RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {}

	@Test
	public  void saveStudent(){

		Student student = new Student(
				"Student2", "Kapil Kataria", Student.Gender.MALE, 1, "9090", new Role("Student2","NEW"));

        Student student1 = new Student(
                "Student3", "Rohan Kumar", Student.Gender.MALE, 1, "9090", new Role("Student3","OLD"));

        studentRepository.save(student);
        studentRepository.save(student1);

	}


	@Test
	public  void findStudent(){

		Optional<Student> student = studentRepository.findById("Student2");
		System.out.println(student.get().getId());
	}


    @Test
    public void findAllStudents(){

        List<Student> students = (List<Student>) studentRepository.findAll();
        for(Student student : students) {
            System.out.println(student.getId());
        }
    }

    @Test
    public void findByName(){

       Student kapil = studentRepository.findByName("Kapil Kataria");

        System.out.println("Found by name "+kapil.getName());

    }


    @Test
    public void findByRefIdAndName(){

        Student data =  studentRepository.findByRefIdAndName("9090","Kapil Kataria");
        System.out.println(" Data found "+data.getName() + data.getRefId()
        );
    }

    @Test
    public void findByRefId(){

        List<Student> data =  studentRepository.findByRefId("9090");

        for(Student student: data) {
            System.out.println(" Students found " + student.getName() +" : "+ student.getRefId()
            );
        }
    }

    @Test
    public void findByRole(){

        List<Student> data =  studentRepository.findByRole_RoleName("NEW");

        System.out.println(" Students found with Role data " + data );
        for(Student student: data) {
            System.out.println(" Students found with Role " + student +" : "
            );
        }



          //  redisTemplate.opsForSet().
       // studentRepository.deleteAll();

    }
}

