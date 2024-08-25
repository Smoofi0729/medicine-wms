package enums;

public enum TaskAbbreviations {
  RR("출고요청"),
  RI("출고검수"),
  RL("출고"),
  DP("배차등록"),
  WB("운송등록");

  private final String description;

  // 생성자
  TaskAbbreviations(String description) {
    this.description = description;
  }

  // 한글 설명 반환
  public String getDescription() {
    return description;
  }

  // 입력된 한글에 해당하는 enum을 반환하는 메서드
  public static TaskAbbreviations fromDescription(String description) {
    for (TaskAbbreviations abbr : TaskAbbreviations.values()) {
      if (abbr.description.equals(description)) {
        return abbr;
      }
    }
    // description에 해당하는 ENUM값을 찾지 못한경우
    throw new IllegalArgumentException("해당하는 데이터가 없습니다: " + description);
  }
}
