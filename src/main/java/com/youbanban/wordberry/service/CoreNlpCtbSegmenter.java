package com.youbanban.wordberry.service;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.Properties;

/**
 * Created by Gerald_Yang on 2017/5/24.
 */
public class CoreNlpCtbSegmenter extends AbstracktCoreNlpSegmenter {
    private static final String MODEL = CoreNLPAndHanLP.Configure.CoreNLPctbmodel;
    private static Properties props = new Properties();

    private static CRFClassifier<CoreLabel> seg;
    protected CoreNlpCtbSegmenter() {
        props.setProperty("sighanCorporaDict", CoreNLPAndHanLP.Configure.CoreNLPsighanCorporaDict);
        /*
        这个不确定是否可以，因为是数组
         */
        props.setProperty("serDictionary", String.valueOf(CoreNLPAndHanLP.Configure.CoreNLPserDictionary));
        props.setProperty("sighanPostProcessing", CoreNLPAndHanLP.Configure.CoreNLPsighanPostProcessing);
        props.setProperty("inputEncoding", CoreNLPAndHanLP.Configure.CoreNLPinputEncoding);
        props.setProperty("keepAllWhitespaces", CoreNLPAndHanLP.Configure.CoreNLPkeepAllWhitespaces);
        if (seg == null)
            seg = createSegmenter(props, MODEL);
    }

    @Override
    protected CRFClassifier<CoreLabel> getSegmenter() {
        return seg;
    }
}
