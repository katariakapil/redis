package com.kapil.redis;

import com.kapil.redis.entity.Student;
import com.kapil.redis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {



	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);


}



}

