package lk.carnage.repository;

import lk.carnage.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {

    public static String getCurrentID() throws SQLException {
        String sql = "SELECT cred_id FROM Credential ORDER BY cred_id DESC LIMIT 1;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }
}
