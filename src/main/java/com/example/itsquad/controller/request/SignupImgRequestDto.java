package com.example.itsquad.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignupImgRequestDto {

    private String email;

    private String nickname;

    private String password;

    private String profileImage;

    private String phoneNum;

    private boolean admin = false;
}
