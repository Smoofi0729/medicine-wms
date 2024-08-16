package config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public enum Messeges {
  DELETE_SUCCESS("삭제 성공"),
  DELETE_CANCEL("삭제 철회"),
  UPDATE_SUCCESS("업데이트 성공"),
  UPDATE_CANCEL("업데이트 실패"),
  UPDATE_DATA("수정할 내용을 입력해주세요"),
  WHICH_COLUMN("수정할 항목을 입력해주세요 (없으면 exit 입력)"),
  WHICH_ID("수정 및 삭제할 섹션의 id를 입력하세요"),
  UPDATE_MENU("1. 수정 | 2. 삭제"),
  WRONG_INPUT("입력이 잘못되었습니다."),
  SECTION_MENU("섹션관리 메뉴"),
  CRUD_MENU("1. 등록 | 2. 조회 | 3. 수정 및 삭제 ");

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
}
