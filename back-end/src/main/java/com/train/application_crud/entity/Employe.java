package com.train.application_crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String employeName;

    private String employeSurname;

    private String employeEmail;

    private Number employePhone;

    private String employePassword;

    private String employeDepartment;

}
