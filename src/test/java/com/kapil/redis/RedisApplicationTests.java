package com.kapil.redis;

import com.kapil.redis.entity.Kapil;
import com.kapil.redis.entity.Role;
import com.kapil.redis.entity.Student;
import com.kapil.redis.repository.StudentRepository;
import org.apache.commons.collections4.ListUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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


    //Time taken 1722Ms
    //Time taken 1745Ms

    @Test
    public void writeWithPipeline(){


        List<String> data = new ArrayList();

        for (int i=0;i<10000;i++){
            data.add(""+i);
        }

        long start = System.currentTimeMillis();

        List<List<String>> batches = ListUtils.partition(new ArrayList<>(data), 5);

        for (List<String> batch : batches) {

            redisTemplate.executePipelined((RedisCallback<Object>) conn -> {

                for (String key : batch) {

                    String redisKey = "Pipeline"+key;
                    conn.set(redisTemplate.getKeySerializer().serialize(redisKey),
                            redisTemplate.getValueSerializer().serialize( new Kapil(redisKey, "Kapil Kataria")
                            ));
                }

                return null;
            });


        }

        long end = System.currentTimeMillis();

        System.out.println( "Time taken "+ (end -start) + "Ms");

    }

    //Time taken 51917Ms

    @Test
    public void writeWithSpringDataRedis(){


        List<String> data = new ArrayList();

        long start = System.currentTimeMillis();

        for (int i=0;i<10000;i++){

            Student student = new Student(
                    "StudentGen"+i, "Kapil Kataria", Student.Gender.MALE, 1, "9090", new Role("Student2","NEW"));

            studentRepository.save(student);

        }


        long end = System.currentTimeMillis();

        System.out.println( "Time taken "+ (end -start) + "Ms");

    }

    //Time taken 58Ms
    @Test
    public  void getStudentPipelineData(){

        long start = System.currentTimeMillis();

        ValueOperations<String, Object> values = redisTemplate.opsForValue();

        Object realData = values.get("Pipeline3");
        System.out.println("Data is "+realData);

        long end = System.currentTimeMillis();

        System.out.println( "Time taken "+ (end -start) + "Ms");

    }

    //Time taken 102Ms
    @Test
    public  void findStudentData(){

        long start = System.currentTimeMillis();

        Optional<Student> student = studentRepository.findById("StudentGen2");
        System.out.println(student.get().getId());

        long end = System.currentTimeMillis();
        System.out.println( "Time taken "+ (end -start) + "Ms");
    }


}

