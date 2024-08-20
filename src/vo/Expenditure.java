package vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Expenditure {
    private String expenditureId;
    private String warehouseId;
    private Date expenditureDate;
    private int expenditureCharge;
    private String expenditureCategory;
    private String note;

    @Override
    public String toString() {
        return expenditureId + "\t" + expenditureDate + "\t" + expenditureCharge + "\t" + warehouseId + "\t" + expenditureCategory;
    }
}
