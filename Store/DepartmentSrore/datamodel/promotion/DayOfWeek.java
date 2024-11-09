package DepartmentSrore.datamodel.promotion;
import java.text.SimpleDateFormat;
import java.util.Date;

import DepartmentSrore.Constants;

public class DayOfWeek implements Validator {
    private String[] daysOfWeek;

    public DayOfWeek(String[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Override    //This function convert the date to day of week and then check if it is matching with the validator or not
    public Boolean validate(Date today) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");

        String dayOfWeek = dateFormat.format(today);

        for (String specifiedDay : daysOfWeek) {
            if (dayOfWeek.equalsIgnoreCase(specifiedDay)) {
                return true;
            }
        }

        return false; 
    }

    @Override
    public String getType() {
        return Constants.PromotionType.DAYOFWEEK.name(); // to get the name type of the validator , this used for check matching types in the constructor in promotion types  
    }
}
