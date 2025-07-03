package entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Products {
    private int id;
    private String productName;
    private String description;
    private double price;
}
