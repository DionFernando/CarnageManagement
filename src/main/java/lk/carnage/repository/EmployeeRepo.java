package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getTel());

        return pstm.executeUpdate() > 0;

    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int tel = resultSet.getInt(3);

            Employee employee = new Employee(id, name, tel);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET name = ? , Telephone = ? WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getTel());
        pstm.setObject(3, employee.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getEmpID() throws SQLException {
        String sql = "SELECT emp_id FROM Employee";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> telList = new ArrayList<>();
        while (resultSet.next()) {
            telList.add(resultSet.getString(1));
        }
        return telList;
    }

    public static Employee searchByTel(String tel) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE emp_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, tel);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tele = resultSet.getString(3);

            Employee employee = new Employee(cus_id, name, Integer.parseInt(tele));

            return employee;
        }

        return null;
    }

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT emp_id FROM Employee ORDER BY emp_id DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    /*public static List<EmpAttend> getEmployeeAttend() throws SQLException {
        String sql = "SELECT Attendance FROM Emp_Attendance WHERE MONTH(Date) = MONTH(CURRENT_DATE()) AND emp_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmpAttend> empAttendList = new ArrayList<>();

        while (resultSet.next()) {
          //  Date id = resultSet.getString(1);

           // EmpAttend empAttend = new EmpAttend(id);

          //  empAttendList.add(empAttend);
        }
        return empAttendList;
    }*/
}
