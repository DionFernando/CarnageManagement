package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setDate(2, order.getDate());
        pstm.setString(3, order.getCustomerId());

        return pstm.executeUpdate() > 0;
    }

    public static String getCurrentName() throws SQLException {
        String sql = "SELECT cus_id FROM Orders ORDER BY oid DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String name = resultSet.getString(1);
            return name;
        }
        return null;
    }
}
