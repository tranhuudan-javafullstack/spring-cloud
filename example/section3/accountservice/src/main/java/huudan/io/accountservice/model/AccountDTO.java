package huudan.io.accountservice.model;

import java.util.Set;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;

    private String name;

    private String password;

    private String username;

    private String email;

    private Set<String> roles;
}
