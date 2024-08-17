package vo;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Warehouse {
  private long id;
  private String name;
  private String location;
  private String contact;
  private int capacity;
  private String type;
//  private Client manager;

  private static ArrayList<Warehouse> warehouseList = new ArrayList<>();

  private Warehouse() {
  }

  public static ArrayList<Warehouse> getInstance() {
    return warehouseList;
  }
}
