package lk.carnage.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpSalaryTm {
    String empID;
    String attend;
    String salary;
    String bonus;
    String finalSalary;
}
