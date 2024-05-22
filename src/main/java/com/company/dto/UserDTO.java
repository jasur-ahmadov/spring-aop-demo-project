package com.company.dto;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private Integer age;
}