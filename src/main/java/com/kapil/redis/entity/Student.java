package com.kapil.redis.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.io.Serializable;

@RedisHash("Student")
public class Student implements Serializable {

    public Student(String id, String name, Gender gender, int grade, String refId, Role role) {

        this.id=id;
        this.name=name;
        this.gender=gender;
        this.grade=grade;
        this.refId = refId;
        this.role=role;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", grade=" + grade +
                ", role=" + role +
                '}';
    }

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    private String id;

    @Indexed
    private String refId;

    @Indexed
    private String name;

    private Gender gender;
    private int grade;

    private Role role;

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


}
