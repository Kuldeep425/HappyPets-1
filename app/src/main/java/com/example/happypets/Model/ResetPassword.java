package com.example.happypets.Model;

public class ResetPassword {

    private String resetToken;
    private String newPassword;

    public ResetPassword(String resetToken, String newPassword){
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
