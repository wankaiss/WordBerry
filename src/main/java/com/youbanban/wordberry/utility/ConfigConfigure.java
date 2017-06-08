package com.youbanban.wordberry.utility;

import com.hankcs.hanlp.seg.Segment;

/**
 * @author Gerald_Yang
 */
public class ConfigConfigure {

    public static final Segment configure(Segment segment, String number) {
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if ('0' == c) {
                segment.enableIndexMode(true); // 是否是索引分词（合理地最小分割）
            } else if ('1' == c) {
                segment.enableJapaneseNameRecognize(true); // 是否识别日本人名
            } else if ('2' == c) {
                segment.enablePlaceRecognize(true); // 是否识别地名
            } else if ('3' == c) {
                segment.enableOrganizationRecognize(true); // 是否识别机构
            } else if ('4' == c) {
                segment.enablePartOfSpeechTagging(true); // 词性标注
            } else if ('5' == c) {
                segment.enableNumberQuantifierRecognize(true); // 是否识别数字和量词
            } else if ('6' == c) {
                segment.enableOffset(true); // 是否计算偏移量
            }
        }
        return segment;
    }
}
