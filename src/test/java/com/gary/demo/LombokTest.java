package com.gary.demo;

import com.gary.demo.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 平台名-子平台名-模块名:
 * <p>
 * [注释信息]
 *
 * @author gary.pu  2018-10-08
 */
@Slf4j
@RunWith(JUnit4.class)
public class LombokTest {
    @Test
    public void testLombok(){
        log.info("start test lombok..");
        Student student = new Student();
        student.setAge(10);
        Assert.assertEquals(student.getAge(),10);
        student.setMale("male");
        student = new Student("gary",10,"male","001");
        Assert.assertEquals(student.getName(),"gary");
        log.info("hashCode is "+String.valueOf(student.hashCode()));
        log.info(student.toString());
    }
}
