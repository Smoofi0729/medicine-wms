package services.warehouse;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;
import static config.UtilMethod.selectColumn;
import static config.enums.Messeges.*;

import enums.SectionType;
import config.UtilMethod;
import dao.warehouse.SectionDao;
import dao.warehouse.WarehouseDao;
import interfaces.warehouse.SectionService;
import interfaces.warehouse.WarehouseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import lombok.Data;

@Data
public class SectionServiceImpl implements SectionService {

  private String table = "section";

  private SectionDao sectionDao;
  private WarehouseDao warehouseDao;
  private WarehouseService warehouseService;

  public SectionServiceImpl() {
    this.sectionDao = new SectionDao();
    this.warehouseDao = new WarehouseDao();
  }

  public SectionServiceImpl(WarehouseService warehouseService) {
    this();
    this.warehouseService = warehouseService;
  }

  @Override
  public void sectionMenu() {
    while (true) {
      printMessage(SC_MENU);
      System.out.println("1. 등록 | 2. 조회 | 3. 수정 및 삭제 | 4. 상위메뉴로");
      switch (inputInt("메뉴선택")) {
        case 1 -> {
          String warehouseId = inputStr("등록할 창고ID");
          System.out.println("현재 가용량 : " + warehouseDao.checkCapacity(warehouseId));
          int available = warehouseDao.checkCapacity(warehouseId);
          if (available > 0) {
            if (estimateSection(warehouseId, available)) {
              System.out.println("등록성공");
            }
          }
        }
        case 2 -> showSelectMenu();
        case 3 -> showUpdateMenu();
        case 4 -> warehouseService.warehouseMenu();
        default -> printMessage(WRONG_INPUT);
      }
    }
  }

  @Override
  public boolean estimateSection(String warehouseId, int available) {
    int width = inputInt("섹션 가로 길이");
    int length = inputInt("섹션 세로 길이");
    int height = inputInt("섹션 높이");

    int sectionCapacity = width * length * height;

    if (sectionCapacity <= available) {
      System.out.println("섹션 수용 가능량: " + sectionCapacity + " (등록 가능)");
      sectionDao.registerSection(warehouseId, width, length, height, sectionCapacity);
      return true;
    } else {
      System.out.println("창고의 가용 용량을 초과합니다. 섹션을 등록할 수 없습니다.");
      return false;
    }
  }

  public void showSelectMenu() {
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 타입별조회 | 4. 상위메뉴");
    switch (inputInt(SELECT_HOW.getDescription())) {
      case 1 -> readAllSection();
      case 2 -> readBySectionId();
      case 3 -> readSectionByType();
      case 4 -> sectionMenu();
    }
  }

  @Override
  public void readAllSection() {
    printInfo(sectionDao.selectSection());
  }

  @Override
  public void readBySectionId() {
    String id = inputStr("섹션Id");
    printInfo(sectionDao.selectFilterBy("section_id", id));
  }

  @Override
  public void readSectionByType() {
    String type = SectionType.fromDescription(inputStr("섹션종류")).getDescription();
    printInfo(sectionDao.selectFilterBy("section_type", type));
  }

  public void showUpdateMenu() {
    String id = inputStr(WHICH_ID.getDescription());
    ResultSet rs = sectionDao.selectFilterBy("section_id", id);
    if (isValidId(sectionDao.selectFilterBy("section_id", id))) {
      printInfo(rs);
      printMessage(UPDATE_OR_DELETE);
      switch (inputInt("메뉴선택")) {
        case 1:

          HashMap<String, String> updates = new HashMap<>();
          printMessage(WHICH_COLUMN);
          System.out.println("1.섹션ID 2.창고ID 3.가로길이 4.세로길이 5.높이 6.섹션종류");
          while (true) {
            int choice = inputInt("수정할 항목");
            String column = selectColumn(table).get(choice);
            if (choice == 0) {
              break;
            }
            String update = inputStr(UPDATE_HOW.getDescription());
            updates.put(column, update);
          }
          boolean success = sectionDao.updateSection(updates, id);
          if (success) {
            printMessage(UPDATE_SUCCESS);
          } else {
            printMessage(UPDATE_CANCEL);
          }
        case 2:
          if (UtilMethod.recheckDelete()) {
            sectionDao.deleteSection(id);
            printMessage(DELETE_SUCCESS);
          } else {
            printMessage(DELETE_CANCEL);
          }
          break;
      }
    } else {
      showUpdateMenu();
    }
  }

  public void printInfo(ResultSet rs) {
    StringBuilder result = new StringBuilder();
    result.append("섹션ID\t\t\t창고ID\t\t\t가로길이\t\t\t세로길이\t\t\t높이\t\t\t섹션종류\t\t\t수용가능량\t\t\t비고\n");
    try {
      while (rs.next()) {
        result.append(sectionDao.getRs().getString("section_id")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("warehouse_id")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_width")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_length")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_height")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_type")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_capacity")).append("\t\t\t");
        result.append(sectionDao.getRs().getString("section_note")).append("\n");
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
      sectionDao.close(sectionDao.getPstmt());
      sectionDao.close(sectionDao.getConnection());
    }
    System.out.println(result);
  }
}
