package vo;

import lombok.Data;
import lombok.Setter;

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

    //getter, setter 전부 만들기
    public String getProductLotNo() {
        return productLotNo;
    }
    public void setProductLotNo(String productLotNo) {
        this.productLotNo = productLotNo;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
