package basesql;

import model.DbfBase;

import java.util.List;

/**
 * Write data to MySQL server.
 * <p>
 * Project: importbasefoxpro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
public interface SqlWrite {
    /**
     * Write data to SQL server
     *
     * @param dbfBases the list of bases data.
     */
    void writeData(DbfBase dbfBases);

    /**
     * Check existed base.
     *
     * @param nameBase the name checking base.
     * @return count of existed fields. If table not exist return -1, otherwise return 0 or positive value.
     */
    int isExistTable(String nameBase);
}
