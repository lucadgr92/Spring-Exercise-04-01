package com.develhope.Exercise0401.services;

        import com.develhope.Exercise0401.students.StudentEntity;
        import com.develhope.Exercise0401.students.StudentRepository;
        import com.develhope.Exercise0401.students.StudentService;
        import org.junit.jupiter.api.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.ActiveProfiles;
        import org.springframework.test.context.TestPropertySource;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;
        import java.util.Optional;

        import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
        import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTests {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    private StudentEntity createStudent () {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("Mario");
        studentEntity.setSurname("Rossi");
        studentEntity.setIsWorking(false);

        return studentEntity;
    }

    @Test
    @Transactional
    void setStudentWorkingStatusValidId() {
        StudentEntity savedStudent = studentRepository.saveAndFlush(createStudent());
        long savedStudentId = savedStudent.getId();

        Optional<StudentEntity> updatedStudent = studentService.setStudentWorkingStatus(savedStudentId, true);
        assertTrue(updatedStudent.get().getIsWorking());
        assertNotNull(updatedStudent);

        assertTrue(studentRepository.getById(savedStudentId).getIsWorking());
        assertNotNull(studentRepository.getById(savedStudentId));
    }

    @Test
    void setStudentWorkingStatusInvalidId() {
        Optional<StudentEntity> result = studentService.setStudentWorkingStatus(-1, true);
        assertEquals(result, Optional.empty());

    }

    @Test
    void setStudentWorkingStatusNullStatus() {
        Optional<StudentEntity> result = studentService.setStudentWorkingStatus(1, null);
        assertEquals(result, Optional.empty());
    }

    @Test
    void updStudentNameInvalidId() {
        Optional<StudentEntity> result = studentService.updateStudentName(-1, "Mario");
        assertEquals(result, Optional.empty());
    }

    @Test
    void updStudentNameNullName() {
        Optional<StudentEntity> result = studentService.updateStudentName(1, null);
        assertEquals(result, Optional.empty());
    }

    @Test
    @Transactional
    void updStudentNameValidId() {
        StudentEntity savedStudent = studentRepository.saveAndFlush(createStudent());
        long savedStudentId = savedStudent.getId();

        Optional<StudentEntity> updatedStudent = studentService.updateStudentName(savedStudentId, "Paolo");
        assertEquals(updatedStudent.get().getName(), "Paolo");
        assertNotNull(updatedStudent);

    }

    @Test
    @Transactional
    void addStudentSuccessfulAddition() {
        Optional<StudentEntity> savedStudent = studentService.addStudent(createStudent());
        Long savedStudentId = savedStudent.get().getId();

        StudentEntity retrievedStudent = studentRepository.findById(savedStudentId).get();
        assertEquals(retrievedStudent.getId(),savedStudentId);
        assertNotNull(retrievedStudent);
    }

    @Test
    @Transactional
    void addStudentNullInput() {
        Optional<StudentEntity> savedStudent = studentService.addStudent(null);
        assertEquals(savedStudent, Optional.empty());
    }

    @Test
    @Transactional
    void showStudentsCompleteList() {
        studentRepository.deleteAll();

        StudentEntity studentEntityOne = createStudent();
        StudentEntity studentEntityTwo = createStudent();
        StudentEntity studentEntityThree = createStudent();
        StudentEntity studentEntityFour = createStudent();
        StudentEntity studentEntityFive = createStudent();

        studentRepository.save(studentEntityOne);
        studentRepository.save(studentEntityTwo);
        studentRepository.save(studentEntityThree);
        studentRepository.save(studentEntityFour);
        studentRepository.save(studentEntityFive);

        List<StudentEntity> studentEntityList = studentService.showStudents();
        assertEquals(studentEntityList.size(), 5);
    }

    @Test
    @Transactional
    void showStudentInvalidId() {
        Optional<StudentEntity> studentEntity = studentService.showStudent(Long.valueOf(-1));
        assertEquals(studentEntity, Optional.empty());
    }

    @Test
    @Transactional
    void showStudentValidID() {
        StudentEntity savedStudent = studentRepository.save(createStudent());
        Optional<StudentEntity> foundStudent = studentService.showStudent(Long.valueOf(savedStudent.getId()));
        assertNotNull(foundStudent.get());
        assertEquals(savedStudent.getId(), foundStudent.get().getId());
    }

    @Test
    @Transactional
    void deleteStudent() {
        Long savedStudentId = studentRepository.save(createStudent()).getId();
        studentService.deleteStudent(savedStudentId);
        Optional<StudentEntity> studentToVerify = studentRepository.findById(savedStudentId);
        assertTrue(!studentToVerify.isPresent());

    }

}

