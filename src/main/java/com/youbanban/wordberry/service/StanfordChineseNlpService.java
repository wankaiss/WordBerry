package com.youbanban.wordberry.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gerald_Yang.
 */
@Service
public class StanfordChineseNlpService {
    private List<String> set;

    public StanfordChineseNlpService() {
        set = new ArrayList<>();
    }

    public static void main(String[] args) {

    }


    public List<String> runChineseAnnotators(String text) {

//        String text = IOUtils.slurpFileNoExceptions("segtest.txt");
        Annotation document = new Annotation(text);
        StanfordCoreNLP coreNLP = new StanfordCoreNLP("StanfordCoreNLP-chinese-test");
        coreNLP.annotate(document);
        return parserOutput(document);
    }

    /**
     * @param document 这是一个annotation的接口
     */
    private List<String> parserOutput(Annotation document) {
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

//        System.out.println("word\t" + "pos\t" + "ner\t");
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                if (word != null) set.add(word);

            }

            //Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            //System.out.println("SpeechTree:");
            //System.out.println(tree.toString());
        }

        //Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
        //System.out.println("graph:");
        //System.out.println(graph.toString());
        return set;
    }

}
