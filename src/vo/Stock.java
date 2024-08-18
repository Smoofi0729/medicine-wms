package vo;

import java.util.Date;
import lombok.Data;

@Data
public class Stock {
    private String productLotNo;
    private String productName;
    private String unit;
    private int total;
    private int expirationDate;
    private String productId;
    private String warehouseId;
    private String sectionId;
}
