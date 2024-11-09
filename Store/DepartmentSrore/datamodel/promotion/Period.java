package DepartmentSrore.datamodel.promotion;

import java.util.Date;

import DepartmentSrore.Constants;

public class Period implements Validator {
    Constants.PromotionType type;
    private Date startDate;
    private Date endDate;

    public Period(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Boolean validate(Date date) { //check the date is in the period or not
        return (startDate.before(date) || startDate.equals(date)) &&
               (endDate.after(date) || endDate.equals(date));
    }
    @Override
    public String getType() {
        return Constants.PromotionType.PERIODIC.name(); // to get the name type of the validator , this used for check matching types in the constructor in promotion types  
    }
    
    
}