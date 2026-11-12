
package com.example.springsecuritytemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String username;
    private String email;
    private Set<String> roles;
}
