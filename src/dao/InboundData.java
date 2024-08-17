package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InboundData {
  private String requestId;
  private String request_date; // 나중에 Membertype으로 변경
  private String memberId;
}
