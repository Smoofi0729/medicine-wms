package vo;

import config.SectionType;
import lombok.Data;

@Data
public class Section {
  private String sectionId;
  private Warehouse warehouseId;
  private int capaWidth;
  private int capaLength;
  private int capaHeight;
  private int totalCapa;
  private SectionType type;
  private String note;
}
