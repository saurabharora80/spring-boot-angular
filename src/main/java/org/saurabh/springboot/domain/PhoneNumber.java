package org.saurabh.springboot.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PhoneNumber {
    private static final String phoneNumberPattern = "(00447|+447|447|07)(\\d){9}";

    @NotNull(message = "null_phonenumber")
    @Pattern(regexp = phoneNumberPattern)
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
