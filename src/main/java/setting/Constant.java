package setting;

import utils.ReadProperties;

/**
 * For constats.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
public abstract class Constant {
    /**
     * File extension.
     */
    public static final String FILE_EXTENTION = "dbf";

    /**
     * The driver name.
     */
    public static final String DB_DRIVER = ReadProperties.getProperties("driver");
    /**
     * The database url
     */
    public static final String DB_URL = ReadProperties.getProperties("url");
    /**
     * Login for database
     */
    public static final String DB_LOGIN = ReadProperties.getProperties("login");
    /**
     * Password for connect to database.
     */
    public static final String DB_PASSWORD = ReadProperties.getProperties("password");
    /**
     * The name schema.
     */
    public static final String DB_NAMESCHEMA = ReadProperties.getProperties("name-schema");
    /**
     * The path to DBF catalog.
     */
    public static final String PATH_TO_DBF = ReadProperties.getProperties("pathToDb");


}
