package org.example.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
