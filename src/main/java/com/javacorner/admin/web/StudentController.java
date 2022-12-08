package com.javacorner.admin.web;

import static com.javacorner.admin.constants.JavaCornerConstants.KEYWORD;
import static com.javacorner.admin.constants.JavaCornerConstants.LIST_STUDENTS;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacorner.admin.entity.Student;
import com.javacorner.admin.service.StudentService;

@Controller
@RequestMapping(value = "/students")
public class StudentController {
    
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/index")
    public String students(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword){
        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);
        return "student-views/students";
    }

    @GetMapping(value = "/delete")
    public String deleteStudent(Long studentId, String keyword){
        studentService.removeStudent(studentId);
        return "redirect:/students/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    public String updateStudent(Model model, Long studentId){
        Student student = studentService.loadStudentById(studentId);
        model.addAttribute("student", student);
        return "student-views/formUpdate";
    }

    @PostMapping(value = "/update")
    public String update(Student student){
        studentService.updateStudent(student);
        return "redirect:/students/index";
    }

}
