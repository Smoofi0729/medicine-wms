package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemIn {

    public static String SystemInString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        return str;
    }

    public static Integer SystemInInt() throws IOException {
        int num = Integer.parseInt(SystemInString());
        return num;

    }



}
