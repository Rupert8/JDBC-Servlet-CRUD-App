package start;

import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCMain {

    private static String Sql = """
            SELECT * FROM info
            """;

    public static void main(String[] args) {
//        try (Connection connectionManager = ConnectionManager.getConnection();
//             Statement statement = connectionManager.createStatement()) {
//            var result = statement.executeQuery(Sql);
//            while (result.next()) {
//                System.out.println(result.getInt(1));
//
//            }
//        } catch (SQLException e) {
//            System.err.println("Error establishing connection: " + e.getMessage());
//        }
        System.out.println("hello");
        System.out.println(getData(2));

    }

    public static String getData(int id) {
        String result = new String();
        String sql = "SELECT * FROM info WHERE id = %s".formatted(id);

        try (var connection = ConnectionManager.getConnectionFromPool();
             var statement = connection.createStatement()) {

            Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery(sql));
            if(resultSet.isPresent()) {
                while(resultSet.get().next()) {
                    return resultSet.get().getString(2);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


}