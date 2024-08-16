package shoeStore.shoeStore.project.models;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Admin {
    private String username;
    private String password;
    private boolean loggedIn;

}
