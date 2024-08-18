package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonMenu {

    public static int userChoice() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();
        int num = Integer.parseInt(str);
        return num;
    }


    public void startMenu() throws IOException {
        while (true) {
            int select = userChoice();  //
            switch (select) {
                case 1:
                    System.out.println("로그인");
                    break;
                case 2:
                    System.out.println("회원가입");
                    break;
                case 3:
                    System.out.println("ID찾기");
                    break;
                case 4:
                    System.out.println("PW찾기");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
            }
        }
    }


}
