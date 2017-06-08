package com.youbanban.wordberry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordBerryApplicationTests {

	@Test
	public void contextLoads() {
	}

    @Test
    public void crfSegment01() {

        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
                /* 读入TXT文件 */

            String pathname = "sensitiveWord/anti-corruption.txt";
            ClassLoader loader = Thread.currentThread().getContextClassLoader(); // 得到上下文类加载器(contextClassLoader)
            InputStream reader = loader.getResourceAsStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(reader)); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            Set<String> set = new HashSet<String>();
            if (line != null) {
                set.add(line);
            }
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                set.add(line);
            }
            br.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() {

    }

}
