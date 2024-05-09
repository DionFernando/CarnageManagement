package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.EmpAttend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmpAttendRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT empAttend_id FROM Emp_Attendance ORDER BY empAttend_id DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean save(EmpAttend empAttend) throws SQLException {
        String sql = "INSERT INTO Product Emp_Attendance(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, empAttend.getEmpAttend_id());
        pstm.setObject(2, empAttend.getEmp_id());
        pstm.setObject(3, empAttend.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static EmpAttend searchById(String daysCount) throws SQLException {
       /* String sql = "SELECT Attendance FROM Emp_Attendance WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, daysCount.getEmpAttend_id());
        pstm.setObject(2, daysCount.getEmp_id());
        pstm.setObject(3, daysCount.getDate());

        return pstm.executeUpdate() > 0;*/
        return null;
    }

    public static List<EmpAttend> getEmployeeAttend() throws SQLException {
        String sql = "SELECT Attendance FROM Emp_Attendance WHERE MONTH(Date) = MONTH(CURRENT_DATE()) AND emp_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmpAttend> empAttendList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

       /* while (resultSet.next()) {
            String id = resultSet.getString(1);
            String eid = resultSet.getString(2);
            String date = resultSet.getString(3);

            EmpAttend empAttend = null;
            try {
                empAttend = new EmpAttend(id, eid, formatter.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            empAttendList.add(empAttend);
        }*/
        return empAttendList;
    }
}
