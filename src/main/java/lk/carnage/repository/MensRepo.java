package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.Mens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MensRepo {
    public static boolean save(Mens mens) throws SQLException {
        String sql = "INSERT INTO Product VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, mens.getId());
        pstm.setObject(2, mens.getCategory());
        pstm.setObject(3, mens.getPrice());
        pstm.setObject(4, mens.getQty());
        pstm.setObject(5, mens.getSeason());

        return pstm.executeUpdate() > 0;

    }

    public static List<Mens> getAll() throws SQLException {
        String sql = "SELECT * FROM Product WHERE prod_id LIKE 'M%'";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Mens> mensList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String category = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            int qty = resultSet.getInt(4);
            String season = resultSet.getString(5);

            Mens mens = new Mens(id, category, price, qty, season);
            mensList.add(mens);
        }
        return mensList;
    }

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT prod_id FROM Product WHERE prod_id LIKE 'M%' ORDER BY prod_id DESC LIMIT 1;";

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
