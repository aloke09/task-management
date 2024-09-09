package com.bridgelabz.UserManagement1.responseDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO
{
    private String username;
    private String email;
    private String msg;
}
