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
        String sql = "INSERT INTO Emp_Attendance VALUES(?,?,?)";

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

    public static List<String> getEmployeeAttend(String empId) throws SQLException {
       // String sql = "SELECT Attendance FROM Emp_Attendance WHERE MONTH(Attendance) =" + month + " AND emp_id = '" + empId + "';";
        // String sql = "SELECT Attendance FROM Emp_Attendance";
        String sql = "SELECT Attendance FROM Emp_Attendance WHERE emp_id = '" + empId + "';";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> empAttendList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        while (resultSet.next()) {
            String attend = resultSet.getString(1);
            empAttendList.add(attend);
        }

        return empAttendList;


    }
}
