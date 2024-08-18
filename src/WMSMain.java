import dao.ObjectDBIO;
import interfaces.warehouse.SectionService;
import interfaces.warehouse.WarehouseService;
import services.warehouse.SectionServiceImpl;
import services.warehouse.WarehouseServiceImpl;
import vo.release.ReleaseManagerMenu;

public class WMSMain {
  public static void main(String[] args) {

//    WarehouseService warehouseService = new WarehouseServiceImpl();
//
//    warehouseService.warehouseMenu();

    ReleaseManagerMenu release = new ReleaseManagerMenu();
    release.releaseMenu();
  }
}
