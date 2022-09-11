package com.example.itmonster.controller.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequestDto {
    private String phoneNo;
    private String authNo;

}
