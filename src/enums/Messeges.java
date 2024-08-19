package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public enum Messeges {
  SELECT_HOW("조회할 방법을 선택하세요"),
  DELETE_SUCCESS("삭제 성공"),
  DELETE_CANCEL("삭제 철회"),
  UPDATE_SUCCESS("업데이트 성공"),
  UPDATE_CANCEL("업데이트 실패"),
  UPDATE_HOW("수정할 내용을 입력해주세요"),
  WHICH_COLUMN("수정할 항목을 선택해주세요 (없으면 0 입력)"),
  WHICH_ID("수정 및 삭제할 섹션의 id를 입력하세요"),
  UPDATE_OR_DELETE("1. 수정 | 2. 삭제"),
  WRONG_INPUT("입력이 잘못되었습니다."),
  APPROVAL_STATUS("1. 처리중 | 2. 승인 | 3. 거절"),
  DEVIDER("====================================================================="),
  WH_MENU("창고관리 메뉴"),
  SC_MENU("섹션관리 메뉴"),
  RR_MENU("출고요청관리 메뉴"),
  RI_MENU("출고검수관리 메뉴"),
  RL_MENU("출고관리 메뉴");



  private final String description;


  // 입력된 한글에 해당하는 enum을 반환하는 메서드
  public static Messeges fromDescription(String description) {
    for (Messeges type : Messeges.values()) {
      if (type.description.equals(description)) {
        return type;
      }
    }
    throw new IllegalArgumentException("해당 설명에 해당하는 Messege가 없습니다: " + description);
  }

  @Override
  public String toString() {
    return description;
  }

  public static void printMessage(Messeges msg) {
    System.out.println(msg.getDescription());
  }

  public String getDescription() {
    return description;
  }
}
