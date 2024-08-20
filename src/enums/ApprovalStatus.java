package enums;

public enum ApprovalStatus {
    승인("승인"),
  거절("거절"),
  처리중("처리중");

    private final String description;

    // 생성자
    ApprovalStatus(String description) {
      this.description = description;
    }

    // 한글 설명 반환
    public String getDescription() {
      return description;
    }

    // 입력된 한글에 해당하는 enum을 반환하는 메서드
    public static ApprovalStatus fromDescription(String description) {
      for (ApprovalStatus type : ApprovalStatus.values()) {
        if (type.description.equals(description)) {
          return type;
        }
      }
      throw new IllegalArgumentException("해당 설명에 해당하는 ApprovalStatus가 없습니다: " + description);
    }
  }
