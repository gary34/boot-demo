package com.gary.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 平台名-子平台名-模块名:
 * <p>
 * [注释信息]
 *
 * @author gary.pu  2018-10-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
    private String male;
    private String studentNo;
}
