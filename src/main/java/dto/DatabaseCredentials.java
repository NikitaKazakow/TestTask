package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseCredentials {
    private String url;
    private String user;
    private String password;
}
