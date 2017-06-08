package com.youbanban.wordberry.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.youbanban.wordberry.service.SensitivewordFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gerald_Yang rewrite in 2017/04/27
 */
@Service
public class ScanWordsDao {

    @Autowired
    private SensitivewordFilter swfilter;

    //Cache Support for scanwords
    private static final Logger LOG = Logger.getLogger(ScanWordsDao.class);


    //Add text san
    public boolean scan(String text) {
        Map<Object, Object> sensitiveWordMap = swfilter.initKeyWord();
        text = text.replaceAll("<img\\s+[^>]*?src=[\"|\']((\\w+?:?//|\\/|\\w*)[^\"]*?)[\"|\'][^>]*?>", "");
        text = text.replaceAll("&nbsp;", "");
        text = text.replaceAll("<br>", "");
        Set<String> set = swfilter.getSensitiveWord(text, 1, sensitiveWordMap);
        LOG.info("this scan set is " + set.toString() + "and the size is " + set.size());
        return set.size() <= 0;
    }

    /**
     * @param text 文字内容
     * @return 敏感词
     */
    public List<String> scan_4(String text) {
        Map<Object, Object> sensitiveWordMap = swfilter.initKeyWord();
        text = text.replaceAll("<img\\s+[^>]*?src=[\"|\']((\\w+?:?//|\\/|\\w*)[^\"]*?)[\"|\'][^>]*?>", "");
        text = text.replaceAll("&nbsp;", "");
        text = text.replaceAll("<br>", "");
        Set<String> set = swfilter.getSensitiveWord(text, 1, sensitiveWordMap);
        LOG.info("this scan set is " + set.toString() + "and the size is " + set.size());
        List<String> scanWordsList = new ArrayList<>();
        scanWordsList.addAll(set);
        return scanWordsList;
    }
}
