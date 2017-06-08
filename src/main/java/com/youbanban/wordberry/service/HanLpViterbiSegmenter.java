package com.youbanban.wordberry.service;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

/**
 * @author AllenZhao, Gerald_Yang
 */
public class HanLpViterbiSegmenter extends AbstractHanlpSegementer {
    private static Segment seg = NLPTokenizer.SEGMENT;

    protected HanLpViterbiSegmenter() {
        if (seg == null) {
            seg = NLPTokenizer.SEGMENT;
            seg.enableOffset(true);
        }
    }

    @Override
    protected Segment getSegmenter() {
        return seg;
    }


}
