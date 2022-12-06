package com.javacorner.admin.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;

@Controller
@RequestMapping(value = "/instructors")
public class InstructorController {
    CourseService courseService;
    InstructorService instructorService;


    public InstructorController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }


    @GetMapping(value= "/index")
    public String instructors(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Instructor> instructors = instructorService.findInstructorByName(keyword);
        model.addAttribute("listInstructors", instructors);
        model.addAttribute("keyword", keyword);
        return "instructor-views/instructors";
    }

}
