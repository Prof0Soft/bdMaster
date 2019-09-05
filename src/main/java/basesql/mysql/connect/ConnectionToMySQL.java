package basesql.mysql.connect;

import setting.Constant;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * For connection and work with MySQL server
 *
 * @author Sergey B. <Prof0Soft@gmail.com>
 */
public class ConnectionToMySQL implements AutoCloseable {

    private String url;                 // url server for connection
    private String userName;            // login for enter to server
    private String password;            // password for enter to server
    private Connection connect;         // the connection MySQL
    private Statement statmt;           // statement and returning the result it produces
    private ResultSet resSet;           // result from query

    /**
     * Default constructor
     */
    public ConnectionToMySQL() {
        this.url = Constant.DB_URL;
        this.userName = Constant.DB_LOGIN;
        this.password = Constant.DB_PASSWORD;
        this.connect = connectToServer();
    }

    /**
     * Constructor with dependenses for coonect to DB.
     *
     * @param url      String: URL server.
     * @param userName String: login for entering on server.
     * @param password String: password for enterig on server.
     */
    public ConnectionToMySQL(final String url, final String userName, final String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.connect = connectToServer();
    }

    /**
     * Connect to MySQL server
     *
     * @return return <b>true</b> if connect was created
     */
    private Connection connectToServer() {
        try {
            Class.forName(Constant.DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionToMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connect = DriverManager.getConnection(url, userName, password);
            statmt = connect.createStatement();
        } catch (SQLException exSQL) {
            exSQL.printStackTrace();
        }
        return connect;
    }

    /**
     * Return result query
     *
     * @param query String: for base MySQL (Exam. SELECT * FROM ExTable)
     * @return - result (ResultSet) from MySQL base
     */
    public ResultSet getResultQuery(final String query) {
        try {
            resSet = statmt.executeQuery(query);
        } catch (SQLException exSQL) {
            exSQL.printStackTrace();
        }
        return resSet;
    }

    /**
     * Return prepared statement for use query DB.
     *
     * @param query String: query for DB.
     * @return PreparedStatement: for use query DB.
     * @throws SQLException SQL Exception.
     */
    public PreparedStatement getPrepareStm(final String query) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connect.prepareStatement(query);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return preparedStatement;
    }

    @Override
    public void close() {
        try {
            closeProcces();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconect from server manual.
     *
     * @return If disonected return true.
     */
    public boolean closeManual() {
        boolean isDisconnect = false;
        try {
            closeProcces();
            isDisconnect = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDisconnect;
    }

    private void closeProcces() throws SQLException {
        if (resSet != null) {
            resSet.close();
        }
        if (statmt != null) {
            statmt.close();
        }
        if (connect != null) {
            connect.close();
        }
    }
}
