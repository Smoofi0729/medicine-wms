package config;

import config.enums.TaskAbbreviations;
import config.enums.SectionType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilMethod {

  private static int sequence = 0;

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

  public static SectionType inputSectionType(String input) {
    System.out.print(input + " : ");
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      return SectionType.fromDescription(br.readLine()); // 입력값을 enum으로 변환
    } catch (IllegalArgumentException e) {
      System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
      return inputSectionType(input); // 재귀 호출을 통해 재입력 요청
    } catch (IOException e) {
      throw new RuntimeException(e);
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    String id = new StringBuilder()
        .append(TaskAbbreviations.fromDescription(abbr))
        .append(now.format(formatter))
        .append(String.format("%04d",sequence++))
        .toString();

    return id;
  }
}



