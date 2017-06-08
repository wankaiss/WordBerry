package com.youbanban.wordberry.service;

import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Segment;

/**
 * Created by Gerald_Yang on 2017/5/24.
 */
public class HanLpCrfSegmenter extends  AbstractHanlpSegementer{
    private static CRFSegment seg;

    protected HanLpCrfSegmenter() {
        if (seg == null) {
            seg = new CRFSegment();
            seg.enableNameRecognize(true).enableTranslatedNameRecognize(true).enableJapaneseNameRecognize(true)
                    .enablePlaceRecognize(true).enableOrganizationRecognize(true).enablePartOfSpeechTagging(true)
                    .enableOffset(true);
        }
    }

    @Override
    protected Segment getSegmenter() {
        return seg;
    }
}
