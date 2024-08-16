import config.ObjectDBIO;
import dao.SectionDao;
import dao.WarehouseDao;
import interfaces.SectionService;
import interfaces.WarehouseService;
import java.sql.Date;
import java.time.LocalDate;
import services.SectionServiceImpl;
import services.WarehouseServiceImpl;
import vo.Warehouse;

public class WMSMain extends ObjectDBIO {

  public static void main(String[] args) {
    WarehouseDao warehouseDao = new WarehouseDao();
    SectionDao sectionDao = new SectionDao();

    SectionService sectionService = new SectionServiceImpl(sectionDao, warehouseDao, null); // 임시로 null을 전달
    WarehouseService warehouseService = new WarehouseServiceImpl(warehouseDao, sectionService);

    // 이제 SectionService에 WarehouseService를 다시 설정
    ((SectionServiceImpl) sectionService).setWarehouseService(warehouseService);

    warehouseService.warehouseMenu();
  }
}
