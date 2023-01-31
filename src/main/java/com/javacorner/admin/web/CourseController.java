package com.javacorner.admin.web;

import static com.javacorner.admin.constants.JavaCornerConstants.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.StudentService;


@Controller
@RequestMapping(value = "/courses")
public class CourseController {
    
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final UserService userService;

    public CourseController(CourseService courseService, InstructorService instructorService, StudentService studentService, UserService userService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String courses(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword){
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute(LIST_COURSES, courses);
        model.addAttribute(KEYWORD, keyword);
        return "course-views/courses";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteCourse(Long courseId, String keyword){
        courseService.removeCourse(courseId);
        return "redirect:/courses/index?keyword=" + keyword;
    }
    
    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public String updateCourse(Model model, Long courseId, Principal principal){
        if(userService.doesCurrentUserHaveRole(INSTRUCTOR)){
            Instructor instructor = instructorService.loadInstructorByEmail(principal.getName());
            model.addAttribute(CURRENT_INSTRUCTOR, instructor);
        }
        Course course = courseService.loadCourseById(courseId);
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute("course", course);
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        return "course-views/formUpdate";
    }

    @PostMapping(value = "/save")
    public String save(Course course){
        courseService.createOrUpdateCourse(course);
        return userService.doesCurrentUserHaveRole(INSTRUCTOR) ? "redirect:/courses/index/instructor" : "redirect:/courses/index";
    }

    @GetMapping(value = "/formCreate")
    public String formCourses(Model model){
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        model.addAttribute("course", new Course());
        return "course-views/formCreate";
    }

    @GetMapping(value = "/index/student")
    public String courseForCurrentStudent(Model model){
        Long studentId = 1L; //To Change
        List<Course> subscribedCourses = courseService.fetchCoursesForStudent(studentId);
        List<Course> otherCourses = courseService.fetchAll().stream().filter(course -> !subscribedCourses.contains(course)).collect(Collectors.toList());
        model.addAttribute("listSubscribedCourses", subscribedCourses);
        model.addAttribute("otherCourses", otherCourses);
        return "course-views/student-courses";
    }

    @GetMapping(value = "/enrollStudent")
    public String enrollCurrentStudentToCourse(Long courseId){
        Long studentId = 1L; //Tooo Change
        courseService.assignStudentToCourse(courseId, studentId);
        return "redirect:/courses/index/student";
    }

    @GetMapping(value = "/unenrollStudent")
    public String unenrollCurrentStudentFromCourse(Long courseId){
        Long studentId = 1L; //Tooo Change
        Course course = courseService.loadCourseById(courseId);
        Student student = studentService.loadStudentById(studentId);
        course.removeStudentFromCourse(student);
        courseService.createOrUpdateCourse(course);
        studentService.updateStudent(student);
        return "redirect:/courses/index/student";
    }

    @GetMapping(value = "/index/instructor")
    public String coursesForCurrentInstructor(Model model, Principal principal){
        User user = userService.loadUserByEmail(principal.getName());
        Instructor instructor = instructorService.loadInstructorById(user.getInstructor().getInstructorId());
        model.addAttribute(LIST_COURSES, instructor.getCourses());
        model.addAttribute(FIRST_NAME, instructor.getFirstName());
        model.addAttribute(LAST_NAME, instructor.getLastName());
        return "course-views/instructor-courses";
    }

    @GetMapping(value = "/instructor")
    public String coursesByInstructorId(Model model, Long instructorId){
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute(LIST_COURSES, instructor.getCourses());
        return "course-views/instructor-courses";
    }
    

}
