package com.javacorner.admin.service;

import java.util.List;

import com.javacorner.admin.entity.Course;

public interface CourseService {

    Course loadCourseById(Long Course);

    Course createCourse(String courseName, String courseDuration, String courseDescription, Long instructorId );
    
    Course createOrUpdateCourse(Course course);

    List<Course> findCoursesByCourseName(String keyword);

    void assignStudentToCourse(Long courseId, Long studentId);

    List<Course> fetchAll();

    List<Course> fetchCoursesForStudent(Long studentId);

    void removeCourse(Long courseId);
}
