package fr.jbrunet.app.awesome.pojos;

import org.hibernate.validator.constraints.Length;

/**
 * Created by Julien BRUNET on 24/09/2014.
 */
public class User {

    private Long id;

    @Length(max = 50)
    private String firstName;

    @Length(max = 50)
    private String lastName;

    private String email;

    @Length(max = 20)
    private String userName;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
