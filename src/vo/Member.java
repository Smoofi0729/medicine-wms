package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private MemberType memberType;
    private String memberStatus;
    private String approval;
    private String businessName;
    private String businessAddress;
    private String warehouseName;
    private TruckType truckType;
    private String truckNumber;
}

enum MemberType {
    일반회원, 배송기사, 관리자
}

enum TruckType {
    냉장, 냉동, 일반
}
