package dao;

import lombok.Data;

@Data
public class Product {
  private String productId;
  private String productName;
  private int quantity;
  private int price;
  private String expirationDate;
}
