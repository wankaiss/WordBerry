package com.youbanban.wordberry.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.youbanban.wordberry.utility.Constants;

@RestController
@RequestMapping(value = {"/" + Constants.VERSION + "/segment"})
public class SegmentController {

    @RequestMapping(value ="standard", method = { RequestMethod.POST })
    public List<Term> standard(@RequestBody String content) {
        return StandardTokenizer.segment(content);
    }

    @RequestMapping(value ="standard_sentence", method = { RequestMethod.POST })
    public List<List<Term>> standardSentence(@RequestBody String content) {
        return StandardTokenizer.seg2sentence(content);
    }

    @RequestMapping(value ="nlp", method = { RequestMethod.POST })
    public List<Term> nlp(@RequestBody String content) {
        return NLPTokenizer.segment(content);
    }

    @RequestMapping(value ="nlp_sentence", method = { RequestMethod.POST })
    public List<List<Term>> nlpSentence(@RequestBody String content) {
        return NLPTokenizer.seg2sentence(content);
    }

}
