package com.youbanban.wordberry.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.youbanban.wordberry.utility.Constants;

/**
 * 简单说明：
 *    t：大陆繁体
 *    tw：台湾繁体（所谓正体）
 *    hk：香港繁体
 * 
 * @author allenzhao
 * 
 */
@RestController
@RequestMapping(value = {"/" + Constants.VERSION + "/simple"})
public class SimpleServiceController {

    @RequestMapping(value ="keyword", method = { RequestMethod.POST })
    public List<String> getKeyword(@RequestBody String content,
            @RequestParam(value="count", required =false) Integer count) {
        if (count==null)
            count = 5;
        return HanLP.extractKeyword(content, count);
    }
    
    @RequestMapping(value ="phrase", method = { RequestMethod.POST })
    public List<String> getPhrase(@RequestBody String content,
            @RequestParam(value="count", required =false) Integer count) {
        if (count==null)
            count = 5;
        return HanLP.extractPhrase(content, count);
    }
    
    @RequestMapping(value ="summary_sentences", method = { RequestMethod.POST })
    public List<String> getSummarySentences(@RequestBody String content,
            @RequestParam(value="count", required =false) Integer count) {
        if (count==null)
            count = 5;
        return HanLP.extractSummary(content, count);
    }

    @RequestMapping(value ="summary", method = { RequestMethod.POST })
    public String getSummary(@RequestBody String content,
            @RequestParam(value="count", required =false) Integer count) {
        if (count==null)
            count = 5;
        return HanLP.getSummary(content, count);
    }

    @RequestMapping(value ="hk2s", method = { RequestMethod.POST })
    public String hk2s(@RequestBody String content) {
        return HanLP.hk2s(content);
    }
    
    @RequestMapping(value ="hk2t", method = { RequestMethod.POST })
    public String hk2t(@RequestBody String content) {
        return HanLP.hk2t(content);
    }

    @RequestMapping(value ="hk2tw", method = { RequestMethod.POST })
    public String hk2tw(@RequestBody String content) {
        return HanLP.hk2tw(content);
    }

    @RequestMapping(value ="s2hk", method = { RequestMethod.POST })
    public String s2hk(@RequestBody String content) {
        return HanLP.s2hk(content);
    }

    @RequestMapping(value ="s2t", method = { RequestMethod.POST })
    public String s2t(@RequestBody String content) {
        return HanLP.s2hk(content);
    }

    @RequestMapping(value ="s2tw", method = { RequestMethod.POST })
    public String s2tw(@RequestBody String content) {
        return HanLP.s2hk(content);
    }

    @RequestMapping(value ="t2hk", method = { RequestMethod.POST })
    public String t2hk(@RequestBody String content) {
        return HanLP.t2hk(content);
    }

    @RequestMapping(value ="t2tw", method = { RequestMethod.POST })
    public String t2tw(@RequestBody String content) {
        return HanLP.t2tw(content);
    }

    @RequestMapping(value ="t2s", method = { RequestMethod.POST })
    public String t2s(@RequestBody String content) {
        return HanLP.t2s(content);
    }
    
    @RequestMapping(value ="tw2hk", method = { RequestMethod.POST })
    public String tw2hk(@RequestBody String content) {
        return HanLP.tw2hk(content);
    }

    @RequestMapping(value ="tw2t", method = { RequestMethod.POST })
    public String tw2t(@RequestBody String content) {
        return HanLP.tw2t(content);
    }

    @RequestMapping(value ="tw2s", method = { RequestMethod.POST })
    public String tw2s(@RequestBody String content) {
        return HanLP.tw2s(content);
    }
    
    @RequestMapping(value ="segment", method = { RequestMethod.POST })
    public List<Term> segment(@RequestBody String content) {
        Segment s =HanLP.newSegment();
        s.enableJapaneseNameRecognize(true);
        s.enablePlaceRecognize(true);
        s.enableOrganizationRecognize(true);
        return s.seg(content);
    }
    
    @RequestMapping(value ="dependency", method = { RequestMethod.POST })
    public CoNLLSentence dependency(@RequestBody String content) {
        return HanLP.parseDependency(content);
    }
    
    @RequestMapping(value ="pinyin/list", method = { RequestMethod.POST })
    public List<Pinyin> pinyinList(@RequestBody String content) {
        return HanLP.convertToPinyinList(content);
    }

    /**
     * 转化为拼音
     *
     * @param content 文本
     * @param separator 分隔符
     * @param remainNone 有些字没有拼音（如标点），是否保留它们的拼音（true用none表示，false用原字符表示）
     * @return 一个字符串，由[拼音][分隔符][拼音]构成
     */
    @RequestMapping(value ="pinyin/string", method = { RequestMethod.POST })
    public String pinyinString(@RequestBody String content,
            @RequestParam(value="separator", required =false, defaultValue=" ")String separator, 
            @RequestParam(value="remainNone", required =false, defaultValue=" ")Boolean remainNone) {
        if (remainNone==null)
            remainNone=true;
        return HanLP.convertToPinyinString(content, separator, remainNone);
    }
    
    /**
     * 转化为拼音（首字母）
     *
     * @param content       文本
     * @param separator  分隔符
     * @param remainNone 有些字没有拼音（如标点），是否保留它们（用none表示）
     * @return 一个字符串，由[首字母][分隔符][首字母]构成
     */
    @RequestMapping(value ="pinyin/fcstring", method = { RequestMethod.POST })
    public String pinyinList(@RequestBody String content,
            @RequestParam(value="separator", required =false, defaultValue=" ")String separator, 
            @RequestParam(value="remainNone", required =false, defaultValue=" ")Boolean remainNone) {
        if (remainNone==null)
            remainNone=true;
        return HanLP.convertToPinyinFirstCharString(content, separator, remainNone);
    }
    
    @RequestMapping(value ="suggest", method = { RequestMethod.POST })
    public List<String> suggest(@RequestBody List<String> content,
            @RequestParam(value="word") String word,
            @RequestParam(value="count") Integer count) {
        Suggester suggester = new Suggester();
        for (String s : content)
            suggester.addSentence(s);
        return suggester.suggest(word, count);
    }
    
    @RequestMapping(value ="distance", method = { RequestMethod.POST })
    public long suggest(@RequestParam(value="a") String a,
            @RequestParam(value="b") String b) {
        return CoreSynonymDictionary.distance(a, b);
    }
}
