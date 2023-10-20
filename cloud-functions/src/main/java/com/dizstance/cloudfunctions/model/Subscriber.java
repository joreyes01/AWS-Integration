package com.dizstance.cloudfunctions.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Subscriber {
    private Integer id;
    private String email;
}
