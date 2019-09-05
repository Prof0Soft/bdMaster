package model;

import com.linuxense.javadbf.DBFDataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for field of dbf file.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Field {
    private String nameField;
    private DBFDataType typeField;
    private String value;
}
