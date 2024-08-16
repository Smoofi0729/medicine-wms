package vo;

import lombok.Data;

import java.util.Date;

@Data
public class Revenue {
    private int revenueId;
    private String revenueHistory;
    private Date revenueDate;
    private String revenueCharge;
    private String note;
}
