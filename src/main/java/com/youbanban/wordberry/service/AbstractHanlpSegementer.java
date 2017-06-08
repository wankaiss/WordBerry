package com.youbanban.wordberry.service;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerald_Yang on 2017/5/24.
 */
public abstract class AbstractHanlpSegementer implements Segmenter{

    protected abstract Segment getSegmenter();

    public List<CoreLabel> seg2SentenceLabel(String s) {
        CoreLabelTokenFactory factory = new CoreLabelTokenFactory();
        List<Term> lt = getSegmenter().seg(s);
        List<CoreLabel> ls = new ArrayList<>();
        for (Term t : lt) {
            ls.add(factory.makeToken(t.word, t.offset, t.length()));
        }
        return ls;
    }

    public List<String> seg2SentenceString(String s) {
        List<Term> lt = getSegmenter().seg(s);
        List<String> ls = new ArrayList<>();
        for (Term t : lt) {
            ls.add(t.word);
        }
        return ls;
    }

    public String seg(String s) {
        List<Term> lt = getSegmenter().seg(s);
        StringBuilder sb = new StringBuilder();
        for (Term t : lt) {
            if (sb.length()>0)
                sb.append(' ');
            sb.append(t.word);
        }
        return sb.toString();
    }

    public List<Term> segTerms(String s) {
        return getSegmenter().seg(s);
    }

    public List<List<Term>> segSentenceTerms(String s) {
        return getSegmenter().seg2sentence(s);
    }
}
