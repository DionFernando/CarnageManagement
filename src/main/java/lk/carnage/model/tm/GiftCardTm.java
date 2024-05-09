package lk.carnage.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GiftCardTm {
    private String id;
    private String type;
    private Double price;

}
