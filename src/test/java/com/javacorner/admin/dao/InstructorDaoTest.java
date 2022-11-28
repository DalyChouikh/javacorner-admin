package com.javacorner.admin.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.javacorner.admin.AbstractTest;
import com.javacorner.admin.entity.Instructor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/javacorner-admin-db.sql"})
public class InstructorDaoTest extends AbstractTest {

    @Autowired
    private InstructorDao instructorDao;

    @Test
    void testFindInstructorByEmail() {
        List<Instructor> instructors = instructorDao.findInstructorsByNameContains("instructor1FN");
        int expectedValue = 1;
        assertEquals(expectedValue, instructors.size());

    }

    @Test
    void testFindInstructorsByNameContains() {
        Instructor expectedInstructor = new Instructor();
        expectedInstructor.setInstructorId(1L);
        expectedInstructor.setFirstName("instructor1FN"); 
        expectedInstructor.setLastName("instructor1LN"); 
        expectedInstructor.setSummary("Experienced Instructor");
        Instructor instructor = instructorDao.findInstructorByEmail("instructorUser1@gmail.com");

        assertEquals(expectedInstructor, instructor);
    }

    @Test
    void testFindInstructorByNotExistingEmail(){
        Instructor instructor = instructorDao.findInstructorByEmail("javacorner@gmail.com");
        assertNull(instructor);
    }
}
