package vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Warehouse {
  private String id;
  private String name;
  private String address;
  private String contact;
  private int capacity;
  private Date registerDate;
  private Member manager;
  private String note;

  private Warehouse() {
  }
}
