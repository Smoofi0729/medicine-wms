package interfaces.warehouse;

import interfaces.Services;
import java.sql.ResultSet;

public interface SectionService extends Services {

  void sectionMenu();

  boolean estimateSection(String warehouseId, int available);

  void readAllSection();

  void readBySectionId();

  void readSectionByType();

  void printInfo(ResultSet rs);
}
