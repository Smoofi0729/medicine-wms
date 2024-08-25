package enums;

public enum SectionType {
    REFRIGERATED("냉장"),
    FROZEN("냉동"),
    ROOM_TEMPERATURE("실온");

    private final String description;

    // 생성자
    SectionType(String description) {
      this.description = description;
    }

    // 한글 설명 반환
    public String getDescription() {
      return description;
    }

    // 입력된 한글에 해당하는 enum을 반환하는 메서드
    public static SectionType fromDescription(String description) {
      for (SectionType type : SectionType.values()) {
        if (type.description.equals(description)) {
          return type;
        }
      }
      throw new IllegalArgumentException("해당하는 데이터가 없습니다: " + description);
    }
  }
