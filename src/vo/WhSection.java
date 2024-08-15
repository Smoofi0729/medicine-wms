package vo;

import config.SectionType;
import lombok.Data;

@Data
public class WhSection {
  private String sectionId;
  private Warehouse warehouseId;
  private int capaWidth;
  private int capaLength;
  private int capaHeight;
  private SectionType type;
  private String reference;
}
