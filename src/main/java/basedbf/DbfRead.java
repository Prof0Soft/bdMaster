package basedbf;

import model.DbfBase;

import java.nio.file.Path;
import java.util.List;

/**
 * Import database from dbf (FoxPro) to MySQL.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 05.04.2019
 */
public interface DbfRead {

    /**
     * Read data from dbf files.
     *
     * @param path the path to file.
     * @return list data of dbf's rows.
     */
    DbfBase readDataFromDbf(Path path);

    /**
     * Get path of all files with need extension.
     *
     * @param pathDatabases the path to database location.
     * @return list of path with files.
     */
    List<Path> getAllBaseLocationFiles(String pathDatabases);
}
