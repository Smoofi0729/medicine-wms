package vo;

import java.util.Date;
import lombok.Data;

@Data
public class Stock {
  private String lotNo;
  private String name;
  private String unit;
  private int total;
  private Date expiration_period;
}
