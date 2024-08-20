package enums;

public enum DeliveryStatus {
    ON_DELIVERY("배송중"),
    PREPARING("배송준비중"),
    DELIVERED("배송완료");

    private final String description;

    // 생성자
    DeliveryStatus(String description) {
      this.description = description;
    }

    // 한글 설명 반환
    public String getDescription() {
      return description;
    }

    // 입력된 한글에 해당하는 enum을 반환하는 메서드
    public static DeliveryStatus fromDescription(String description) {
      for (DeliveryStatus type : DeliveryStatus.values()) {
        if (type.description.equals(description)) {
          return type;
        }
      }
      throw new IllegalArgumentException("해당 설명에 해당하는 DeliveryStatus가 없습니다: " + description);
    }
  }
