package vo;

import lombok.Data;

import java.util.Date;

@Data
public class Revenue {
    private String revenueId;
    private String warehouseId;
    private Date revenueDate;
    private int revenueCharge;
    private String revenueCategory;
    private String note;
}
