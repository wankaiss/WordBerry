package com.youbanban.wordberry.web;

import com.youbanban.wordberry.dao.ScanWordsDao;
import com.youbanban.wordberry.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gerald_Yang.
 */
@RestController
@RequestMapping(value = "/" + Constants.VERSION + "/sensitiveword/", method = RequestMethod.POST)
public class SensitiveWordController {

    @Autowired
    private ScanWordsDao scanWordsDao;

    @RequestMapping(value = "scan4exist")
    public String scan4exist(@RequestBody String keyword) {
        if (keyword != null) {
            boolean scan = scanWordsDao.scan(keyword);
            if (scan) {
                return "exist: true";
            } else {
                return "exist: false";
            }
        } /*else if (sensitiveword != null) {
            boolean scan = scanWordsDao.scan(sensitiveword);
            if (scan) {
                return "exist: true";
            } else {
                return "exist: false";
            }
        }*/
        return "扫描不存在/sensitiveword doesn't exist!";
    }

    @RequestMapping(value = "scan4text")
    public List<String> scan4text(@RequestBody String keyword) {
        List<String> strings = new ArrayList<>();
        if (keyword != null) {
            return scanWordsDao.scan_4(keyword);
        }/* else if (sensitiveword.equals(keyword)) {
            return scanWordsDao.scan_4(keyword);
        }*/
        strings.add("扫描不存在/sensitiveword doesn't exist!");
        return strings;
    }
}
