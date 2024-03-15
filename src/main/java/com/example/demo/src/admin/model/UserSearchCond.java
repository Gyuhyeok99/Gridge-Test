package com.example.demo.src.admin.model;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.validation.annotation.LocalDateTimeForm;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchCond {

    private String name;
    private String username;
    @LocalDateTimeForm
    private String signupDate;
    private State state;
}
