package vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class StockTaking {
    private String stockTakingId;
    private String warehouseId;
    private String productId;
    private String productName;
    private int total;
    private String lotNo;
    private String unit;
    private int computerizedStock;
    private int physicalStock;
    private int differenceQuantity;
    private Date stockTakingDate;
    private String note;
}
