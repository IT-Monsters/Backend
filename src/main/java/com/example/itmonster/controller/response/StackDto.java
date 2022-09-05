package com.example.itmonster.controller.response;

import com.example.itmonster.domain.StackOfQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StackDto {
    private String stackName;

    public StackDto( StackOfQuest stack ){
        stackName = stack.getStackName();
    }
}
