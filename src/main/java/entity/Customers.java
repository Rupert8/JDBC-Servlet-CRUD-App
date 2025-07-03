package entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Customers {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
