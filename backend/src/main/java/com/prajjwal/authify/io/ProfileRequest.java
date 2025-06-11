package com.prajjwal.authify.io;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "Name Should be not Empty")
    private String name;
    @Email(message = "Enter Valid email address")
    @NotNull(message = "Email Should not be empty")
    private String email;
    @Size(min= 6,message ="Password must be atleast 6 characters" )
    private String password;
}
