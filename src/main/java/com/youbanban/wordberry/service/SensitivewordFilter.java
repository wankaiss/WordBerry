package com.youbanban.wordberry.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.youbanban.wordberry.dao.ScanWordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Description: 敏感词过滤
 * @Project：test
 * @Author : chenming
 * @Date ： 2014年4月20日 下午4:17:15
 */
@Service
@SuppressWarnings("rawtypes")
public class SensitivewordFilter {

    @Autowired
    private ScanWordsRepository repository;
    private final static Logger LOG = LoggerFactory.getLogger(SensitivewordFilter.class);
    private Map<Object, Object> sensitiveWordMap = null;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @author xinglinfan
     * @date 2016年04月15日 下午4:28:30
     * @version 1.0
     */
    public boolean isContaintSensitiveWord(String txt, int matchType, Map<Object, Object> sensitiveWordMap) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType, sensitiveWordMap); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       敏感词
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @author xinglinfan
     * @date 2016年04月15日 下午5:10:52
     * @version 1.0
     */
    public Set<String> getSensitiveWord(String txt, int matchType, Map<Object, Object> sensitiveWordMap) {
        Set<String> sensitiveWordList = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {
            int length = CheckSensitiveWord(txt, i, matchType, sensitiveWordMap);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @author xinglinfan
     * @date 2016年04月15日 下午5:12:07
     * @version 1.0
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar, Map<Object, Object> sensitiveWordMap) {
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType, sensitiveWordMap);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     * @author xinglinfan
     * @date 2016年04月15日 下午5:21:19
     * @version 1.0
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @author xinglinfan
     * @date 2016年04月15日 下午4:31:03
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType, Map<Object, Object> sensitiveWordMap) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd")) || "1".equals(nowMap.get("isOne"))) {
                    flag = true;
                    if (SensitivewordFilter.minMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        if (matchFlag < 2 && flag) {
            matchFlag = 1;
        }
        return matchFlag;
    }

    public Map<Object, Object> initKeyWord() {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    @SuppressWarnings("unchecked")
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap<Object, Object>(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map<Object, Object> nowMap = null;
        Map<Object, Object> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();    //关键字
            if (key == null) {
                continue;
            }
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    newWorMap = new HashMap<Object, Object>();
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }


                if (key.length() > 1) {
                    if (i == key.length() - 1) {
                        nowMap.put("isEnd", "1");
                    }
                } else if (key.length() == 1) {
                    nowMap.put("isOne", "1");
                }
            }
        }
    }

    private Set<String> readSensitiveWordFile() throws Exception {
        Set<String> set = repository.initKeywork();
        return set;
    }
}
