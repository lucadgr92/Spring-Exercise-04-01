package com.develhope.Exercise0401.controllers;

import com.develhope.Exercise0401.students.StudentController;
import com.develhope.Exercise0401.students.StudentEntity;
import com.develhope.Exercise0401.students.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private StudentEntity createStudent () {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(Long.valueOf(1));
        studentEntity.setName("Mario");
        studentEntity.setSurname("Rossi");
        studentEntity.setIsWorking(false);

        return studentEntity;
    }
    private void createAndSaveFourStudents() {

        studentRepository.deleteAll();

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(Long.valueOf(1));
        studentEntity.setName("Mario");
        studentEntity.setSurname("Rossi");
        studentEntity.setIsWorking(false);

        StudentEntity studentEntityTwo = new StudentEntity();
        studentEntity.setId(Long.valueOf(2));
        studentEntity.setName("Anna");
        studentEntity.setSurname("Mannino");
        studentEntity.setIsWorking(false);

        StudentEntity studentEntityThree = new StudentEntity();
        studentEntity.setId(Long.valueOf(3));
        studentEntity.setName("Luca");
        studentEntity.setSurname("Bianchi");
        studentEntity.setIsWorking(false);

        StudentEntity studentEntityFour = new StudentEntity();
        studentEntity.setId(Long.valueOf(4));
        studentEntity.setName("Romano");
        studentEntity.setSurname("Fini");
        studentEntity.setIsWorking(false);

        studentRepository.save(studentEntity);
        studentRepository.save(studentEntityTwo);
        studentRepository.save(studentEntityThree);
        studentRepository.save(studentEntityFour);}


    @Test
    void addStudent() throws Exception {
        StudentEntity studentEntity = createStudent();
        String studentJSON = objectMapper.writeValueAsString(studentEntity);
        MvcResult result = this.mockMvc.perform(post("/v1/newstudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), StudentEntity.class);
    }

    @Test
    void showStudents() throws Exception {
        createAndSaveFourStudents();

        MvcResult result = mockMvc.perform(get("/v1/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<StudentEntity> studentsFromResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(),List.class);
        assertEquals(4, studentsFromResponse.size());
        System.out.println("Students in DB are: " + studentsFromResponse.size());
    }

    @Test
    void showStudent() throws Exception {
        StudentEntity student = studentRepository.save(createStudent());
        MvcResult result = this.mockMvc.perform(get("/v1/student/" + student.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(), StudentEntity.class);
        assertEquals(studentFromResponse.getId(),student.getId());
    }

    @Test
    void deleteStudent() throws Exception {
        StudentEntity student = studentRepository.save(createStudent());

        this.mockMvc.perform(delete("/v1/delstudent/"+student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Optional<StudentEntity> studentEntity = studentRepository.findById(student.getId());
        assertEquals(studentEntity,Optional.empty());
    }

    @Test
    void updateStudentName() throws Exception {
        StudentEntity student = studentRepository.save(createStudent());

        String newName = "Paolo";

        String studentJSON = objectMapper.writeValueAsString(student);
        MvcResult result = this.mockMvc.perform(patch("/v1/updatename/"+student.getId()+"?newName="+newName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(),StudentEntity.class);
        assertEquals(studentFromResponse.getId(),student.getId());
        assertEquals(studentFromResponse.getName(),newName);
    }

    @Test
    void updateStudentWorkingStatus() throws Exception {
        StudentEntity student = studentRepository.save(createStudent());

        Boolean newStatus = true;

        String studentJSON = objectMapper.writeValueAsString(student);
        MvcResult result = this.mockMvc.perform(patch("/v1/updateworking/"+student.getId()+"?working="+newStatus)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse()
                .getContentAsString(),StudentEntity.class);
        assertEquals(studentFromResponse.getId(),student.getId());
        assertTrue(studentFromResponse.getIsWorking());
    }

}
