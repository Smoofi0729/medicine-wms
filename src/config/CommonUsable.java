package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonUsable {

  public static String inputStr(String input) {
    System.out.print(input + " : ");
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      return br.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static int inputInt(String input) {
    System.out.print(input + " : ");
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      return Integer.parseInt(br.readLine());
    } catch (NumberFormatException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
