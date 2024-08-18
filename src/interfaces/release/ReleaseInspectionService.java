package interfaces.release;

import interfaces.CrudService;
import java.sql.ResultSet;

public interface ReleaseInspectionService {

  void releaseInspectionMenu();

  void readAllReleaseInspection();

  void readByReleaseInspectionId();

  void showStatusMenu();
}