package com.youbanban.wordberry.service;


public class SegmenterFactory {
    public static final String HANLP_CRF = "hanlp_crf";
    public static final String HANLP_VIBERTI = "hanlp_viberti";
    public static final String CORE_CTB = "corenlp_ctb";
    public static final String CORE_PKU = "corenlp_pku";
    public static final String ZH = "zh";
    public static final String EN = "en";

    private SegmenterFactory() {
    }

    public static final Segmenter makeEnglishSegmenter() {
        return new EnglishSegmenter();
    }

    public static final Segmenter makeSegmenter(String language, String model) {

        Segmenter seg;
        switch (language) {
            case ZH:
                seg = makeChineseSegmenter(model);
                break;
            case EN:
                seg = makeEnglishSegmenter();
                break;
            default:
                seg = makeChineseSegmenter(model);
                break;
        }
        return seg;
    }

    public static final Segmenter makeChineseSegmenter(String model) {
        Segmenter seg;
        switch (model) {
            case HANLP_CRF:
                seg = new HanLpCrfSegmenter();
                break;
            case HANLP_VIBERTI:
                seg = new HanLpViterbiSegmenter();
                break;
            case CORE_CTB:
                seg = new CoreNlpCtbSegmenter();
                break;
            case CORE_PKU:
                seg = new CoreNlpPkuSegmenter();
                break;
            default:
                seg = new HanLpViterbiSegmenter();
                break;
        }
        return seg;
    }
}
