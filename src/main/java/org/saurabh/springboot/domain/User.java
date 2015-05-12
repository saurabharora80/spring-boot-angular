package org.saurabh.springboot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.saurabh.springboot.domain.validation.MustBeOfType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

public class User {

    @Id
    private String id;

    @NotNull(message = "null_gender")
    @MustBeOfType(value = Gender.class, message = "invalid_gender")
    private String gender;

    @NotNull(message = "null_centre")
    @MustBeOfType(value = Centre.class, message = "invalid_centre")
    private String centre;

    @NotNull(message = "null_firstname")
    private String firstName;

    @NotNull(message = "null_lastname")
    private String lastName;

    private DateOfBirth dateOfBirth;
    private PhoneNumber phoneNumber;

    @JsonCreator
    public User(@JsonProperty("gender") String gender, @JsonProperty("centre") String centre, @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName, @JsonProperty("dateOfBirth") DateOfBirth dateOfBirth,
                @JsonProperty("phoneNumber") PhoneNumber phoneNumber) {
        this.gender = gender;
        this.centre = centre;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return Gender.valueOf(gender);
    }

    public Centre getCentre() {
        return Centre.valueOf(centre);
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public static class DateOfBirth {

        @NotNull
        @Min(1)
        @Max(31)
        private int day;

        @Min(1)
        @Max(31)
        private int month;

        @Min(1910)
        @Max(2100)
        private int year;

        @JsonCreator
        public DateOfBirth(@JsonProperty("day") int day, @JsonProperty("month") int month, @JsonProperty("year") int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

    }


}

