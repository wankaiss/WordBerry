package com.youbanban.wordberry.service;

import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.HMM.HMMSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gerald_Yang.
 */
@Service
public class SegmentService {

    private final CRFSegment CRF_SEGMENT = new CRFSegment();
    private final DijkstraSegment DIJKSTRA_SEGMENT = new DijkstraSegment();
    private final HMMSegment HMM_SEGMENT = new HMMSegment();
    private final NShortSegment N_SHORT_SEGMENT = new NShortSegment();


    /**
     * @param text 文本
     * @return
     * @description escription 以下三个方法是对于indexTokenizer分词
     */
    public List<Term> indexSegment(String text) {
        return IndexTokenizer.segment(text);
    }

    public List<Term> indexSegment(char[] text) {
        return IndexTokenizer.segment(text);
    }

    public List<List<Term>> indexSeg2sentence(String text) {
        return IndexTokenizer.seg2sentence(text);
    }

    /**
     * @param text 文本
     * @return
     * @description 以下三个方法是对于NLPTokenizer分词
     */
    public List<Term> nLPSegment(String text) {
        return NLPTokenizer.segment(text);
    }

    public List<Term> nLPSegment(char[] text) {
        return NLPTokenizer.segment(text);
    }

    public List<List<Term>> nLPSeg2sentence(String text) {
        return NLPTokenizer.seg2sentence(text);
    }

    /**
     * @param text 文本
     * @return
     * @description CRF分词
     */
    public List<Term> cRFSegment(String text) {
        return CRF_SEGMENT.seg(text);
    }

    public List<Term> cRFSegment(char[] text) {
        return CRF_SEGMENT.seg(text);
    }

    public List<List<Term>> cRFSeg2sentence(String text) {
        return CRF_SEGMENT.seg2sentence(text);
    }

    /**
     * @param text 文本
     * @return
     * @description dijkstra分词
     */
    public List<Term> dijkstraSegment(String text) {
        return DIJKSTRA_SEGMENT.seg(text);
    }

    public List<Term> dijkstraSegment(char[] text) {
        return DIJKSTRA_SEGMENT.seg(text);
    }

    public List<List<Term>> dijkstraSeg2sentence(String text) {
        return DIJKSTRA_SEGMENT.seg2sentence(text);
    }

    /**
     * @param text 文本
     * @return
     * @description HMM分词
     */
    public List<Term> hMMSegment(String text) {
        return HMM_SEGMENT.seg(text);
    }

    public List<Term> hMMSegment(char[] text) {
        return HMM_SEGMENT.seg(text);
    }

    public List<List<Term>> hMMSeg2sentence(String text) {
        return HMM_SEGMENT.seg2sentence(text);
    }


    /**
     * @param text 文本
     * @return
     * @description NShort分词
     */
    public List<Term> nShortSegment(String text) {
        return N_SHORT_SEGMENT.seg(text);
    }

    public List<Term> nShortSegment(char[] text) {
        return N_SHORT_SEGMENT.seg(text);
    }

    public List<List<Term>> nShortSeg2sentence(String text) {
        return N_SHORT_SEGMENT.seg2sentence(text);
    }
}
