package config;

import static config.UtilMethod.inputInt;
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
import services.memberServices;
import services.release.ReleaseRequestServiceImpl;
import services.release.ReleaseServiceImpl;
import services.warehouse.WarehouseServiceImpl;
import vo.Expenditure;
import vo.LOGO;
import vo.Stock;
import vo.StockTaking;
import vo.UserMessege;

public class CLIController {
    private static Connection connection;

    public static void BasicMenu() throws Exception {
        boolean validinput = false;

        LOGO.logo(); //로고
        UserMessege.START_WELCOME_MESSEGE.println(); //인사말

        while (!validinput) {
            UserMessege.START_MAIN_MENU.println();
            UserMessege.MENU_CHOICE.print();

            switch (SystemIn.SystemInInt()) {

                //로그인
                case 1:
                    System.out.println("로그인");
                    memberServices.login();
                    validinput = true;
                    break;

                //회원가입
                case 2:
                    System.out.println("회원가입");
                    memberServices.signInMember();
                    validinput = true;
                    break;

                //ID 찾기
                case 3:
                    System.out.println("ID 찾기");
                    validinput = true;
                    memberServices.findId();
                    break;

                //비밀번호 찾기
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

    public static void MemberMainMenu(String memberId) throws IOException, SQLException {
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.고객센터 | 6.회원정보");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("입고");
                    validinput = true;
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


                    validinput = true;
                    break;
                case 3:
                    System.out.println("재고 조회");
                    CLIController cliController = new CLIController();
                    try {
                        cliController.stockMenu();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    validinput = true;
                    break;
                case 4:
                    System.out.println("재무");
                    validinput = true;
                    break;
                case 5:
                    System.out.println("고객센터");
                    validinput = true;
                    break;
                case 6:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    validinput = true;
                    break;
                case 7:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public static void DeliveryMainMenu(String memberId) throws IOException {
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.배송 | 2.고객센터 | 3.회원정보 | 4.로그아웃");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("배송");
                    validinput = true;
                    break;
                case 2:
                    System.out.println("고객센터");
                    validinput = true;
                    break;
                case 3:
                    System.out.println("회원정보");
                    validinput = true;
                    break;
                case 4:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }
    }

    public static void adminMainMenu(String memberId) throws IOException {
        System.out.println(memberId + " 님 환영합니다.");
        System.out.println("메인 메뉴 : 1.입고 | 2.출고 | 3.재고 | 4.재무 | 5.창고 | 6.회원정보 | 7.로그아웃");
        int select = SystemIn.SystemInInt();
        boolean validinput = false;
        while (!validinput) {
            switch (select) {
                case 1:
                    System.out.println("입고");
                    validinput = true;
                    break;
                case 2:
                    ReleaseService releaseService = new ReleaseServiceImpl();
                    releaseService.showReleaseMenuForManager();
                    validinput = true;
                    break;
                case 3:
                    System.out.println("재고 메뉴");
                    CLIController cliController = new CLIController();
                    try {
                        cliController.stockMenu();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    validinput = true;
                    break;
                case 4:
                    System.out.println("재무");
                    validinput = true;
                    break;
                case 5:
                    WarehouseService warehouseService = new WarehouseServiceImpl();
                    warehouseService.warehouseMenu();
                    validinput = true;
                    break;
                case 6:
                    System.out.println("회원정보");
                    memberServices.viewMemberInfo(memberId);
                    validinput = true;
                    break;
                case 7:
                    System.out.println("로그아웃");
                    validinput = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시한번 입력해주세요");
            }
        }

    }

    public static void showMenu(String memberId) throws SQLException, IOException {
        try {
            connection = ConnectionFactory.open();
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
                                System.out.println("Unknown member type.");
                                break;
                        }
                    } else {
                        System.out.println("Member type not found.");
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
        System.out.println("1. 재고 전체 조회 2. 재고 구역 별 조회 3. 재고 실사");
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
                System.out.println("총 수량");
                stockTaking.setTotal(SystemIn.SystemInInt());
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

        System.out.println("1. 매출 조회 2. 지출 조회 3. 지출 등록 4. 지출 수정 5. 지출 삭제 6. 지출 조회 (ID)");
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
                System.out.println("지출 등록");
                Expenditure newExpenditure = new Expenditure();
                System.out.println("지출 ID:");
                newExpenditure.setExpenditureId(SystemIn.SystemInString());
                System.out.println("창고 ID:");
                newExpenditure.setWarehouseId(SystemIn.SystemInString());
                System.out.println("지출 날짜 (yyyy-MM-dd):");
                try {
                    newExpenditure.setExpenditureDate(SystemIn.SystemInDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("지출 금액:");
                newExpenditure.setExpenditureCharge(SystemIn.SystemInInt());
                System.out.println("지출 카테고리:");
                newExpenditure.setExpenditureCategory(SystemIn.SystemInString());
                System.out.println("비고:");
                newExpenditure.setNote(SystemIn.SystemInString());
                es.insertExpenditure(newExpenditure);
                break;
            case 4:
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
            case 5:
                System.out.println("지출 삭제");
                Expenditure deleteExpenditure = new Expenditure();
                System.out.println("삭제할 지출 ID:");
                deleteExpenditure.setExpenditureId(SystemIn.SystemInString());
                System.out.println("창고 ID:");
                deleteExpenditure.setWarehouseId(SystemIn.SystemInString());
                es.deleteExpenditure(deleteExpenditure);
                break;
            case 6:
                System.out.println("지출 조회 (ID)");
                System.out.println("조회할 지출 ID:");
                //es.printOneExpenditure(expenditureId);
                break;
            default:
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }
    }
}
