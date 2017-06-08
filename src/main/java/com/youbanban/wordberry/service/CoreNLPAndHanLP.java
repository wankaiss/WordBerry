package com.youbanban.wordberry.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IIOAdapter;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.corpus.io.ResourceIOAdapter;
import com.hankcs.hanlp.utility.TextUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Gerald_Yang.<br>
 *         通过config将hanLP和coreLP整合起来
 */
public class CoreNLPAndHanLP {

    public static Logger logger = Logger.getLogger("CoreNLPAndHanLP");

    static {
        logger.setLevel(Level.WARNING);
    }


    public static final class Configure {
        /**
         * 开发模式
         */
        public static boolean DEBUG = false;
        /**
         * hanLP核心词典加载路径
         */
        public static String HanLPCoreDictionaryPath = "data.dictionary.CoreNatureDictionary.txt";
        /**
         * 二元语法词典路径
         */
        public static String HanLPBiGramDictionaryPath = "data.dictionary.CoreNatureDictionary.ngram.txt";
        /**
         * 停用词词典路径
         */
        public static String HanLPCoreStopWordDictionaryPath = "data.dictionary.stopwords.txt";
        /**
         * 同义词词典路径
         */
        public static String HanLPCoreSynonymDictionaryDictionaryPath = "data/dictionary/synonym/CoreSynonym.txt";
        /**
         * 人名词典路径
         */
        public static String HanLPPersonDictionaryPath = "data.dictionary.person.nr.txt";
        /**
         * 人名词典转移矩阵路径
         */
        public static String HanLPPersonDictionaryTrPath = "data/dictionary/person/nr.tr.txt";
        /**
         * 繁简词典路径
         */
        public static String HanLPTraditionalChineseDictionaryPath = "data/dictionary/tc/TraditionalChinese.txt";
        /**
         * CRF分词模型
         */
        public static String HanLPCRFSegmentModelPath = "data/model/segment/CRFSegmentModel.txt";
        /**
         * HMM分词模型
         */
        public static String HanLPHMMSegmentModelPath = "data/model/segment/HMMSegmentModel.bin";
        /**
         * 用户自定义词典路径
         */
        public static String HanLPCustomDictionaryPath[] = new String[]{"data/dictionary/custom/CustomDictionary.txt"};

        /**
         * 带有CoreNLP的结均为CoreNLP分词
         */
        public static String CoreNLPpkumodel = "edu/stanford/nlp/models/segmenter/chinese/pku.gz";
        public static String CoreNLPctbmodel = "edu/stanford/nlp/models/segmenter/chinese/ctb.gz";
        public static String CoreNLPsighanCorporaDict = "edu/stanford/nlp/models/segmenter/chinese";
        public static String[] CoreNLPserDictionary = {"edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz"};
        public static String CoreNLPsighanPostProcessing = "true";
        public static String CoreNLPinputEncoding = "UTF-8";
        public static String CoreNLPkeepAllWhitespaces = "false";
        public static String CoreNLPAnnotators = "tokenize, ssplit, pos, lemma, ner, parse, dcoref";

        /**
         * IO适配器（默认null，表示从本地文件系统读取），实现com.hankcs.hanlp.corpus.io.IIOAdapter接口
         * 以在不同的平台（Hadoop、Redis等）上运行HanLP
         */
        public static IIOAdapter IOAdapter = new ResourceIOAdapter();

