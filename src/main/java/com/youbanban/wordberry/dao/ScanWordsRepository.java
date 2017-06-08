package com.youbanban.wordberry.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gerald_Yang.
 */
@Service
public class ScanWordsRepository {

    private Set<String> set = new HashSet<>();

    @Value("${sensitive-word.pathname}")
    private String path;

    public Set initKeywork() {
        String[] pathname = path.split(",");
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            InputStream reader = null;
            ClassLoader loader = Thread.currentThread().getContextClassLoader(); // 得到上下文类加载器(contextClassLoader)
            for (String aPathname : pathname) {
                reader = loader.getResourceAsStream(aPathname.trim());
                BufferedReader br = new BufferedReader(new InputStreamReader(reader)); // 建立一个对象，它把文件内容转成计算机能读懂的语言
                String line;
                line = br.readLine();
                if (line != null) {
                    set.add(line);
                }
                while (line != null) {
                    line = br.readLine(); // 一次读入一行数据
                    set.add(line);
                }
                br.close();
            }
            assert reader != null;
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }


}
