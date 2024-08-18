package interfaces.release;

import interfaces.Services;
import java.sql.ResultSet;

public interface ReleaseInspectionService extends Services {

  void releaseInspectionMenu();

  void readAllReleaseInspection();

  void readByReleaseInspectionId();

  void showStatusMenu();

  void printInfo(ResultSet rs);
}