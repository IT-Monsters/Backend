package com.example.itsquad.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Data
@Builder
public class SignupRequestDto {

    private String email;

    private String nickname;

    private String password;

    @Nullable
    private String profileImage;

}
