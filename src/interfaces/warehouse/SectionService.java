package interfaces.warehouse;

public interface SectionService {

  void sectionMenu();

  boolean estimateSection(String warehouseId, int available);

  void readAllSection();

  void readBySectionId();

  void readSectionByType();
}
