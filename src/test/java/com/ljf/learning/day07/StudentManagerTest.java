package com.ljf.learning.day07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagerTest {
    @Test
    void increasesCountAfterAddingStudent(){
        StudentManager manager = new StudentManager(2);
        StudentRecord student = new StudentRecord("S001","张三");
        manager.addStudent(student);

        assertEquals(1, manager.getCount());
    }

    @Test
    void rejectsStudentWhenCapacityIsFull(){
        StudentManager manager = new StudentManager(1);
        StudentRecord student1 = new StudentRecord("S001","name1");
        StudentRecord student2 = new StudentRecord("S002","name2");

        boolean add1 = manager.addStudent(student1);
        assertTrue(add1);
        boolean add2 = manager.addStudent(student2);
        assertFalse(add2);
        assertEquals(1, manager.getCount());

    }

    @Test
    void rejectsStudentWithDuplicateId(){
        StudentManager manager = new StudentManager(2);
        StudentRecord student1 = new StudentRecord("S001","name1");
        StudentRecord student2 = new StudentRecord("S001","name2");

        boolean add1 = manager.addStudent(student1);
        assertTrue(add1);
        boolean add2 = manager.addStudent(student2);
        assertFalse(add2);
        assertEquals(1, manager.getCount());
    }

    @Test
    void returnsStudentWhenIdExists(){
        StudentManager manager = new StudentManager(2);
        StudentRecord student1 = new StudentRecord("S001","张三");
        manager.addStudent(student1);

        StudentRecord found = manager.findById("S001");
        assertNotNull(found);
        assertEquals("张三", found.getName());
    }

    @Test
    void returnsNullWhenIdDoesNotExist(){
        StudentManager manager = new StudentManager(2);
        StudentRecord student1 = new StudentRecord("S001","name1");
        manager.addStudent(student1);

        StudentRecord found001 = manager.findById("S001");
        assertNotNull(found001);
        assertEquals("name1", found001.getName());
        StudentRecord found999 = manager.findById("S999");
        assertNull(found999);
    }
}
