package com.develhope.Exercise0401.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;


    @PostMapping("/newstudent")
    public @ResponseBody ResponseEntity<StudentEntity> addStudent(@RequestBody StudentEntity studentEntity) {
        Optional<StudentEntity> savedStudent = studentService.addStudent(studentEntity);
        if(savedStudent.isPresent()) {
            return ResponseEntity.ok(savedStudent.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/students")
    public @ResponseBody List<StudentEntity> showStudents() {
        return studentService.showStudents();
    }

    @GetMapping("/student/{id}")
    public @ResponseBody ResponseEntity<StudentEntity> showStudent(@PathVariable Long id) {
        Optional<StudentEntity> student = studentService.showStudent(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/updatename/{id}")
    public @ResponseBody StudentEntity updateStudentName(@PathVariable Long id, @RequestParam String newName) {
        return studentService.updateStudentName(id,newName).get();
    }

    @PatchMapping("/updateworking/{id}")
    public @ResponseBody Optional<StudentEntity> updateStudentWorkingStatus(@PathVariable Long id, @RequestParam boolean working) {
        return studentService.setStudentWorkingStatus(id,working);
    }

    @DeleteMapping("/delstudent/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

}
