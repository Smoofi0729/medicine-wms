package config;

import enums.ApprovalStatus;
import enums.TaskAbbreviations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class UtilMethod{

  private static int sequence = 0;
  private static final int MAX_SEQUENCE=9999;

  public static String inputStr(String input) {
    System.out.print(input + " : ");
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      return br.readLine();
    } catch (IOException e) {
      System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
      return inputStr(input); // 재귀 호출을 통해 재입력 요청
    }
  }

  public static int inputInt(String input) {
    System.out.print(input + " : ");
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      return Integer.parseInt(br.readLine());
    } catch (NumberFormatException e) {
      System.out.println("숫자를 입력하세요.");
      return inputInt(input); // 재귀 호출을 통해 재입력 요청
    } catch (IOException e) {
      System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
      return inputInt(input); // 재귀 호출을 통해 재입력 요청
    }
  }

  public static boolean recheckDelete() {
    System.out.println("정말로 삭제하시겠습니까?");
    System.out.println("1. 예 | 2. 아니오");
    switch (inputInt("선택")) {
      case 1:
        return true;
      case 2:
      default:
        return false;
    }
  }

  public static boolean isValidId(ResultSet rs) {
    try {
      if (rs != null && rs.next()) {
        return true; // 유효한 ID
      } else {
        System.out.println("해당 ID는 존재하지 않습니다.");
        return false; // 유효하지 않은 ID
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static String autoCreateId(String abbr,LocalDateTime now) {
    now = LocalDateTime.now();
    sequence = (sequence > MAX_SEQUENCE) ? 1 : sequence;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    String id = new StringBuilder()
        .append(TaskAbbreviations.fromDescription(abbr))
        .append(now.format(formatter))
        .append(String.format("%04d",sequence++))
        .toString();

    return id;
  }

  public static HashMap<Integer, String> selectColumn(String table) {
    HashMap<Integer, String> columns = new HashMap<>();
    int number = 0;
    String query = "select column_name from information_schema.columns where table_name = '" + table + "' order by ordinal_position";
    try (Connection connection = new MysqlDBIO().open();
        PreparedStatement pstmt = connection.prepareStatement(query)) {

      try (ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
          ++number;
          columns.put(number, rs.getString("column_name"));
        }
        return columns;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean catchApprovalDescription(String description) {
    if (description.contains("승인") || description.contains("거절") || description.contains("처리중")) {
      return true;
    }
    return false;
  }
}



