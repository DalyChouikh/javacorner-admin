package com.javacorner.admin.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;


@Controller
@RequestMapping(value = "/courses")
public class CourseController {
    
    private CourseService courseService;
    private InstructorService instructorService;

    public CourseController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    @GetMapping(value = "/index")
    public String courses(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword){
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute("listCourses", courses);
        model.addAttribute("keyword", keyword);
        return "course-views/courses";
    }

    @GetMapping(value = "/delete")
    public String deleteCourse(Long courseId, String keyword){
        courseService.removeCourse(courseId);
        return "redirect:/courses/index?keyword=" + keyword;
    }
    
    @GetMapping(value = "/formUpdate")
    public String updateCourse(Model model, Long courseId){
        Course course = courseService.loadCourseById(courseId);
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute("course", course);
        model.addAttribute("listInstructors", instructors);
        return "course-views/formUpdate";
    }

    @PostMapping(value = "/save")
    public String save(Course course){
        courseService.createOrUpdateCourse(course);
        return "redirect:/courses/index";
    }

    @GetMapping(value = "/formCreate")
    public String formCourses(Model model){
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute("listInstructors", instructors);
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
        Long studentId = 1L; //To Change
        courseService.assignStudentToCourse(courseId, studentId);
        return "redirect:/courses/index/student";
    }

}
