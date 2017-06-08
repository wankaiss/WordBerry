package com.youbanban.wordberry.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.youbanban.wordberry.model.GitRepositoryState;
import com.youbanban.wordberry.utility.Constants;

@RestController
@RequestMapping(value ={"/" + Constants.VERSION + "/info"})
public class ServiceHomeController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;    
    
    @RequestMapping(value = {"/", ""}, method = { RequestMethod.GET })
    public List<String> home() {
        List<String> list = new ArrayList<String>();
        for ( RequestMappingInfo i : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
            list.add(i.toString());
        }
        return list;
    }
    
    @RequestMapping(value = {"/api_spec", "/api_spec/"}, method = { RequestMethod.GET })
    public List<Map<String, Object>> getApiInfo() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for ( RequestMappingInfo i : handlerMethods.keySet()) {
            String api = i.toString();
            HandlerMethod m = handlerMethods.get(i);
            String retType = m.getMethod().getReturnType().getSimpleName();
            Map<String, Object> retVal = new LinkedHashMap<String, Object>();
            retVal.put("api", api);
            retVal.put("return-type", retType);
            Class<?>[] mps = m.getMethod().getParameterTypes();
            List<String> methodParams = new ArrayList<String>();
            if (mps!=null && mps.length>0) {
                for (Class<?> mp : mps) {
                    methodParams.add(mp.getSimpleName());
                }
            }
            retVal.put("java-method", m.toString());
            retVal.put("parameters", methodParams);
            list.add(retVal);
        }
        return list;
    }

    @RequestMapping(value = {"/version", "/version/"}, method = { RequestMethod.GET })
    public final static Map<String, String> getAppInfo() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("api.version", Constants.VERSION);
        map.put("project.vesion", GitRepositoryState.getInstance().getBuildVersion());
        map.put("project.name", "YbbApp Service");
        map.put("project.build-git-id", GitRepositoryState.getInstance().getCommitId());
        map.put("project.build-time", GitRepositoryState.getInstance().getBuildTime());
        map.put("project.build-user", GitRepositoryState.getInstance().getBuildUserName());
        return map;
    }

    @RequestMapping(value = {"/gitVersion", "/gitVersion/"}, method = { RequestMethod.GET })
    public GitRepositoryState version() {
        return GitRepositoryState.getInstance();
    }
}
