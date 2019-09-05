package convert.impl;

import basedbf.DbfRead;
import basedbf.impl.DbfReadImpl;
import basesql.mysql.MySqlWrite;
import convert.ConvertBase;
import model.DbfBase;
import org.apache.log4j.Logger;
import setting.Constant;

import java.nio.file.Path;

/**
 * Classs for work with database.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 05.04.2019
 */
public class ConvertBaseImpl implements ConvertBase {

    private Logger logger = Logger.getLogger(ConvertBase.class);

    @Override
    public void start() {
        logger.info("Start read data from dbf.");

        MySqlWrite mySqlWrite = new MySqlWrite();
        DbfRead dbfRead = new DbfReadImpl();
        int count = 0;
        for (Path path : dbfRead.getAllBaseLocationFiles(Constant.PATH_TO_DBF)) {
            DbfBase dbfBase = dbfRead.readDataFromDbf(path);
            if (dbfBase.getNameBase() != null) {
                mySqlWrite.writeData(dbfBase);
            }
            count++;
        }

//        dbfBases = dbfRead.readDataFromDbf();

        logger.info("End read data from dbf. Read files - " + count);

    }


}