        static {
            // 自动读取配置
            Properties p = new Properties() {
                String root;

                @Override
                public String getProperty(String key, String defaultValue) {
                    // 自带文件是否存在的校验逻辑，如果value是路径则校验它
                    if (root == null) {
                        root = getProperty("root");
                        if (!root.endsWith("/")) {
                            root += "/";
                        }
                        if (!IOUtil.isFileExists(root + "data")) {
                            logger.warning("root=" + root + "这个目录下没有data");
                        }
                    }
                    if ("root".equals(key)) {
                        return root;
                    }
                    String property = getProperty(key);
                    if (property == null) {
                        return defaultValue;
                    }
                    if (property.startsWith("data") && !"HanLPCustomDictionaryPath".equals(key)) {
                        String path = root + property;
                        // 判断文件是否存在并返回path
                        if (IOUtil.isFileExists(path) || IOUtil.isFileExists(path + Predefine.BIN_EXT)) {
                            return path;
                        }
                        return property;
                    }
                    return property;
                }
            };

            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                if (loader == null) {
                    loader = CoreNLPAndHanLP.Configure.class.getClassLoader();
                }
                p.load(new InputStreamReader(Predefine.CORE_HANLP_CORENLP_PATH == null ? loader.getResourceAsStream("hanlpandcorenlp.properties") : new FileInputStream(Predefine.CORE_HANLP_CORENLP_PATH), "UTF-8"));
                String root = p.getProperty("root", "").replaceAll("\\\\", "/");
                HanLPCoreDictionaryPath = p.getProperty("HanLPCoreDictionaryPath", HanLPCoreDictionaryPath);
                HanLP.Config.CoreDictionaryPath = HanLPCoreDictionaryPath;
                HanLPBiGramDictionaryPath = p.getProperty("HanLPBiGramDictionaryPath", HanLPBiGramDictionaryPath);
                HanLP.Config.BiGramDictionaryPath = HanLPBiGramDictionaryPath;
                HanLPCoreStopWordDictionaryPath = p.getProperty("HanLPCoreStopWordDictionaryPath", HanLPCoreStopWordDictionaryPath);
                HanLP.Config.CoreStopWordDictionaryPath = HanLPCoreStopWordDictionaryPath;
                HanLPCoreSynonymDictionaryDictionaryPath = p.getProperty("HanLPCoreSynonymDictionaryDictionaryPath", HanLPCoreSynonymDictionaryDictionaryPath);
                HanLP.Config.CoreSynonymDictionaryDictionaryPath = HanLPCoreSynonymDictionaryDictionaryPath;
                HanLPPersonDictionaryPath = p.getProperty("HanLPPersonDictionaryPath", HanLPPersonDictionaryPath);
                HanLP.Config.PersonDictionaryPath = HanLPPersonDictionaryPath;
                HanLPTraditionalChineseDictionaryPath = p.getProperty("HanLPTraditionalChineseDictionaryPath", HanLPTraditionalChineseDictionaryPath);
                HanLP.Config.TranslatedPersonDictionaryPath = HanLPTraditionalChineseDictionaryPath;
                HanLPCRFSegmentModelPath = p.getProperty("HanLPCRFSegmentModelPath", HanLPCRFSegmentModelPath);
                HanLP.Config.CRFSegmentModelPath = HanLPCRFSegmentModelPath;
                HanLPHMMSegmentModelPath = p.getProperty("HanLPHMMSegmentModelPath", HanLPHMMSegmentModelPath);
                HanLP.Config.HMMSegmentModelPath = HanLPHMMSegmentModelPath;
                String[] pathArray = p.getProperty("HanLPCustomDictionaryPath", "dictionary/custom/CustomDictionary.txt").split(";");
                String[] processPathArray = processArray(pathArray, root);
                HanLPCustomDictionaryPath = processPathArray;
                HanLP.Config.CustomDictionaryPath = HanLPCustomDictionaryPath;
                HanLPPersonDictionaryTrPath = p.getProperty("HanLPPersonDictionaryTrPath", HanLPPersonDictionaryTrPath);
                HanLP.Config.PersonDictionaryTrPath = HanLPPersonDictionaryTrPath;
                CoreNLPpkumodel = p.getProperty("CoreNLPpkumodel", CoreNLPpkumodel);
                CoreNLPctbmodel = p.getProperty("CoreNLPctbmodel", CoreNLPctbmodel);
                CoreNLPsighanCorporaDict = p.getProperty("CoreNLPsighanCorporaDict", CoreNLPsighanCorporaDict);
                String[] nlPserDictionaries = p.getProperty("CoreNLPserDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz,dict.txt").split(";");
                String[] processArray = processArray(nlPserDictionaries, root);
                CoreNLPserDictionary = processArray;
                CoreNLPsighanPostProcessing = p.getProperty("CoreNLPsighanPostProcessing", CoreNLPsighanPostProcessing);
                CoreNLPinputEncoding = p.getProperty("CoreNLPinputEncoding", CoreNLPinputEncoding);
                CoreNLPkeepAllWhitespaces = p.getProperty("CoreNLPkeepAllWhitespaces", CoreNLPkeepAllWhitespaces);
                CoreNLPAnnotators = p.getProperty("CoreNLPAnnotators", CoreNLPAnnotators);
                String ioAdapterClassName = p.getProperty("IOAdapter");
                if (ioAdapterClassName != null) {
                    try {
                        Class<?> clazz = Class.forName(ioAdapterClassName);
                        Constructor<?> ctor = clazz.getConstructor();
                        Object instance = ctor.newInstance();
                        if (instance != null) IOAdapter = (IIOAdapter) instance;
                    } catch (ClassNotFoundException e) {
                        logger.warning(String.format("找不到IO适配器类： %s ，请检查第三方插件jar包", ioAdapterClassName));
                    } catch (NoSuchMethodException e) {
                        logger.warning(String.format("工厂类[%s]没有默认构造方法，不符合要求", ioAdapterClassName));
                    } catch (SecurityException e) {
                        logger.warning(String.format("工厂类[%s]默认构造方法无法访问，不符合要求", ioAdapterClassName));
                    } catch (Exception e) {
                        logger.warning(String.format("工厂类[%s]构造失败：%s\n", ioAdapterClassName, TextUtility.exceptionToString(e)));
                    }
                }
            } catch (Exception e) {
                StringBuilder sbInfo = new StringBuilder("========Tips========\n请将HanLP.properties放在下列目录：\n"); // 打印一些友好的tips
                String classPath = (String) System.getProperties().get("java.class.path");
                if (classPath != null) {
                    for (String path : classPath.split(File.pathSeparator)) {
                        if (new File(path).isDirectory()) {
                            sbInfo.append(path).append('\n');
                        }
                    }
                }
                sbInfo.append("Web项目则请放到下列目录：\n" +
                        "Webapp/WEB-INF/lib\n" +
                        "Webapp/WEB-INF/classes\n" +
                        "Appserver/lib\n" +
                        "JRE/lib\n" +
                        "resources/\n");
                sbInfo.append("并且编辑root=PARENT/path/to/your/data\n");
                sbInfo.append("现在NLP将尝试从jar包内部resource读取data……");
                logger.info("hanlpandcorenlp.properties，进入portable模式。若需要自定义NLP，请按下列提示操作：\n" + sbInfo);
            }
        }

        private static String[] processArray(String[] pathArray, String root) {
            String prePath = root;
            for (int i = 0; i < pathArray.length; ++i) {
                if (pathArray[i].startsWith(" ")) {
                    pathArray[i] = prePath + pathArray[i].trim();
                } else {
                    pathArray[i] = root + pathArray[i];
                    int lastSplash = pathArray[i].lastIndexOf('/');
                    if (lastSplash != -1) {
                        prePath = pathArray[i].substring(0, lastSplash + 1);
                    }
                }
            }
            return pathArray;
        }

        /**
         * 开启调试模式(会降低性能)
         */
        public static void enableDebug() {
            enableDebug(true);
        }

        /**
         * 开启调试模式(会降低性能)
         *
         * @param enable
         */
        public static void enableDebug(boolean enable) {
            DEBUG = enable;
            if (DEBUG) {
                logger.setLevel(Level.ALL);
            } else {
                logger.setLevel(Level.OFF);
            }
        }

    }

    private CoreNLPAndHanLP() {

    }
}
