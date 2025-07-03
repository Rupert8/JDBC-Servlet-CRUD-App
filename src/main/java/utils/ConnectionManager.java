package utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USERNAME_KEY = "db.username";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static int CONNECTION_POOL_SIZE = 10;
    private static String CONNECTION_POOL_KEY = "db.pool";
    private static BlockingQueue<Connection> pool;

    static {
        loadDriver();
        initConnection();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnection() {
        String poolSize = PropertiesUtils.getProperty(CONNECTION_POOL_KEY);
        int size = poolSize == null ? CONNECTION_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue(size);


        for (int i = 0; i < size; i++) {
            Connection con = getConnection();

            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.equals("close") ?
                    pool.add((Connection) proxy) : method.invoke(con,args));


            pool.add(proxyConnection);

        }
    }

    private ConnectionManager() {
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtils.getProperty(DB_URL_KEY),
                    PropertiesUtils.getProperty(DB_USERNAME_KEY),
                    PropertiesUtils.getProperty(DB_PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnectionFromPool() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
