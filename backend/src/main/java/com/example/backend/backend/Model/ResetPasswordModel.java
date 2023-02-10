package com.example.backend.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordModel {
    private String email;
    private int otp;
    private String newPassword;
}
