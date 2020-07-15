import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DBController {

    private static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    public DBController(){
    }

    protected void initDBConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected Map<String, List<String>> loadItems() {
        Map<String, List<String>> items = new HashMap<>();
        items.put(testaufgabe.warsch[0], new ArrayList<>());
        items.put(testaufgabe.warsch[1], new ArrayList<>());
        items.put(testaufgabe.warsch[2], new ArrayList<>());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table if not exists items ( id integer constraint items_pk primary key autoincrement, warsch TEXT not null, string TEXT);");
            ResultSet rs = statement.executeQuery("SELECT * FROM items;");
            while (rs.next()) {
                List<String> itemsInside = items.get(rs.getString("warsch"));
                itemsInside.add(rs.getString("string"));
                items.put(rs.getString("warsch"), itemsInside);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }

    protected void addItem(String string, String string2) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("create table if not exists items ( id integer constraint items_pk primary key autoincrement, warsch TEXT not null, string TEXT);");
            stmt.execute("INSERT INTO items (warsch, string) VALUES ('" + string + "', '" + string2 + "')");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }

    protected void closeDBConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}