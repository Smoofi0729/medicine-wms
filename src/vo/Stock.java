package vo;

import java.util.Date;
import lombok.Data;

@Data
public class Stock {
    private String productLotNo;
    private String productName;
    private int total;
    private String productId;
    private String sectionId;
    private String warehouseName;
}
