package lk.carnage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GiftCard {
    private String id;
    private Double price;
    private String type;
}
