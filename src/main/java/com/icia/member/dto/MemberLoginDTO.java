package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본생정자
@AllArgsConstructor // 매개변수 있는 생성자
public class MemberLoginDTO {
    private String memberEmail;
    private String memberPassword;

}