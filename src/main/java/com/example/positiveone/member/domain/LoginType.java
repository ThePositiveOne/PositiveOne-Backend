package com.example.positiveone.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum LoginType {

    APPLE("apple"),
    POSITIVE_ONE("positiveOne");

    private String value;

}
