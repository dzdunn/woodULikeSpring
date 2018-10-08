package com.dunn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class User {

  //  @Id
    //@GeneratedValue(GenerationType.AUTO)
    public Long id;
    public String firstName;
    public String lastName;
    public String password;
}
