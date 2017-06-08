package com.youbanban.wordberry.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youbanban.wordberry.utility.Utility;
import com.youbanban.wordberry.utility.Constants;


@RestController
@RequestMapping(value = { "/" + Constants.VERSION + "/scanword" })
public class ScanWordsController {
	private static final Logger LOG = Logger.getLogger(ScanWordsController.class);
	@Value("${ybb.scanword-url}")
	private String scanWordUrl;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public String getScanWord(HttpServletRequest request, @RequestBody String content) throws UnsupportedEncodingException {
		LOG.debug("this method is getScanWord,this content is " + content);
		return Utility.requestPost(content,scanWordUrl);
	}
}
