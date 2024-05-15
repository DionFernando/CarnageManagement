package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.Accessories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesRepo {
    public static boolean save(Accessories accessories) throws SQLException {
        String sql = "INSERT INTO Product VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, accessories.getId());
        pstm.setObject(2, accessories.getCategory());
        pstm.setObject(3, accessories.getPrice());
        pstm.setObject(4, accessories.getQty());
        pstm.setObject(5, accessories.getSeason());

        return pstm.executeUpdate() > 0;

    }

    public static List<Accessories> getAll() throws SQLException {
        String sql = "SELECT * FROM Product WHERE prod_id LIKE 'A%'";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Accessories> accessoriesList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String category = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            int qty = resultSet.getInt(4);
            String season = resultSet.getString(5);

            Accessories accessories = new Accessories(id, category, price, qty, season);
            accessoriesList.add(accessories);
        }
        return accessoriesList;
    }

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT prod_id FROM Product WHERE prod_id LIKE 'A%' ORDER BY prod_id DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }
}
