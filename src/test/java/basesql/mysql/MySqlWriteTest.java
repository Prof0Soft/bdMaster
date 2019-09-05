package basesql.mysql;

import basesql.mysql.connect.ConnectionToMySQL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.*;

/**
 * <ToWriteJavaDoc>
 * <p>
 * Project: importbasefoxpro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 09.04.2019
 */
public class MySqlWriteTest {

    private ConnectionToMySQL connection;

    @Test
    public void checkConnectionToServer() {
        connection = new ConnectionToMySQL();
        assertNotNull(connection);
    }

    @Test
    public void isExistTable() {
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            MySqlWrite mySqlWrite = new MySqlWrite();
            //language=MySQL
            String sqlQuery = "CREATE TABLE `prom_old`.`testololo` (`FieldTest` INT NULL) COLLATE='latin1_swedish_ci'";
            PreparedStatement statement = connection.getPrepareStm(sqlQuery);
            statement.executeUpdate();

            int result = mySqlWrite.isExistTable("testololo");

            sqlQuery = "DROP table `prom_old`.`testololo`";
            statement.execute(sqlQuery);

            assertEquals(1, result);

            result = mySqlWrite.isExistTable("testololo");
            assertEquals(result, -1);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

    }

    @Test
    public void dropTable() {
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            String nameTable = "testololo";
            MySqlWrite mySqlWrite = new MySqlWrite();
            //language=MySQL
            String sqlQuery = "CREATE TABLE `prom_old`.`" + nameTable + "` (`FieldTest` INT NULL) COLLATE='latin1_swedish_ci'";
            PreparedStatement statement = connection.getPrepareStm(sqlQuery);
            statement.executeUpdate();

            mySqlWrite.dropTable(nameTable);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }
    }
}
