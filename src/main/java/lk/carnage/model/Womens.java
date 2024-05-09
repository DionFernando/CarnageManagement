package lk.carnage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Womens {
    private String id;
    private String category;
    private Double price;
    private int qty;
    private String season;
}
