package interfaces.warehouse;

import java.io.IOException;
import java.sql.SQLException;

public interface SectionService {

  void sectionMenu(String memberId) throws SQLException, IOException;

  boolean estimateSection(String warehouseId, int available);

  void readAllSection();

  void readBySectionId();

  void readSectionByType();
}
