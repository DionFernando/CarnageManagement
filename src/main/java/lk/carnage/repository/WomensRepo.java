package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.Womens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WomensRepo {
    public static boolean save(Womens womens) throws SQLException {
        String sql = "INSERT INTO Product VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, womens.getId());
        pstm.setObject(2, womens.getCategory());
        pstm.setObject(3, womens.getPrice());
        pstm.setObject(4, womens.getQty());
        pstm.setObject(5, womens.getSeason());

        return pstm.executeUpdate() > 0;

    }

    public static List<Womens> getAll() throws SQLException {
        String sql = "SELECT * FROM Product WHERE prod_id LIKE 'W%'";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Womens> womensList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String category = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            int qty = resultSet.getInt(4);
            String season = resultSet.getString(5);

            Womens womens = new Womens(id, category, price, qty, season);
            womensList.add(womens);
        }
        return womensList;
    }

    public static boolean update(Womens womens) throws SQLException {
        String sql = "UPDATE Product SET category = ?, price = ?, qty = ?, season = ? WHERE prod_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, womens.getCategory());
        pstm.setObject(2, womens.getPrice());
        pstm.setObject(3, womens.getQty());
        pstm.setObject(4, womens.getSeason());
        pstm.setObject(5, womens.getId());

        return pstm.executeUpdate() > 0;
    }

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT prod_id FROM Product WHERE prod_id LIKE 'W%' ORDER BY prod_id DESC LIMIT 1;";

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
