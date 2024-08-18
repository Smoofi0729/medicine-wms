package services.warehouse;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.selectColumn;

import config.UtilMethod;
import dao.warehouse.WarehouseDao;
import interfaces.warehouse.SectionService;
import interfaces.warehouse.WarehouseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.Data;

@Data
public class WarehouseServiceImpl implements WarehouseService {

  private String table = "warehouse";
  private WarehouseDao warehouseDao;
  private SectionService sectionService;

  public WarehouseServiceImpl() {
    this.warehouseDao = new WarehouseDao();
    this.sectionService = new SectionServiceImpl(this);
  }

  @Override
  public void warehouseMenu() {
    while (true) {
      System.out.println("=====================================================================");
      System.out.println("창고관리 메뉴");
      System.out.println("=====================================================================");

      System.out.println("1. 창고등록 | 2. 창고조회 | 3. 창고수정 및 삭제 | 4. 섹션관리 | 5. 상위메뉴로");
      switch (inputInt("메뉴선택")) {
        case 1 -> warehouseDao.registerWh();
        case 2 -> showSelectMenu();
        case 3 -> showUpdateMenu();
        case 4 -> {
          sectionService.sectionMenu();
          return;
        }
        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 지역별조회 | 4. 현재 가용량조회 | 5. 상위메뉴로");
    switch (inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllWh();
      case 2 -> readByWhId();
      case 3 -> readByWhLocation();
      case 4 -> System.out.println("현재가용량 : " + warehouseDao.checkCapacity(inputStr("창고ID")));
    }
  }

  public void showUpdateMenu() {
    String id = inputStr("수정 및 삭제할 창고의 id를 입력하세요");
    ResultSet rs = warehouseDao.selectFilterBy("warehouse_id", id);
    if (isValidId(warehouseDao.selectFilterBy("warehouse_id", id))) {
      printInfo(rs);
      System.out.println("1. 수정 | 2. 삭제");
      switch (inputInt("메뉴선택")) {
        case 1:

          HashMap<String, String> updates = new HashMap<>();
          System.out.println(("수정할 항목을 선택해주세요 (없으면 0 입력)"));
          System.out.println("1.창고ID 2.창고이름	3.창고주소 4.창고연락처 5.창고수용량 6.현재가용량 7.등록날짜 8.관리자ID	9.비고");
          while (true) {
            int choice = inputInt("수정할 항목");
            String column = selectColumn(table).get(choice);
            if (choice==0) {
              break;
            }
            String update = inputStr("수정할 내용을 입력해주세요");
            updates.put(column, update);
          }
          boolean success = warehouseDao.updateWh(updates, id);
          if (success) {
            System.out.println("업데이트 성공");
          } else {
            System.out.println("업데이트 실패");
          }
          break;
        case 2:
          if (UtilMethod.recheckDelete()) {
            warehouseDao.deleteWh(id);
            System.out.println("삭제 성공");
          } else {
            System.out.println("삭제 철회");
          }
      }
    }
  }

  @Override
  public void readAllWh() {
    printInfo(warehouseDao.selectWh());
  }

  @Override
  public void readByWhLocation() {
    String address = inputStr("창고소재지");
    printInfo(warehouseDao.selectFilterBy("warehouse_address", address));
  }

  @Override
  public void readByWhId() {
    String id = inputStr("창고ID");
    printInfo(warehouseDao.selectFilterBy("warehouse_id", id));
  }

  public void printInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append(
        "창고ID\t\t\t창고이름\t\t\t창고주소\t\t\t창고연락처\t\t\t창고수용량\t\t\t현재가용량\t\t\t등록날짜\t\t\t관리자ID\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(warehouseDao.getRs().getString("warehouse_id")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_name")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_address")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_contact")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_capacity")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_available")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_regi_date")).append("\t\t");
        result.append(warehouseDao.getRs().getString("member_id")).append("\t\t");
        result.append(warehouseDao.getRs().getString("warehouse_note")).append("\n");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      warehouseDao.close(warehouseDao.getPstmt());
      warehouseDao.close(warehouseDao.getConnection());
    }
    System.out.println(result);
  }
}
