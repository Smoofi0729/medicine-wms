package services;

import static config.UtilMethod.inputInt;
import static config.UtilMethod.inputStr;
import static config.UtilMethod.isValidId;

import config.SectionType;
import config.UtilMethod;
import dao.SectionDao;
import dao.WarehouseDao;
import interfaces.SectionService;
import interfaces.WarehouseService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

  private SectionDao sectionDao;
  private WarehouseDao warehouseDao;
  private WarehouseService warehouseService;


  public void sectionMenu() {
    while (true) {
      System.out.println("섹션관리 메뉴");
      System.out.println("1. 등록 | 2. 조회 | 3. 수정 및 삭제 | 4. 상위메뉴");
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
        default -> System.out.println("입력이 잘못되었습니다.");
      }
    }
  }

  private boolean estimateSection(String warehouseId, int available) {
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
    System.out.println("1. 전체조회 | 2. 개별조회 | 3. 타입별조회");
    switch (inputInt("조회방법을 선택해주세요")) {
      case 1 -> readAllSection();
      case 2 -> readBySectionId();
      case 3 -> readSectionByType();
    }
  }

  @Override
  public void readAllSection() {
    printSectionInfo(sectionDao.selectSection());
  }

  @Override
  public void readBySectionId() {
    String id = inputStr("섹션Id");
    printSectionInfo(sectionDao.selectFilterBy("section_id", id));
  }

  @Override
  public void readSectionByType() {
    String type = SectionType.fromDescription(inputStr("섹션종류")).getDescription();
    printSectionInfo(sectionDao.selectFilterBy("section_type", type));
  }

  public void showUpdateMenu() {
    String id = inputStr("수정 및 삭제할 섹션의 id를 입력하세요");
    ResultSet rs = sectionDao.selectFilterBy("section_id", id);
    if (isValidId(sectionDao.selectFilterBy("section_id", id))) {
      printSectionInfo(rs);
      System.out.println("1. 수정 | 2. 삭제");
      switch (inputInt("메뉴선택")) {
        case 1:
          HashMap<String, String> updates = new HashMap<>();
          while (true) {
            String column = inputStr("수정할 항목을 입력해주세요 (없으면 exit 입력)");
            if (column.equals("exit")) {
              break;
            }
            String update = inputStr("수정할 내용을 입력해주세요");
            updates.put(column, update);
          }
          boolean success = sectionDao.updateSection(updates, id);
          if (success) {
            System.out.println("업데이트 성공");
          } else {
            System.out.println("업데이트 실패");
          }
        case 2:
          if (UtilMethod.recheckDelete()) {
            sectionDao.deleteSection(id);
            System.out.println("삭제 성공");
          } else {
            System.out.println("삭제 철회");
          }
          break;
      }
    } else {
      showUpdateMenu();
    }
  }

  @Override
  public void printSectionInfo(ResultSet rs) {
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
