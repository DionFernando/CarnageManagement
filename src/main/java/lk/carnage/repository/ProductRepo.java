package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.OrderDetail;
import lk.carnage.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT prod_id FROM Product";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Product searchById(String code) throws SQLException {
        String sql = "SELECT * FROM Product WHERE prod_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static boolean update(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            boolean isUpdateQty = updateQty(od.getItemCode(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String itemCode, int qty) throws SQLException {
        String sql = "UPDATE Product SET qty = qty - ? WHERE prod_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateItem(Product product) throws SQLException {
        String sql = "UPDATE Product SET category = ? , price = ? , qty = ? , season = ? WHERE prod_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, product.getCategory());
        pstm.setObject(2, product.getPrice());
        pstm.setObject(3, product.getQty());
        pstm.setObject(4, product.getSeason());
        pstm.setObject(5, product.getId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Product WHERE prod_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static String qtyCount() throws SQLException {
        String sql = "SELECT prod_id from Product WHERE qty <= 10";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<String> prodIds = new ArrayList<>();
        while (resultSet.next()) {
            prodIds.add(resultSet.getString("prod_id"));
        }

        return prodIds.toString();
    }
}
