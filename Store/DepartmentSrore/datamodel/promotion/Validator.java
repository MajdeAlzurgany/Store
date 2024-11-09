package DepartmentSrore.datamodel.promotion;

import java.util.Date;

public interface Validator {
    Boolean validate(Date date);
    String getType();
}