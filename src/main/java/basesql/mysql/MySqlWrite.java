package basesql.mysql;

import basesql.SqlWrite;
import basesql.mysql.connect.ConnectionToMySQL;
import com.linuxense.javadbf.DBFField;
import model.DbfBase;
import model.Field;
import model.Row;
import org.apache.log4j.Logger;
import setting.Constant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Write data to MySQL server.
 * <p>
 * Project: importbasefoxpro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
public class MySqlWrite implements SqlWrite {

    private Logger logger = Logger.getLogger(MySqlWrite.class);
    private String nameSchema;

    /**
     * Default constructor.
     */
    public MySqlWrite() {
        this.nameSchema = Constant.DB_NAMESCHEMA;
    }

    @Override
    public void writeData(final DbfBase dbfBases) {
        logger.info("Start write data " + dbfBases.getNameBase() + " to the SQL server. Server info : "
                + Constant.DB_DRIVER + " ::: " + Constant.DB_URL + " ::: " + Constant.DB_NAMESCHEMA);

        // check if base existed.
        int countFileds = isExistTable(dbfBases.getNameBase());

        if (countFileds > 0 && countFileds == dbfBases.getDbfFields().size() && 1 == 2) {
            int i = 0;
        } else {
            if (countFileds > 0) {
                dropTable(dbfBases.getNameBase());
            }
            createNewTable(dbfBases);
            addNewRecordsToTable(dbfBases);
        }

    }

    private void addNewRecordsToTable(final DbfBase dbfBase) {
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            String sqlQuery = constructSqlQueryInsertAllData(dbfBase);

            connection.getPrepareStm(sqlQuery).execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String constructSqlQueryInsertAllData(final DbfBase dbfBase) {
        StringBuilder queryResult = new StringBuilder("INSERT INTO " + nameSchema + "." + dbfBase.getNameBase() + " (");
        for (int i = 0; i < dbfBase.getDbfFields().size(); i++) {
            queryResult.append(dbfBase.getDbfFields().get(i).getName());
            if (dbfBase.getDbfFields().size() > i + 1) {
                queryResult.append(", ");
            }
        }

        queryResult.append(") VALUES ");

        List<Row> rows = dbfBase.getRows();
        boolean isValuesFirst = true;
        for (Row row : rows) {
            if (!isValuesFirst) {
                queryResult.append(", ");
            }
            List<Field> fields = row.getFields();
            queryResult.append("(");
            boolean isFirst = true;
            for (Field field : fields) {
                if (!isFirst) {
                    queryResult.append(", ");
                }
                queryResult.append(checkCorrectValue(field));
                isFirst = false;
            }
            queryResult.append(")");
            isValuesFirst = false;
        }
        return queryResult.toString();
    }

    private String checkCorrectValue(final Field field) {
        StringBuilder resultString = new StringBuilder();
        switch (field.getTypeField()) {
            case DATE:
                if ("NULL".equals(field.getValue())) {
                    resultString.append("NULL");
                } else {
                    resultString.append("'").append(field.getValue()).append("'");
                }
                break;
            case CHARACTER:
                String buffer = field.getValue();
                resultString.append("'").append(buffer.replaceAll("[']", "\"")).append("'");
                break;
            default:
                resultString.append("'").append(field.getValue()).append("'");
                break;
        }
        return resultString.toString();
    }

    @Override
    public int isExistTable(final String nameBase) {
        int countResult = -1;
        String fullNameBase = nameSchema + "." + nameBase;
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            // language=MySQL
            String sqlQuery = "SHOW TABLES FROM `" + nameSchema + "` like '" + nameBase + "'";
            ResultSet resultSet = connection.getResultQuery(sqlQuery);
            while (resultSet.next()) {
                sqlQuery = "SELECT * FROM " + fullNameBase;
                countResult = connection.getResultQuery(sqlQuery).getMetaData().getColumnCount();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return countResult;
        }
    }

    /**
     * Drop table from MySQL
     *
     * @param nameBase the name dropping table.
     */
    public void dropTable(final String nameBase) {
        String fullNameBase = nameSchema + "." + nameBase;
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            // language=MySQL
            String sqlQuery = "DROP table " + fullNameBase;
            connection.getPrepareStm(sqlQuery).execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Create base and add new data to table.
     *
     * @param dbfBase the base object to add.
     */
    public void createNewTable(final DbfBase dbfBase) {
        String fullNameBase = nameSchema + "." + dbfBase.getNameBase();
        try (ConnectionToMySQL connection = new ConnectionToMySQL()) {
            // language=MySQL
            String sqlQuery = constructSqlQueryCreateTable(dbfBase);
            System.out.println(sqlQuery);
            connection.getPrepareStm(sqlQuery).execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String constructSqlQueryCreateTable(final DbfBase dbfBase) {
        StringBuilder queryResult = new StringBuilder("CREATE TABLE " + nameSchema
                + "." + dbfBase.getNameBase() + " (");
        for (int i = 0; i < dbfBase.getDbfFields().size(); i++) {
            queryResult.append("`")
                    .append(dbfBase.getDbfFields().get(i).getName())
                    .append("`")
                    .append(" ")
                    .append(getTypeMySqlFromDbf(dbfBase.getDbfFields().get(i)));
            if (dbfBase.getDbfFields().size() > i + 1) {
                queryResult.append(", ");
            }
        }

        queryResult.append(")")
                .append(" COLLATE = 'cp1251_general_ci'");
        return queryResult.toString();
    }

    private String getTypeMySqlFromDbf(final DBFField dbfField) {
        String type = "";
        switch (dbfField.getType()) {
            case NUMERIC:
                if (dbfField.getDecimalCount() == 0) {
                    type = "INT";
                } else {
                    type = "FLOAT";
                }
                break;
            case DOUBLE:
                type = "DOUBLE";
                break;
            case LOGICAL:
                type = "BOOL";
                break;
            case CHARACTER:
                type = "VARCHAR(" + dbfField.getLength() + ")";
                break;
            case VARCHAR:
                type = "VARCHAR";
                break;
            case DATE:
                type = "DATE";
                break;
            default:
                type = "NONE";
                break;
        }
        return type;
    }
}
