package com.develhope.Exercise0401;

import com.develhope.Exercise0401.students.StudentEntity;
import com.develhope.Exercise0401.students.StudentRepository;
import com.develhope.Exercise0401.students.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Exercise0401ApplicationTests {



	@Test
	void contextLoads() {
	}


}
