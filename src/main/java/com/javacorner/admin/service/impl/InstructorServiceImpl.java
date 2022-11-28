package com.javacorner.admin.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.InstructorDao;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.UserService;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {
    
    private InstructorDao instructorDao;
    private UserService userService;
    private CourseService courseService;

    public InstructorServiceImpl(InstructorDao instructorDao, UserService userService, CourseService courseService) {
        this.instructorDao = instructorDao;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId){
        return instructorDao.findById(instructorId)
        .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " Not Found"));
    }

    @Override
    public List<Instructor> findInstructorByName(String name){
        return instructorDao.findInstructorsByNameContains(name);
    }

    @Override
    public Instructor loadInstructorByEmail(String email){
        return instructorDao.findInstructorByEmail(email);
    }

    @Override
    public Instructor createInstructor(String firstName, String lastName, String summary, String email,
            String password) {
                User user = userService.createUser(email, password);
                userService.assignRoleToUser(email, "Instructor");
                return instructorDao.save(new Instructor(firstName, lastName, summary, user));
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorDao.save(instructor);
    }

    @Override
    public List<Instructor> fetchInstructors() {
        return instructorDao.findAll();
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for(Course course : instructor.getCourses()){
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }

    

    

}
