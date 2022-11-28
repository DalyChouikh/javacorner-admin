package com.javacorner.admin.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.StudentService;
import com.javacorner.admin.service.UserService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    StudentDao studentDao;
    UserService userService;

    
    public StudentServiceImpl(StudentDao studentDao, UserService userService) {
        this.studentDao = studentDao;
        this.userService = userService;
    }

    @Override
    public Student loadStudentById(Long studentId) {
        return studentDao.findById(studentId)
        .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " Not Found"));
    }
    
    @Override
    public List<Student> findStudentsByName(String name) {
        return studentDao.findStudentByNameContains(name);
    }
    
    @Override
    public Student loadStudentByEmail(String email) {
        return studentDao.findStudentByEmail(email);
    }

    @Override
    public Student createStudent(String firstName, String lastName, String level, String email, String password) {
        User user = userService.createUser(email, password);
        userService.assignRoleToUser(email, "Student");
        return studentDao.save(new Student(firstName, lastName, level, user));
    }
    
    @Override
    public Student updateStudent(Student student) {
        return studentDao.save(student);
    }
    
    @Override
    public List<Student> fetchStudents() {
        return studentDao.findAll();
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student = loadStudentById(studentId);
        Iterator<Course> iterator = student.getCourses().iterator();
        if(iterator.hasNext()){
            Course course  = iterator.next();
            course.removeStudentFromCourse(student);
        }
        studentDao.deleteById(studentId);
    }

   
    
}
