package lk.carnage.repository;

import lk.carnage.db.DbConnection;
import lk.carnage.model.GiftCard;
import lk.carnage.model.Womens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCardRepo {
    public static boolean save(GiftCard giftCard) throws SQLException {
        String sql = "INSERT INTO Gift_Cards VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, giftCard.getId());
        pstm.setObject(2, giftCard.getPrice());
        pstm.setObject(3, giftCard.getType());

        return pstm.executeUpdate() > 0;

    }

    public static List<GiftCard> getAll() throws SQLException {
        String sql = "SELECT * FROM Gift_Cards";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<GiftCard> giftCardList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            double price = resultSet.getDouble(2);
            String type = resultSet.getString(3);

            GiftCard giftCard = new GiftCard(id, price, type);
            giftCardList.add(giftCard);
        }
        return giftCardList;
    }

    public static boolean updateItem(GiftCard giftCard) throws SQLException {
        String sql = "UPDATE Gift_Cards SET price = ?, type = ? WHERE gc_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, giftCard.getPrice());
        pstm.setObject(2, giftCard.getType());
        pstm.setObject(3, giftCard.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Gift_Cards WHERE gc_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
