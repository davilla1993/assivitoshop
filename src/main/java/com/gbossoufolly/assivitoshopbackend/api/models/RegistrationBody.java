package com.gbossoufolly.assivitoshopbackend.api.models;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationBody {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "At least 3 characters are required")
    private String username;

    @Email(message = "Please, enter a correct address email")
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "At least 3 characters are required")
    private String password;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "At least 3 characters are required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "At least 3 characters are required")
    private String lastName;

}
