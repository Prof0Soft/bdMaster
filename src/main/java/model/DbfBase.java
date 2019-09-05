package model;

import com.linuxense.javadbf.DBFField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dbf base with all rows and data.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DbfBase {
    private String nameBase;
    private String pathToFile;
    private List<Row> rows;
    private List<DBFField> dbfFields;
}
