package com.example.demo.src.auth.model;

import com.example.demo.common.validation.annotation.MustBeTrue;
import com.example.demo.common.validation.annotation.PhoneNumber;
import com.example.demo.src.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    @NotNull
    @Size(max = 20)
    @PhoneNumber
    private String phoneNumber;

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    @Size(max = 20)
    private String userId;

    @NotNull
    private String birth;

    @NotNull
    @Size(max = 20)
    private String password;

    private boolean isOAuth;

    @MustBeTrue
    private boolean termsAgreed;
    @NotNull
    private LocalDateTime termsAgreedDate;


    public User toEntity() {
        return User.builder()
                .phoneNumber(this.phoneNumber)
                .name(this.name)
                .userId(this.userId)
                .birth(this.birth)
                .password(this.password)
                .isOAuth(this.isOAuth)
                .termsAgreed(this.termsAgreed)
                .termsAgreedDate(this.termsAgreedDate)
                .build();
    }
}
