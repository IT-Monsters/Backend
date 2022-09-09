package com.example.itmonster.controller.request;

import com.example.itmonster.domain.Offer.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestDto {

    private ClassType classType;
}
