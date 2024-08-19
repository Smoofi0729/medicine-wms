package enums;

public enum UserMessege {

    //공통
    MENU_CHOICE("메뉴 선택 : "),
    ID("아이디를 입력해주세요."),
    PASSWORD("비밀번호를 입력해주세요."),
    NEW_PASSWORD("새로운 비밀번호를 입력해주세요."),
    CONTACT("전화번호를 입력해주세요."),
    NOT_MEMBER("일치하는 정보를 찾을 수 없습니다"),
    PW_CHECK("비밀번호를 다시 한번 입력해주세요."),
    PW_ERROR("비밀번호가 일치하지 않습니다."),

    // 시작메뉴
    START_WELCOME_MESSEGE("SMOOFI에 오신 것을 환영합니다"),
    START_MAIN_MENU("\n메인 메뉴 : 1.로그인 | 2.회원가입 | 3.아이디 찾기 | 4.비밀번호 찾기"),


    //회원가입
    SIGN_UP("회원가입 메뉴입니다."),
    SIGN_UP_Name("이름을 입력해주세요."),
    SIGN_UP_ID_ALREADY("이미 존재하는 ID입니다."),
    SIGN_UP_EMAIL("E-MAIL을 입력해 주세요."),
    SIGN_UP_ADDRESS("주소를 입력해주세요"),
    SIGN_UP_MEMBER_TYPE("회원종류 : 1. 일반회원 | 2. 배송기사 | 3. 관리자"),
    SIGN_UP_BUSINESS_NAME("사업장이름을 입력해주세요."),
    SIGN_UP_BUSINESS_NUMER("사업자 번호를 입력해주세요."),
    SIGN_UP_WAREHOUSE_NAME("관리창고 번호를 입력해주세요"),
    SIGN_UP_TRUCK_FUNCTION("1. 냉장 | 2. 냉동 | 3. 일반"),
    SIGN_UP_TRUCK_NUMBER("차량 번호를 입력해 주세요."),

    //로그인
    LOGIN("로그인 화면입니다."),
    LOGIN_ERROR("회원정보를 찾을 수 없습니다."),

    //메뉴화면(접속)
    MENU_WELCOME("님 어서오세요."),//앞에 MEMBER NAME
    MENU_ADMIN_MANAGER("총관리자 메뉴입니다."),
    MENU_MANAGER("창고관리자 메뉴입니다."),
    MENU_DELIVERY_DRIVER("배송기사 메뉴입니다"),
    MENU_WATING_FOR_APPROVAL("승인대기중입니다. 총관리자에게 문의하세요."), //+총관리자 이름 + 전화번호

    //ID,PW 찾기
    FIND_ID("아이디 찾기 입니다"),
    FIND_ID_RETURN("사용자 ID : "), //+ ID
    FIND_PW("비밀번호 찾기 입니다"),
    FIND_PW_EMAIL("입력하신 이메일을 입력해주세요"),
    FIND_PW_EMAIL_CODE("입력하신 이메일로 인증번호를 발송했습니다."),
    FIND_PW_EMAIL_CODE_CHECK("인증번호를 입력해주세요."),
    FIND_PW_EMAIL_CODE_ERROR("인증번호가 일치하지 않습니다."),
    FIND_PW_RESET_PASSWORD("새로운 비밀번호를 생성해주세요."),


    //일반

    //회원 접근 권한
    ACCESS_DENIED("접근 권한이 없습니다."),


    //로그아웃
    LOGOUT("로그아웃 합니다.");


    private final String message;

    UserMessege(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void println() {
        System.out.println(message);
    }

    public void print() {
        System.out.print(message);
    }
}
