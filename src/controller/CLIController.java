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
import java.util.Optional;

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
                    InboundService inboundService = new InboundService();

                    while (!exit) {
                        System.out.println(memberId + " 님 환영합니다.");
                        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보 | 7.로그아웃");
                        int select = SystemIn.SystemInInt();
                        switch (select) {
                            case 1:
                                System.out.println("입고");
                                inboundService.serviceInboundForMember(memberId);
                    break;
                case 2:
                    System.out.println("나의 출고 메뉴 : 1.출고요청 | 2.출고현황");
                    switch (inputInt("메뉴선택")) {
                        case 1 :
                            ReleaseRequestService releaseRequestService = new ReleaseRequestServiceImpl();
                            releaseRequestService.releaseRequestMenuForMall(memberId);
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
        InboundService inboundService = new InboundService();
        CLIController cliController = new CLIController();
        
        while (!exit) {
            System.out.println(memberId + " 님 환영합니다.");
            System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.창고 | 6.회원정보 관리 | 7.로그아웃");
            int select = SystemIn.SystemInInt();
            switch (select) {
                case 1:
                    System.out.println("입고");
                    inboundService.serviceInboundForManager(memberId);
                    break;
                case 2:
                    ReleaseService releaseService = new ReleaseServiceImpl();
                    releaseService.showReleaseMenuForManager(memberId);
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
                    warehouseService.warehouseMenu(memberId);
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
            case 1:
                printAllStock(sps);
                break;
            case 2:
                printStockBySection(sps);
                break;
            case 3:
                manageStockTaking(sts);
                break;
            case 4:
                registerStockTaking(sts);
                break;
            default:
                System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
        }
    }

    private void printAllStock(StockPrintService sps) throws SQLException {
        System.out.println("전체 재고를 조회합니다.");
        sps.printAllStock();
    }

    private void printStockBySection(StockPrintService sps) throws SQLException, IOException {
        System.out.println("재고 구역별 조회. 구역 아이디를 입력하세요.");
        System.out.print("->");
        String sectionId = SystemIn.SystemInString();
        Stock stock = new Stock();
        stock.setSectionId(sectionId);
        sps.printBySectionStock(stock);
    }

    private void manageStockTaking(StockTakingService sts) throws SQLException, IOException {
        System.out.println("재고 실사 조회. 재고 실사 아이디를 입력해주세요.");
        System.out.print("->");
        String stockTakingId = SystemIn.SystemInString();
        StockTaking s = new StockTaking();
        s.setStockTakingId(stockTakingId);
        Optional<StockTaking> optionalStockTaking = sts.printStockTakingList(s);

        if (!optionalStockTaking.isPresent()) {
            System.out.println("재고 실사 아이디가 존재하지 않습니다. 다시 시도해주세요.");
            return;
        }
        s = optionalStockTaking.get();
        System.out.println("재고 실사 정보"); //한줄로 출력하자
        System.out.println("재고실사id \t창고id \t제품id \t제품이름 \t로트번호 \t전산상재고 \t실제재고 \t차이수량 \t재고실사날짜 \t비고");
        System.out.println(s.getStockTakingId() + "\t\t" + s.getWarehouseId() + "\t\t" + s.getProductId() + "\t\t" + s.getProductName() + "\t\t" + s.getLotNo() + "\t\t" + s.getComputerizedStock() + "\t\t" + s.getPhysicalStock() + "\t\t" + s.getDifferenceQuantity() + "\t\t" + s.getStockTakingDate() + "\t\t" + s.getNote());

        System.out.println("1. 재고 실사 수정 2. 재고 실사 삭제");
        int choice = SystemIn.SystemInInt();
        switch (choice) {
            case 1:
                updateStockTaking(sts, s);
                break;
            case 2:
                deleteStockTaking(sts, s);
                break;
            default:
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }
    }

    private void updateStockTaking(StockTakingService sts, StockTaking s) throws SQLException, IOException {
        System.out.println("재고 실사 수정. 재고 실사 정보를 수정해주세요.");
        System.out.println("전산 상 재고");
        System.out.print("->");
        s.setComputerizedStock(SystemIn.SystemInInt());
        System.out.println("실제 재고");
        System.out.print("->");
        s.setPhysicalStock(SystemIn.SystemInInt());
        System.out.println("차이 수량");
        System.out.print("->");
        s.setDifferenceQuantity(SystemIn.SystemInInt());
        System.out.println("비고");
        System.out.print("->");
        s.setNote(SystemIn.SystemInString());
        sts.UpdateStockTakingList(s);
    }

    private void deleteStockTaking(StockTakingService sts, StockTaking s) throws SQLException, IOException {
        System.out.println("재고 실사 삭제. 해당 재고 실사 정보를 삭제할까요? Y, N");
        System.out.print("->");
        String answer = SystemIn.SystemInString();
        if (answer.equals("Y")) {
            sts.deleteStockTakingList(s);
        } else {
            System.out.println("삭제를 취소합니다.");
        }
    }

    private void registerStockTaking(StockTakingService sts) throws SQLException, IOException, ParseException {
        System.out.println("재고 실사 등록. 재고 실사 정보를 등록해주세요.");
        StockTaking stockTaking = new StockTaking();
        System.out.println("재고 실사 아이디");
        System.out.print("->");
        stockTaking.setStockTakingId(SystemIn.SystemInString());
        System.out.println("창고 아이디");
        System.out.print("->");
        stockTaking.setWarehouseId(SystemIn.SystemInString());
        System.out.println("제품 아이디");
        System.out.print("->");
        stockTaking.setProductId(SystemIn.SystemInString());
        System.out.println("제품 이름");
        System.out.print("->");
        stockTaking.setProductName(SystemIn.SystemInString());
        System.out.println("로트 번호");
        System.out.print("->");
        stockTaking.setLotNo(SystemIn.SystemInString());
        System.out.println("전산 상 재고");
        System.out.print("->");
        stockTaking.setComputerizedStock(SystemIn.SystemInInt());
        System.out.println("실제 재고");
        System.out.print("->");
        stockTaking.setPhysicalStock(SystemIn.SystemInInt());
        System.out.println("차이 수량");
        System.out.print("->");
        stockTaking.setDifferenceQuantity(SystemIn.SystemInInt());
        System.out.println("재고 실사 날짜 ex) YYYY-MM-DD");
        System.out.print("->");
        stockTaking.setStockTakingDate(SystemIn.SystemInDate());
        System.out.println("비고");
        System.out.print("->");
        stockTaking.setNote(SystemIn.SystemInString());
        sts.insertStockTakingList(stockTaking);
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
                System.out.print("->");
                expenditure.setExpenditureId(SystemIn.SystemInString());

                Optional<Expenditure> optionalExpenditure = es.printOneExpenditure(expenditure);

                if (!optionalExpenditure.isPresent()) {
                    System.out.println("지출 ID가 존재하지 않습니다. 다시 시도해주세요.");
                    return;
                }

                expenditure = optionalExpenditure.get();
                System.out.println("지출id \t창고id \t지출일자 \t지출액 \t지출분류 \t비고");
                System.out.println(expenditure.getExpenditureId() + "\t" + expenditure.getWarehouseId() + "\t" + expenditure.getExpenditureDate() + "\t" + expenditure.getExpenditureCharge() + "\t" + expenditure.getExpenditureCategory() + "\t" + expenditure.getNote());

                System.out.println("1. 지출 수정 2. 지출 삭제");
                System.out.print("->");
                int choice = SystemIn.SystemInInt();
                switch (choice) {
                    case 1:
                        System.out.println("지출 수정");
                        System.out.println("수정할 지출 날짜 ex) YYYY-MM-DD");
                        System.out.print("->");
                        try {
                            expenditure.setExpenditureDate(SystemIn.SystemInDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("수정할 지출 금액:");
                        System.out.print("->");
                        expenditure.setExpenditureCharge(SystemIn.SystemInInt());
                        System.out.println("수정할 지출 내역:");
                        System.out.print("->");
                        expenditure.setExpenditureCategory(SystemIn.SystemInString());
                        System.out.println("수정할 비고:");
                        System.out.print("->");
                        expenditure.setNote(SystemIn.SystemInString());

                        es.updateExpenditure(expenditure);
                        break;
                    case 2:
                        System.out.println("재고 실사 삭제. 해당 재고 실사 정보를 삭제할까요? Y, N");
                        System.out.print("->");
                        String answer = SystemIn.SystemInString();
                        if (answer.equals("Y"))
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
                System.out.println("지출 ID");
                System.out.print("->");
                ex.setExpenditureId(SystemIn.SystemInString());
                System.out.println("창고 ID");
                System.out.print("->");
                ex.setWarehouseId(SystemIn.SystemInString());
                System.out.println("지출 날짜 ex)YYYY-MM-DD");
                System.out.print("->");
                try {
                    ex.setExpenditureDate(SystemIn.SystemInDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("지출 금액");
                System.out.print("->");
                ex.setExpenditureCharge(SystemIn.SystemInInt());
                System.out.println("지출 내역");
                System.out.print("->");
                ex.setExpenditureCategory(SystemIn.SystemInString());
                System.out.println("비고:");
                System.out.print("->");
                ex.setNote(SystemIn.SystemInString());
                es.insertExpenditure(ex);
                break;
            default:
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }

    }
}
