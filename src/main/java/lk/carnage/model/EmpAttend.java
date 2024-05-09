package lk.carnage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpAttend {
    private String empAttend_id;
    private String emp_id;
    private Date date;
}
