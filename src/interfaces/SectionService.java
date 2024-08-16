package interfaces;

import java.sql.ResultSet;

public interface SectionService {

  void sectionMenu();

  void readAllSection();

  void readBySectionId();

  void readSectionByType();

  void printSectionInfo(ResultSet rs);
}
