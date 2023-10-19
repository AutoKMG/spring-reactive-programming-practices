package com.khaledsaleh.studentsapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "student")
public class Student {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
}
