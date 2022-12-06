package com.javacorner.admin.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.UserService;

@Controller
@RequestMapping(value = "/instructors")
public class InstructorController {
    CourseService courseService;
    InstructorService instructorService;
    UserService userService;


    public InstructorController(CourseService courseService, InstructorService instructorService, UserService userService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.userService = userService;
    }


    @GetMapping(value= "/index")
    public String instructors(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Instructor> instructors = instructorService.findInstructorByName(keyword);
        model.addAttribute("listInstructors", instructors);
        model.addAttribute("keyword", keyword);
        return "instructor-views/instructors";
    }

    @GetMapping(value = "/delete")
    public String deleteInstructor(Long instructorId, String keyword){
        instructorService.removeInstructor(instructorId);
        return "redirect:/instructors/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    public String updateInstructor(Model model, Long instructorId){
        Instructor instructor = instructorService.loadInstructorById(1L);
        model.addAttribute("instructor", instructor);
        return "instructor-views/formUpdate";
    }

    @PostMapping(value = "/update")
    public String update(Instructor instructor){
        instructorService.updateInstructor(instructor);
        return "redirect:/instructors/index";
    }

    @GetMapping(value = "/formCreate")
    public String formInstructor(Model model){
        model.addAttribute("instructor", new Instructor());
        return "instructor-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@Valid Instructor instructor, BindingResult bindingResult) {
        User user = userService.loadUserByEmail(instructor.getUser().getEmail());
        if(user != null) bindingResult.rejectValue("user.email", null, "There is already an account registered with that email ");
        if(bindingResult.hasErrors()) return "instructor-views/formCreate";
        instructorService.createInstructor(instructor.getFirstName(), instructor.getLastName(), instructor.getSummary(), instructor.getUser().getEmail(), instructor.getUser().getPassword());
        return "redirect:/instructors/index";
    }

}
