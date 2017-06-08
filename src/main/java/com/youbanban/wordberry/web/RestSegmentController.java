package com.youbanban.wordberry.web;

import com.hankcs.hanlp.seg.common.Term;
import com.youbanban.wordberry.service.SegmentService;
import com.youbanban.wordberry.service.StanfordChineseNlpService;
import com.youbanban.wordberry.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Gerald_Yang.
 */
@RestController
@RequestMapping(value = {"/" + Constants.VERSION + "/segment/"}, method = RequestMethod.POST)
public class RestSegmentController {

    @Autowired
    private SegmentService segmentService; // hanLP分词及方法

    @Autowired
    private StanfordChineseNlpService nlpService;  // coreNLP方法封装

    @RequestMapping(value = "index/seg")
    public List<Term> indexTokenizerSeg(@RequestBody String text) {
        return segmentService.indexSegment(text);
    }

    @RequestMapping(value = "index/charseg")
    public List<Term> indexTokenizerCharSeg(@RequestBody String text) {
        return segmentService.indexSegment(text.toCharArray());
    }

    @RequestMapping(value = "index/sentence")
    public List<List<Term>> indexTokenizerSeg2sentence(@RequestBody String text) {
        return segmentService.indexSeg2sentence(text);
    }

    @RequestMapping(value = "nlp/seg")
    public List<Term> nLPTokenizerSeg(@RequestBody String text) {
        return segmentService.nLPSegment(text);
    }

    @RequestMapping(value = "nlp/charseg")
    public List<Term> nLPTokenizerCharSeg(@RequestBody String text) {
        return segmentService.nLPSegment(text.toCharArray());
    }

    @RequestMapping(value = "nlp/sentence")
    public List<List<Term>> nLPTokenizerSeg2sentence(@RequestBody String text) {
        return segmentService.nLPSeg2sentence(text);
    }

    @RequestMapping(value = "crf/seg")
    public List<Term> cRFSeg(@RequestBody String text) {
        return segmentService.cRFSegment(text);
    }

    @RequestMapping(value = "crf/charseg")
    public List<Term> cRFCharSeg(@RequestBody String text) {
        return segmentService.cRFSegment(text.toCharArray());
    }

    @RequestMapping(value = "crf/sentence")
    public List<List<Term>> cRFSeg2sentence(@RequestBody String text) {
        return segmentService.cRFSeg2sentence(text);
    }

    @RequestMapping(value = "hmm/seg")
    public List<Term> hMMseg(@RequestBody String text) {
        return segmentService.hMMSegment(text);
    }

    @RequestMapping(value = "hmm/charseg")
    public List<Term> hMMCharSeg(@RequestBody String text) {
        return segmentService.hMMSegment(text.toCharArray());
    }

    @RequestMapping(value = "hmm/sentence")
    public List<List<Term>> hMMSeg2sentence(@RequestBody String text) {
        return segmentService.hMMSeg2sentence(text);
    }

    @RequestMapping(value = "nshort/seg")
    public List<Term> nShortSeg(@RequestBody String text) {
        return segmentService.nShortSegment(text);
    }

    @RequestMapping(value = "nshort/charseg")
    public List<Term> nShortCharSeg(@RequestBody String text) {
        return segmentService.nShortSegment(text.toCharArray());
    }

    @RequestMapping(value = "nshort/sentence")
    public List<List<Term>> nShortSeg2sentence(@RequestBody String text) {
        return segmentService.nShortSeg2sentence(text);
    }

    @RequestMapping(value = "dijkstra/seg")
    public List<Term> dijkstraSeg(@RequestBody String text) {
        return segmentService.dijkstraSegment(text);
    }

    @RequestMapping(value = "dijkstra/charseg")
    public List<Term> dijkstraCharSeg(@RequestBody String text) {
        return segmentService.dijkstraSegment(text.toCharArray());
    }

    @RequestMapping(value = "dijkstra/sentence")
    public List<List<Term>> dijkstraSeg2sentence(@RequestBody String text) {
        return segmentService.dijkstraSeg2sentence(text);
    }

    @RequestMapping(value = "corenlpcrf/seg")
    public List<String> coreNlpCrf(@RequestBody String text) {
        return nlpService.runChineseAnnotators(text);
    }
}