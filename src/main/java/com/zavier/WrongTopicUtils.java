package com.zavier;

import com.zavier.DTO.MatcherDTO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrongTopicUtils {
    private static String REGEX = "\\d{1,3}\\.";
    private static String REGEX2 = "[A-E]";
    private static String REGEX3 = "[A-E]\\.";
    private static String REGEX4 = "[A-E]\\..";
    private static String FALG = "flag";
    private static Integer SEQUENCE_NUMBER = 1;

    private static String questionPathString = "D:\\workspace\\ReadWordTable\\src\\main\\resources\\question.txt";
    private static String answerPathString = "D:\\workspace\\ReadWordTable\\src\\main\\resources\\answer.txt";
    private static String questionPathString2 = "D:\\workspace\\ReadWordTable\\src\\main\\resources\\question2.txt";
    private static String answerPathString2 = "D:\\workspace\\ReadWordTable\\src\\main\\resources\\answer2.txt";
    /**
     * 获取处理完整的答案
     *
     * @return
     */
    public static String getAnswer1(Integer sequenceNumber) throws IOException {
        FileInputStream fin = new FileInputStream(answerPathString);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        StringBuffer answerBuff = new StringBuffer();
        String strTmp = "";
        while((strTmp = buffReader.readLine())!=null){
            strTmp = strTmp.trim().replaceAll(" ","");
            if(strTmp.length() == 0
                    || "。".equalsIgnoreCase(String.valueOf(strTmp.charAt(strTmp.length()-1)))
                    ||")".equalsIgnoreCase(String.valueOf(strTmp.charAt(strTmp.length()-1)))){
                answerBuff.append(strTmp);
                answerBuff.append("\n");
            }
        }
        buffReader.close();
        return sequenceNumber(deleteLine(answerBuff.toString()),sequenceNumber);
    }

    /**
     * 获取处理完整的题干
     *
     * @return
     */
    public static String getStem1(Integer sequenceNumber) throws IOException {
        FileInputStream queFin = new FileInputStream(questionPathString);
        InputStreamReader queReader = new InputStreamReader(queFin);
        BufferedReader queBuffReader = new BufferedReader(queReader);
        StringBuffer queBuff = new StringBuffer();
        String strTmpQue = "";
        Pattern pattern = Pattern.compile(REGEX4);
        Pattern pattern2 = Pattern.compile(REGEX);
        while((strTmpQue = queBuffReader.readLine())!=null){
            strTmpQue = strTmpQue.trim();
            Matcher matcher = pattern.matcher(strTmpQue);
            Matcher matcher2 = pattern2.matcher(strTmpQue);
            Boolean flage = Boolean.FALSE;
            if(strTmpQue.length() != 0) {
                flage = matcher2.find()
                        && ("。".equalsIgnoreCase(String.valueOf(strTmpQue.charAt(strTmpQue.length() - 1)))
                        || ")".equalsIgnoreCase(String.valueOf(strTmpQue.charAt(strTmpQue.length() - 1)))
                        || "?".equalsIgnoreCase(String.valueOf(strTmpQue.charAt(strTmpQue.length() - 1))));
            }
            if(strTmpQue.length() == 0
                    ||matcher.find()
                    ||flage){
                queBuff.append(strTmpQue);
                queBuff.append("\n");
            }
        }
        queBuffReader.close();
        return addPoint(sequenceNumber(deleteLine(queBuff.toString()),sequenceNumber));
    }

    /**
     * 获取处理完整的答案2
     *
     * @return
     */
    public static String getAnswer2(Integer sequenceNumber,List<String> number) throws IOException {
        Path path = Paths.get(answerPathString2);
        byte[] data = Files.readAllBytes(path);
        String result = new String(data, "utf-8");
        List<MatcherDTO> integerList = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);
        StringBuffer stringBuffer = new StringBuffer(result);
        StringBuffer resultBuffer = new StringBuffer();
        Matcher matcher = pattern.matcher(result);
        while(matcher.find()) {
            integerList.add(new MatcherDTO(matcher.start(),matcher.end()));
        }
        for(int i = 0;i<integerList.size();i++){
            MatcherDTO item = integerList.get(i);
            if(number.contains(stringBuffer.substring(item.getStart(),item.getEnd()-1))){
                String substring = "";
                if(i!=integerList.size()-1) {
                    substring = stringBuffer.substring(item.getStart(),integerList.get(i+1).getStart());
                }else {
                    substring = stringBuffer.substring(item.getStart(),stringBuffer.length()-1);
                }
                resultBuffer.append(substring);
            }

        }
        return sequenceNumber(deleteLine(resultBuffer.toString()),sequenceNumber);
    }

    /**
     * 获取处理完整的题干2
     *
     * @return
     */
    public static String getStem2(Integer sequenceNumber,List<String> number) throws IOException {
        Path path = Paths.get(questionPathString2);
        byte[] data = Files.readAllBytes(path);
        String result = new String(data, "utf-8");
        List<MatcherDTO> integerList = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);
        StringBuffer stringBuffer = new StringBuffer(result);
        StringBuffer resultBuffer = new StringBuffer();
        Matcher matcher = pattern.matcher(result);
        while(matcher.find()) {
            integerList.add(new MatcherDTO(matcher.start(),matcher.end()));
        }
        for(int i = 0;i<integerList.size();i++){
            MatcherDTO item = integerList.get(i);
            if(number.contains(stringBuffer.substring(item.getStart(),item.getEnd()-1))){
                String substring = "";
                if(i!=integerList.size()-1) {
                    substring = stringBuffer.substring(item.getStart(),integerList.get(i+1).getStart());
                }else {
                    substring = stringBuffer.substring(item.getStart(),stringBuffer.length()-1);
                }
                resultBuffer.append(substring);
            }
        }
        String resultStr = resultBuffer.toString().replaceAll("×", " ").replaceAll("✔", " ");
        return addPoint(sequenceNumber(deleteLine(resultStr),sequenceNumber));
    }
    /**
     * 将 . 前的数字 变成序号
     *
     * @param content
     * @return
     */
    public static String sequenceNumber(String content,Integer sequenceNumber) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(content);
        while (true) {
            if (!matcher.find()) {
                break;
            }
            content = content.replaceFirst(REGEX, sequenceNumber.toString() + FALG);
            sequenceNumber++;
        }
        content = content.replaceAll(FALG, ".");

        return content;
    }

    /**
     * 删除空白换行
     *
     * @param content
     * @return
     */
    public static String deleteLine(String content) {
        content = content.replaceAll("[<br>]{0,}", "").replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return content;
    }

    /**
     * 选项前添加.
     *
     * @param content
     * @return
     */
    public static String addPoint(String content) {
        StringBuffer stringBuffer = new StringBuffer(content);
        Pattern pattern = Pattern.compile(REGEX2);
        Matcher matcher = pattern.matcher(stringBuffer);
        while (matcher.find()) {
            int i = matcher.start();
            if (!".".equalsIgnoreCase(String.valueOf(stringBuffer.charAt(i + 1)))) {
                stringBuffer.insert(i + 1, ".");
            }
        }
        return stringBuffer.toString();
    }

}
