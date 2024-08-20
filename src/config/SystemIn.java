package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static Date SystemInDate() throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String dateString = br.readLine();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.parse(dateString);
    }
}
