package com.javacorner.admin.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.RoleService;
import com.javacorner.admin.service.StudentService;
import com.javacorner.admin.service.UserService;

@Component
public class MyRunner implements CommandLineRunner {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private InstructorService instructorService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private StudentService studentService;



    private static final String INSTRUCTOR = "Instructor";

	private static final String STUDENT = "Student";

	private static final String ADMIN = "Admin";

    @Override
    public void run(String... args) throws Exception {
            // User user1 = userService.createUser("user1@gmail.com", "pass1");
			// User user2 = userService.createUser("user2@gmail.com", "pass2");

			// roleService.createRole(ADMIN);
			// roleService.createRole(INSTRUCTOR);
			// roleService.createRole(STUDENT);

			// userService.assignRoleToUser(user1.getEmail(), ADMIN);
			// userService.assignRoleToUser(user1.getEmail(), INSTRUCTOR);
			// userService.assignRoleToUser(user2.getEmail(), STUDENT);

			// Instructor instructor1 = instructorService.createInstructor("instructor1FN", "instructor1LN", "Experienced Instructor", "instructorUser1@gmail.com", "instructorPass1");
			// Instructor instructor2 = instructorService.createInstructor("instructor2FN", "instructor2LN", "Certified Instructor", "instructorUser2@gmail.com", "instructorPass2");

			// Student student1 = studentService.createStudent("student1FN", "student1LN", "Beginner", "student1User@gmail.com", "studentPass1");
			// Student student2 = studentService.createStudent("student2FN", "student2LN", "Expert", "student2User@gmail.com", "studentPass2");
			
			// Course course1 = courseService.createCourse("Spring Data JPA", "5 Hours", "Master Spring Data JPA", instructor1.getInstructorId());
			// Course course2 = courseService.createCourse("Spring Security", "4 Hours", "Secure your App", instructor2.getInstructorId());

			// courseService.assignStudentToCourse(course1.getCourseId(), student1.getStudentId());
			// courseService.assignStudentToCourse(course1.getCourseId(), student2.getStudentId());
			// courseService.assignStudentToCourse(course2.getCourseId(), student2.getStudentId());
            // List<Instructor> instructors = instructorService.fetchInstructors();
            // System.out.println(instructors);

    }
    
}
