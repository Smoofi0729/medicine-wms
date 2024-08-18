package vo.warehouse;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import vo.Member;

@Data
@AllArgsConstructor
public class Warehouse {
  private String id;
  private String name;
  private String address;
  private String contact;
  private int capacity;
  private int available;
  private Date registerDate;
  private Member manager;
  private String note;

  private Warehouse() {
  }
}
