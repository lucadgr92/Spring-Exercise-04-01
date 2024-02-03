package com.develhope.Exercise0401.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Optional<StudentEntity> setStudentWorkingStatus(long studentId, Boolean isWorking) {
        if (Objects.equals(isWorking, null)) {
            return Optional.empty();
        } else {
            Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);

            if (!studentEntity.isPresent()) {
                return Optional.empty();
            }

            studentEntity.get().setIsWorking(isWorking);
            Optional<StudentEntity> savedEntity = Optional.of(studentRepository.save(studentEntity.get()));
            return savedEntity;
        }
    }

    public Optional<StudentEntity> updateStudentName(long id, String newName) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if (!studentEntity.isPresent()) {
            return Optional.empty();
        } else {
            if (Objects.equals(newName, null)) {
                return Optional.empty();
            }
            studentEntity.get().setName(newName);
            Optional<StudentEntity> savedEntity = Optional.of(studentRepository.save(studentEntity.get()));
            return savedEntity;
        }
    }

    public Optional<StudentEntity> addStudent (StudentEntity studentEntity) {
        if(studentEntity == null) {
            return Optional.empty();
        } else {
            return Optional.of(studentRepository.save(studentEntity));
        }
    }

    public List<StudentEntity> showStudents () {
    return studentRepository.findAll();
    }

    public Optional<StudentEntity> showStudent (Long id) {
        Optional<StudentEntity> studentById = studentRepository.findById(id);
        if(!studentById.isPresent()) return Optional.empty();
        return studentById;
    }

    public void deleteStudent (Long id) {
        studentRepository.deleteById(id);
    }
}
