package com.youbanban.wordberry.service;

import com.hankcs.hanlp.seg.common.Term;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.List;

/**
 * Created by Gerald_Yang on 2017/5/24.
 */
public interface Segmenter {
    /**
     * Split to sentences and each sentence are list of labels
     * @param s
     * @return
     */
    List<CoreLabel> seg2SentenceLabel(String s);
    /**
     * Split to sentences are each sentence is a space separated string
     * @param s
     * @return
     */
    List<String> seg2SentenceString(String s);

    /**
     * Split all word/sentence separated by white space
     * @param s
     * @return
     */
    String seg(String s);

    /**
     * This segment just only for hanLP
     *
     * @param s
     * @return
     */
    List<Term> segTerms(String s);

    /**
     * This segmenter just only for Hanlp
     *
     * @param s
     * @return
     */
    List<List<Term>> segSentenceTerms(String s);
}
