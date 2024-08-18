package interfaces.warehouse;

import interfaces.CrudService;
import java.sql.ResultSet;

public interface SectionService {

  void sectionMenu();

  boolean estimateSection(String warehouseId, int available);

  void readAllSection();

  void readBySectionId();

  void readSectionByType();
}
