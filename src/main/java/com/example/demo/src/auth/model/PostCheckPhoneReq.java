package com.example.demo.src.auth.model;

import com.example.demo.common.validation.annotation.PhoneForm;
import com.example.demo.common.validation.annotation.PhoneUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCheckPhoneReq {

    @Schema(description = "휴대폰 번호", example = "+82-10-1234-5678")
    @NotNull
    @PhoneUnique
    @Size(max = 20)
    @PhoneForm
    private String phoneNumber;
}
