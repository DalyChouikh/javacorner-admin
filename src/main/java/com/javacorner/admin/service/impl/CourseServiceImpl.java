package com.javacorner.admin.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.CourseDao;
import com.javacorner.admin.dao.InstructorDao;
import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.service.CourseService;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final InstructorDao instructorDao;
    private final StudentDao studentDao;

    public CourseServiceImpl(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao){
        this.courseDao = courseDao;
        this.instructorDao = instructorDao;
        this.studentDao = studentDao;
    }
    
    @Override
    public Course loadCourseById(Long courseId){
        return courseDao.findById(courseId)
        .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " Not Found"));
    }

    @Override
    public Course createCourse(String courseName, String courseDuration, String courseDescription, Long instructorId){
        Instructor instructor = instructorDao.findById(instructorId)
        .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " Not Found"));
        return courseDao.save(new Course(courseName, courseDuration, courseDescription, instructor));
    }

    @Override
    public Course createOrUpdateCourse(Course course){
        return courseDao.save(course);
    }

    @Override
    public List<Course> findCoursesByCourseName(String keyword){
        return courseDao.findCoursesByCourseNameContains(keyword);
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId){
        Student student = studentDao.findById(studentId)
        .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " Not Found"));

        Course course = courseDao.findById(courseId)
        .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " Not Found"));

        course.assignStudentToCourse(student);
    }

    @Override
    public List<Course> fetchAll(){
        return courseDao.findAll();
    }

    @Override
    public List<Course> fetchCoursesForStudent(Long studentId){
        return courseDao.getCoursesByStudentId(studentId);
    }

    @Override
    public void removeCourse(Long courseId){
        courseDao.deleteById(courseId);
    }



}
