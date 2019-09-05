package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for row of dbf file.
 * <p>
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 08.04.2019
 */
@Data
public class Row {

    private List<Field> fields = new ArrayList<>();

    /**
     * Add field to row.
     *
     * @param field the field.
     */
    public void addField(final Field field) {
        fields.add(field);
    }
}
