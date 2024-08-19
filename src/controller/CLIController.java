package controller;

import config.ConnectionFactory;
import config.SystemIn;
import dao.memberServices;
import config.LOGO;
import enums.UserMessege;
import static config.UtilMethod.inputInt;

import dao.AdminFunctions;
import interfaces.ExpenditureService;
import interfaces.RevenueService;
import interfaces.StockPrintService;
import interfaces.StockTakingService;
import interfaces.release.ReleaseRequestService;
import interfaces.release.ReleaseService;
import interfaces.warehouse.WarehouseService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import services.FinanceServiceImpl;
import services.StockPrintServiceImpl;
import services.StockTakingServiceImpl;
import services.release.ReleaseRequestServiceImpl;
import services.release.ReleaseServiceImpl;
import services.warehouse.WarehouseServiceImpl;
import vo.Expenditure;
import vo.Stock;
import vo.StockTaking;

public class CLIController {
    private Connection connection; 

    public void BasicMenu() throws Exception { 

        boolean validinput = false;

        LOGO.logo(); // 로고
        UserMessege.START_WELCOME_MESSEGE.println(); // 인사말

        while (!validinput) {
            UserMessege.START_MAIN_MENU.println();
            UserMessege.MENU_CHOICE.print();

            switch (SystemIn.SystemInInt()) {

                // 로그인
                case 1:
                    System.out.println("로그인");
                    memberServices.login();
                    validinput = true;
                    break;

                // 회원가입
                case 2:
                    System.out.println("회원가입");
                    memberServices.signInMember();
                    validinput = true;
                    break;

                // ID 찾기
                case 3:
                    System.out.println("ID 찾기");
                    validinput = true;
                    memberServices.findId();
                    break;

                // 비밀번호 찾기
                case 4:
                    System.out.println("비밀번호 찾기");
                    validinput = true;
                    memberServices.resetPassword();
                    break;

                default:
                    System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
            }
        }
    }

public void MemberMainMenu(String memberId) throws IOException {
                    boolean exit = false;
                    CLIController cliController = new CLIController();
                    //WMS inboundservice = new WMS();

                    while (!exit) {
                        System.out.println(memberId + " 님 환영합니다.");
                        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 | 7.로그아웃");
                        int select = SystemIn.SystemInInt();
                        switch (select) {
                            case 1:
                                System.out.println("입고");
                                //inboundservice.serviceInbound();
                    break;
                case 2:
                    System.out.println("나의 출고 메뉴 : 1.출고요청 | 2.출고현황");
                    switch (inputInt("메뉴선택")) {
                        case 1 :
                            ReleaseRequestService releaseRequestService = new ReleaseRequestServiceImpl();
                            releaseRequestService.releaseRequestMenuForMall();
                            break;
                        case 2:
                            ReleaseService releaseService = new ReleaseServiceImpl();
                            releaseService.showReleasesForMall();
                    }
                    break;
                case 3:
                    System.out.println("재고 조회");
                    try {
                        cliController.stockMenu();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    System.out.println("재무");
                    try {
                        cliController.financeMenu();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    System.out.println("고객센터");
                    break;
                case 6:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 7:
                    System.out.println("로그아웃");
                    exit = true;
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void DeliveryMainMenu(String memberId) throws IOException {
        boolean exit = false;
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.배송 | 2.고객센터 | 3.회원정보 | 4.로그아웃");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    System.out.println("배송");
                    break;
                case 2:
                    System.out.println("고객센터");
                    break;
                case 3:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 4:
                    System.out.println("로그아웃");
                    exit = true;
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void adminMainMenu(String memberId) throws IOException, SQLException { 
        boolean exit = false;
        //WMS inboundService = new WMS();
        CLIController cliController = new CLIController();
        
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 관리 | 7.로그아웃");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    System.out.println("입고");
                    //inboundService.serviceInbound();
                    break;
                case 2:
                    System.out.println(" 고");
                    ReleaseService releaseService = new ReleaseServiceImpl();
                    releaseService.showReleaseMenuForManager();
                    break;
                case 3:
                    try {
                        cliController.stockMenu();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    System.out.println("재무");
                    try {
                        cliController.financeMenu();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    WarehouseService warehouseService = new WarehouseServiceImpl();
                    warehouseService.warehouseMenu();
                    break;
                case 6:
                    manageMemberInfo(memberId);
                    break;
                case 7:
                    System.out.println("회원 " + memberId + " 님이 로그아웃되었습니다.");
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    private void manageMemberInfo(String memberId) throws IOException, SQLException { 
        boolean exit = false;
        while (!exit) {
            System.out.println("1. 개인 정보 관리 | 2. 회원 정보 관리 | 3. 뒤로가기");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    memberServices.viewMemberInfo(memberId);
                    break;
                case 2:
                    manageAllMembers();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    private void manageAllMembers() throws SQLException, IOException {
        boolean exit = false;
        while (!exit) {
            System.out.println("회원 정보 관리 : 1.모든 사용자 조회 | 2.회원 타입별 조회 | 3.회원 ID로 조회 | 4.권한 승인 요청 조회 | 5.권한 승인 |  6.삭제 | 7.뒤로가기");
            int select = SystemIn.SystemInInt();
            AdminFunctions adminFunctions = new AdminFunctions();
            switch (select) {
                case 1:
                    // 모든 사용자 조회
                    adminFunctions.searchAllMembers();
                    break;
                case 2:
                    // 회원 타입별 조회
                    System.out.println("조회할 회원 타입을 입력하세요 (일반회원, 배송기사, 관리자):");
                    String memberType = SystemIn.SystemInString();
                    adminFunctions.searchMembersByType(memberType);
                    break;
                case 3:
                    // 회원 ID로 조회
                    System.out.println("조회할 회원 ID를 입력하세요:");
                    String memberId = SystemIn.SystemInString();
                    adminFunctions.searchMemberById(memberId);
                    break;
                case 4:
                    // 권한 승인 요청 조회
                    adminFunctions.searchMembersWithPendingApproval();
                    break;
                case 5:
                    // 권한 승인
                    System.out.println("승인할 회원 ID를 입력하세요:");
                    memberId = SystemIn.SystemInString();
                    adminFunctions.approveMember(memberId);
                    break;

                case 6:
                    // 회원 삭제 승인
                    System.out.println("비활성화할 회원 ID를 입력하세요:");
                    memberId = SystemIn.SystemInString();
                    adminFunctions.finalizeMemberDeletion(memberId);
                    break;
                case 7:
                    // 뒤로가기
                    exit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public void showMenu(String memberId) throws SQLException, IOException { 
        try {
            connection = ConnectionFactory.getInstance().open();
            String sql = "SELECT member_type FROM member WHERE member_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, memberId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String memberType = rs.getString("member_type");
                        switch (memberType) {
                            case "일반회원":
                                MemberMainMenu(memberId);
                                break;
                            case "배송기사":
                                DeliveryMainMenu(memberId);
                                break;
                            case "관리자":
                                adminMainMenu(memberId);
                                break;
                            default:
                                System.out.println("회원정보를 찾을 수 없습니다.");
                                break;
                        }
                    } else {
                        System.out.println("회원정보를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void stockMenu() throws SQLException, IOException, ParseException {
        System.out.println("1. 재고 전체 조회 2. 재고 구역 별 조회 3. 재고 실사 조회(수정, 삭제) 4. 재고 실사 등록");
        System.out.print("->");
        int num = SystemIn.SystemInInt();
        StockPrintService sps = new StockPrintServiceImpl(ConnectionFactory.getInstance().open());
        StockTakingService sts = new StockTakingServiceImpl(ConnectionFactory.getInstance().open());
        switch (num) {
            // 재고 조회
            case 1:
                System.out.println("전체 재고를 조회합니다.");
                sps.printAllStock();
                break;
            //재고 실사
            case 2:
                System.out.println("재고 구역별 조회. 구역 아이디를 입력하세요.");
                System.out.print("->");

                String sectionId = SystemIn.SystemInString();
                Stock stock = new Stock();
                stock.setSectionId(sectionId);

                sps.printBySectionStock(stock);
                break;
            case 3:
                System.out.println("재고 실사 조회. 재고 실사 아이디를 입력해주세요.");
                System.out.print("->");
                String stockTakingId = SystemIn.SystemInString();
                StockTaking s = new StockTaking();
                s.setStockTakingId(stockTakingId);
                s = sts.printStockTakingList(s);

                System.out.println("1. 재고 실사 수정 2. 재고 실사 삭제");
                int choice = SystemIn.SystemInInt();
                switch (choice) {
                    case 1:
                        System.out.println("재고 실사 수정. 재고 실사 정보를 수정해주세요.");
                        // 재고 실사 수정 값 입력받기
                        System.out.println("전산 상 재고");
                        s.setComputerizedStock(SystemIn.SystemInInt());
                        System.out.println("실제 재고");
                        s.setPhysicalStock(SystemIn.SystemInInt());
                        System.out.println("차이 수량");
                        s.setDifferenceQuantity(SystemIn.SystemInInt());
                        System.out.println("비고");
                        s.setNote(SystemIn.SystemInString());

                        sts.UpdateStockTakingList(s);
                        break;
                    case 2:
                        System.out.println("재고 실사 삭제. 해당 재고 실사 정보를 삭제할까요? 예, 아니오");
                        String answer = SystemIn.SystemInString();
                        if (answer.equals("예"))
                            sts.deleteStockTakingList(s);
                        else
                            System.out.println("삭제를 취소합니다.");
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
                break;
            case 4:
                System.out.println("재고 실사 등록. 재고 실사 정보를 등록해주세요.");
                StockTaking stockTaking = new StockTaking();
                System.out.println("재고 실사 아이디");
                stockTaking.setStockTakingId(SystemIn.SystemInString());
                System.out.println("창고 아이디");
                stockTaking.setWarehouseId(SystemIn.SystemInString());
                System.out.println("제품 아이디");
                stockTaking.setProductId(SystemIn.SystemInString());
                System.out.println("제품 이름");
                stockTaking.setProductName(SystemIn.SystemInString());
                System.out.println("로트 번호");
                stockTaking.setLotNo(SystemIn.SystemInString());
                System.out.println("전산 상 재고");
                stockTaking.setComputerizedStock(SystemIn.SystemInInt());
                System.out.println("실제 재고");
                stockTaking.setPhysicalStock(SystemIn.SystemInInt());
                System.out.println("차이 수량");
                stockTaking.setDifferenceQuantity(SystemIn.SystemInInt());
                System.out.println("재고 실사 날짜 ex) 2024-08-19");
                stockTaking.setStockTakingDate(SystemIn.SystemInDate());
                System.out.println("비고");
                stockTaking.setNote(SystemIn.SystemInString());

                sts.insertStockTakingList(stockTaking);
        }
    }

    public void financeMenu() throws SQLException, IOException {
        ExpenditureService es = new FinanceServiceImpl(ConnectionFactory.getInstance().open());
        RevenueService rs = new FinanceServiceImpl(ConnectionFactory.getInstance().open());

        System.out.println("1. 매출 조회 2. 전체 지출 조회 3. 단일 지출 조회(수정, 삭제) 4. 지출 등록");
        System.out.print("->");
        int num = SystemIn.SystemInInt();
        switch (num) {
            case 1:
                System.out.println("전체 매출을 조회합니다.");
                rs.printAllRevenue();
                break;
            case 2:
                System.out.println("전체 지출을 조회합니다");
                es.printAllExpenditure();
                break;
            case 3:
                System.out.println("단일 지출 내역 조회");
                Expenditure expenditure = new Expenditure();
                System.out.println("조회할 지출 ID를 입력하세요.");
                expenditure.setExpenditureId(SystemIn.SystemInString());
                expenditure = es.printOneExpenditure(expenditure);

                System.out.println("1. 지출 수정 2. 지출 삭제");
                int choice = SystemIn.SystemInInt();
                switch (choice) {
                    case 1:
                        System.out.println("지출 수정");
                        System.out.println("수정할 지출 날짜 (yyyy-MM-dd):");
                        try {
                            expenditure.setExpenditureDate(SystemIn.SystemInDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("수정할 지출 금액:");
                        expenditure.setExpenditureCharge(SystemIn.SystemInInt());
                        System.out.println("수정할 지출 내역:");
                        expenditure.setExpenditureCategory(SystemIn.SystemInString());
                        System.out.println("수정할 비고:");
                        expenditure.setNote(SystemIn.SystemInString());

                        es.updateExpenditure(expenditure);
                        break;
                    case 2:
                        System.out.println("재고 실사 삭제. 해당 재고 실사 정보를 삭제할까요? 예, 아니오");
                        String answer = SystemIn.SystemInString();
                        if (answer.equals("예"))
                            es.deleteExpenditure(expenditure);
                        else
                            System.out.println("삭제를 취소합니다.");
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
                break;
            case 4:
                System.out.println("지출 등록");
                Expenditure ex = new Expenditure();
                System.out.println("지출 ID:");
                ex.setExpenditureId(SystemIn.SystemInString());
                System.out.println("창고 ID:");
                ex.setWarehouseId(SystemIn.SystemInString());
                System.out.println("지출 날짜 (yyyy-MM-dd):");
                try {
                    ex.setExpenditureDate(SystemIn.SystemInDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("지출 금액:");
                ex.setExpenditureCharge(SystemIn.SystemInInt());
                System.out.println("지출 카테고리:");
                ex.setExpenditureCategory(SystemIn.SystemInString());
                System.out.println("비고:");
                ex.setNote(SystemIn.SystemInString());
                es.insertExpenditure(ex);
                break;
            case 5:
                System.out.println("지출 수정");
                Expenditure updateExpenditure = new Expenditure();
                System.out.println("수정할 지출 ID:");
                updateExpenditure.setExpenditureId(SystemIn.SystemInString());
                System.out.println("새 창고 ID:");
                updateExpenditure.setWarehouseId(SystemIn.SystemInString());
                System.out.println("새 지출 날짜 (yyyy-MM-dd):");
                try {
                    updateExpenditure.setExpenditureDate(SystemIn.SystemInDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("새 지출 금액:");
                updateExpenditure.setExpenditureCharge(SystemIn.SystemInInt());
                System.out.println("새 지출 카테고리:");
                updateExpenditure.setExpenditureCategory(SystemIn.SystemInString());
                System.out.println("새 비고:");
                updateExpenditure.setNote(SystemIn.SystemInString());
                es.updateExpenditure(updateExpenditure);
                break;
            case 6:
                System.out.println("지출 삭제");
                Expenditure deleteExpenditure = new Expenditure();
                System.out.println("삭제할 지출 ID:");
                deleteExpenditure.setExpenditureId(SystemIn.SystemInString());
                System.out.println("창고 ID:");
                deleteExpenditure.setWarehouseId(SystemIn.SystemInString());
                es.deleteExpenditure(deleteExpenditure);
                break;
            case 7:
                System.out.println("지출 조회 (ID)");
                System.out.println("조회할 지출 ID:");
              
                break;
            default:
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }

    }
}
