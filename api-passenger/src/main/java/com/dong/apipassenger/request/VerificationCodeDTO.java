package com.dong.apipassenger.request;

public class VerificationCodeDTO {

    private String passengerPhone;

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public VerificationCodeDTO(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public VerificationCodeDTO() {
    }
}
