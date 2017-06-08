package com.youbanban.wordberry.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class EnglishSegmenter extends AbstracktCoreNlpSegmenter {
    @Override
    public List<CoreLabel> seg2SentenceLabel(String s) {
        StringReader sr = new StringReader(s);
        DocumentPreprocessor dp = new DocumentPreprocessor(sr);
        List<CoreLabel> ls = new ArrayList<CoreLabel>();
        CoreLabelTokenFactory factory = new CoreLabelTokenFactory();
        int from = 0;
        for (List<HasWord> sentence : dp) {
            for (HasWord w : sentence) {
                int offset = s.indexOf(w.word(), from);
                ls.add(factory.makeToken(w.word(), offset, w.word().length()));
                from = offset + w.word().length();
            }
         }
        return ls;
    }
    public List<String> seg2SentenceString(String s) {
        StringReader sr = new StringReader(s);
        DocumentPreprocessor dp = new DocumentPreprocessor(sr);
        List<String> sent = new ArrayList<String>();
        for (List<HasWord> sentence : dp) {
            for (HasWord l : sentence) {
                sent.add(l.word());
            }
         }
        return sent;
    }  
    @Override
    protected CRFClassifier<CoreLabel> getSegmenter() {
        return null;
    }

}
