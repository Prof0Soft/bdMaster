package basedbf.impl;

import basedbf.DbfRead;
import com.linuxense.javadbf.*;
import lombok.NoArgsConstructor;
import model.DbfBase;
import model.Field;
import model.Row;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import setting.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Read database from dbf (FoxPro).
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 05.04.2019
 */
@NoArgsConstructor
public class DbfReadImpl implements DbfRead {

    private Logger logger = Logger.getLogger(DbfReadImpl.class);

    @Override
    public List<Path> getAllBaseLocationFiles(final String pathDatabases) {
        try {
            return Files.walk(Paths.get(pathDatabases))
                    .filter(Files::isRegularFile)
                    .filter(p -> FilenameUtils.getExtension(p.toString()).equalsIgnoreCase(Constant.FILE_EXTENTION))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("Not searched files ::: path > " + pathDatabases + " " + Constant.FILE_EXTENTION);
        return new ArrayList<>();
    }

    @Override
    public DbfBase readDataFromDbf(final Path path) {
        DbfBase dbfBase = new DbfBase();
        File file = new File(path.toString());
        Charset charset = Charset.forName("windows-1251");
        List<Row> rows = new ArrayList<>();
        int countRow = 0;
        try (InputStream inputStream = new FileInputStream(file);
             DBFReader reader = new DBFReader(inputStream, charset)) {

            int countFields = reader.getFieldCount();
            DBFRow dbfRow;
            while ((dbfRow = reader.nextRow()) != null) {
                Row row = new Row();
                for (int i = 0; i < countFields; i++) {
                    DBFField field = reader.getField(i);
                    Field singleField = new Field(field.getName(), field.getType(),
                            getValueOfDbf(dbfRow, field.getType(), i));
                    row.addField(singleField);
                }
                rows.add(row);
                countRow++;
            }
            logger.info("Read dbf file: " + path.getFileName().toString() + ". Read count " + countRow
                    + " Path > " + path.toString());
            dbfBase = new DbfBase(FilenameUtils.removeExtension(path.getFileName().toString()),
                    path.toString(), rows, getAllField(reader));
        } catch (DBFException | IOException e) {
            logger.error(e.getMessage(), e);
        }

        return dbfBase;
    }

    private String getValueOfDbf(final DBFRow dbfRow, final DBFDataType dbfDataType, final int index) {
        String resultString;
        switch (dbfDataType) {
            case DATE:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dbfRow.getDate(index);
                if (date != null) {
                    resultString = dateFormat.format(date);
                } else {
                    resultString = "NULL";
                }
                break;
            case NUMERIC:
                resultString = dbfRow.getString(index);
                if (resultString == null) {
                    resultString = "0";
                }
                break;
            case LOGICAL:
                resultString = dbfRow.getString(index);
                if (resultString == null) {
                    resultString = "0";
                }
                if ("true".equals(resultString)) {
                    resultString = "1";
                } else {
                    resultString = "0";
                }
                break;
            default:
                resultString = dbfRow.getString(index);
                break;
        }
        return resultString;
    }

    private List<DBFField> getAllField(final DBFReader dbfReader) {
        List<DBFField> dbfFields = new ArrayList<>();
        for (int i = 0; i < dbfReader.getFieldCount(); i++) {
            dbfFields.add(dbfReader.getField(i));
        }
        return dbfFields;
    }

}
