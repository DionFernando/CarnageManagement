package lk.carnage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpSalary {
    String empID;
    String attend;
    String salary;
    String bonus;
    String finalSalary;

}
