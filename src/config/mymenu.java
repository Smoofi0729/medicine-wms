package config;

import interfaces.StockPrintService;
import interfaces.StockTakingService;
import services.StockPrintServiceImpl;
import services.StockTakingServiceImpl;
import vo.Stock;
import vo.StockTaking;

import java.sql.Connection;
import java.sql.SQLException;

//합치고 지울 예정
public class mymenu {
    public static void main(String[] args) throws SQLException {
        int num = 3;
        StockPrintService sps = new StockPrintServiceImpl(ConnectionFactory.getInstance().open());
        StockTakingService sts = new StockTakingServiceImpl(ConnectionFactory.getInstance().open());
        switch (num) {
            // 재고 조회
            case 1:
                System.out.println("재고 조회");
                sps.printAllStock();
                //1. 전체 조회
                //2. 구역별 조회
                break;
                //재고 실사
            case 2:
                System.out.println("재고 구역별 조회");
                Stock stock = new Stock();
                stock.setSectionId("A");
                sps.printBySectionStock(stock);
                //1. 재고 실사 등록
                //2. 재고 실사 조회
                break;
            case 3:
                System.out.println("재고 실사");
                StockTaking s = new StockTaking();
                s.setStockTakingId("ST001");
                sts.printStockTakingList(s);
                //1. 재고 실사 등록
                //2. 재고 실사 조회
                    //2.1 수정
                    //2.2 삭제
                break;

        }
    }
}
