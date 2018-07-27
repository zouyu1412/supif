package com.zouyu.regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class Regex {

    public static void main(String[] args) throws IOException {
        System.out.println(Regex.class.getClassLoader().getResource("").getPath());
        BufferedReader in;
        Pattern pattern = Pattern.compile("//(//d{3}//)//s//d{3}-//d{4}");
        in = new BufferedReader(new FileReader("F:\\workspace\\projects\\supif\\src/main\\java\\com\\zouyu\\phone"));
        String s;
        while((s=in.readLine())!=null){
            Matcher matcher = pattern.matcher(s);
            if(matcher.find()){
                System.out.println(matcher.group());
            }
        }
        in.close();

    }
}
