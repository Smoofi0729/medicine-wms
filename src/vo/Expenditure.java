package vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Expenditure {
    private String expenditureId;
    private String expenditureHistory;
    private Date expenditureDate;
    private int expenditureCharge;
    private String note;
}
