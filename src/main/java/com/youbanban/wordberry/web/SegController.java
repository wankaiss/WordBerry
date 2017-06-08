package com.youbanban.wordberry.web;

import com.hankcs.hanlp.seg.common.Term;
import com.youbanban.wordberry.service.Segmenter;
import com.youbanban.wordberry.service.SegmenterFactory;
import com.youbanban.wordberry.utility.Constants;
import edu.stanford.nlp.ling.CoreLabel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gerald_Yang.
 */
@RestController
@RequestMapping(value = "/seg/" + Constants.VERSION + "/", method = RequestMethod.POST)
public class SegController {

    /*
    HANLP_CRF = "hanlp_crf";
    HANLP_VIBERTI = "hanlp_viberti";
    CORE_CTB = "corenlp_ctb";
    CORE_PKU = "corenlp_pku";
    这是负责用哪一种分词方式
     */

    @RequestMapping(value = "seg")
    public String seg(@RequestBody String context, @RequestParam String language, @RequestParam String model) {
        Segmenter segmenter = SegmenterFactory.makeSegmenter(language, model);
        return segmenter.seg(context);
    }

    @RequestMapping(value = "seg2SentenceString")
    public List<String> seg2Sentence(@RequestBody String context, @RequestParam String language, @RequestParam String model) {
        Segmenter segmenter = SegmenterFactory.makeSegmenter(language, model);
        return segmenter.seg2SentenceString(context);
    }

    @RequestMapping(value = "seg2SentenceLabel")
    public List<CoreLabel> seg1(@RequestBody String context, @RequestParam String language, @RequestParam String model) {
        Segmenter segmenter = SegmenterFactory.makeSegmenter(language, model);
        return segmenter.seg2SentenceLabel(context);
    }

    @RequestMapping(value = "segTerms")
    public List<Term> segTerm(@RequestBody String context, @RequestParam String language, @RequestParam String model) {
        Segmenter segmenter = SegmenterFactory.makeSegmenter(language, model);
        return segmenter.segTerms(context);
    }

    @RequestMapping(value = "segSentenceTerms")
    public List<List<Term>> segSentenceTerms(@RequestBody String context, @RequestParam String language, @RequestParam String model) {
        Segmenter segmenter = SegmenterFactory.makeSegmenter(language, model);
        return segmenter.segSentenceTerms(context);
    }
}

