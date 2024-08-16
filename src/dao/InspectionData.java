package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InspectionData {
  private String request_id;
  private String inspection_date;
  private String inspector_id;
  private String inspection_result;

}
