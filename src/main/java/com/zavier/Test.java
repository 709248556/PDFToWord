package com.zavier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static Integer sequenceNumber = 53;
    private static String numberPath = "D:\\workspace\\ReadWordTable\\src\\main\\resources\\number.txt";
    public static void main(String[] args) throws IOException {
         List<String> number = new ArrayList<>();
        FileInputStream queFin = new FileInputStream(numberPath);
        InputStreamReader queReader = new InputStreamReader(queFin);
        BufferedReader queBuffReader = new BufferedReader(queReader);
        String strTmpQue = "";
        while((strTmpQue = queBuffReader.readLine())!=null){
            number.add(strTmpQue);
        }

//        System.out.println(WrongTopicUtils.getStem1(sequenceNumber));
//        System.out.println(WrongTopicUtils.getAnswer1(sequenceNumber));
        System.out.println(WrongTopicUtils.getStem2(sequenceNumber,number));
        System.out.println(WrongTopicUtils.getAnswer2(sequenceNumber,number));

    }
}
