package com.youbanban.wordberry.service;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Gerald_Yang on 2017/5/24.
 */
public abstract class AbstracktCoreNlpSegmenter implements Segmenter{
    private static final Logger LOG = Logger.getLogger(AbstracktCoreNlpSegmenter.class);
    protected abstract CRFClassifier<CoreLabel> getSegmenter();
    protected CRFClassifier<CoreLabel> createSegmenter(Properties props, String model) {
        LOG.debug("Load model: " + model);
        CRFClassifier<CoreLabel> seg = new CRFClassifier<CoreLabel>(props);
        seg.loadClassifierNoExceptions(model, props);
        return seg;
    }

    public List<CoreLabel> seg2SentenceLabel(String s) {
        List<String> lt = this.getSegmenter().segmentString(s);
        CoreLabelTokenFactory factory = new CoreLabelTokenFactory();
        List<CoreLabel> ls = new ArrayList<>();
        int from = 0;
        for (String t : lt) {
            int offset = s.indexOf(t, from);
            ls.add(factory.makeToken(t, offset, t.length()));
            from = offset + t.length();
        }
        return ls;
    }

    public List<String> seg2SentenceString(String s) {
        return this.getSegmenter().segmentString(s);
    }

    public String seg(String s) {
        List<String> ls = this.seg2SentenceString(s);
        StringBuilder sb = new StringBuilder();
        for (String t : ls) {
            if (sb.length()>0)
                sb.append(' ');
            sb.append(t);
        }
        return sb.toString();
    }

    @Override
    public List<Term> segTerms(String s) {
        List<Term> objects = new LinkedList<>();
        Term term0 = new Term("This", Nature.n);
        Term term1 = new Term("segmenter", Nature.n);
        Term term2 = new Term("only", Nature.n);
        Term term3 = new Term("for", Nature.n);
        Term term4 = new Term("hanlp", Nature.n);
        objects.add(term0);
        objects.add(term1);
        objects.add(term2);
        objects.add(term3);
        objects.add(term4);
        return objects;
    }

    @Override
    public List<List<Term>> segSentenceTerms(String s) {
        List<Term> objects = new LinkedList<>();
        Term term0 = new Term("This", Nature.n);
        Term term1 = new Term("segmenter", Nature.n);
        Term term2 = new Term("only", Nature.n);
        Term term3 = new Term("for", Nature.n);
        Term term4 = new Term("hanlp", Nature.n);
        objects.add(term0);
        objects.add(term1);
        objects.add(term2);
        objects.add(term3);
        objects.add(term4);
        List<List<Term>> lists = new LinkedList<>();
        lists.add(objects);
        return lists;
    }
}
