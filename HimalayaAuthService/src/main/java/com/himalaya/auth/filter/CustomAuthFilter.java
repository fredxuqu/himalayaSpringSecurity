package com.himalaya.auth.filter;

import com.alibaba.druid.util.StringUtils;
import com.himalaya.auth.constant.SdkConstant;
import com.himalaya.auth.domain.RoleDO;
import com.himalaya.auth.domain.UserDO;
import com.himalaya.auth.repository.RoleMapper;
import com.himalaya.auth.repository.UserMapper;
import com.himalaya.auth.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuqu on 2018/9/10.
 */
@Component
public class CustomAuthFilter extends GenericFilterBean {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomAuthFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.debug("CustomAuthFilter called!");

        // get http request
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        // get header parameters
        String appKey = String.class.cast(
                HttpServletRequest.class.cast(httpRequest).getHeader(SdkConstant.CLOUDAPI_X_CA_KEY));
        String sign = String.class.cast(
                HttpServletRequest.class.cast(httpRequest).getHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE));

        if(!StringUtils.isEmpty(appKey) && !StringUtils.isEmpty(sign)) {

            // get user by app key
            UserDO userDO = userMapper.selectUserByAppId(appKey);

            if (userDO != null) {

                // get header parameters
                String version = String.class.cast(
                        HttpServletRequest.class.cast(httpRequest).getHeader(SdkConstant.CLOUDAPI_X_CA_VERSION));
                String timestamp = String.class.cast(
                        HttpServletRequest.class.cast(httpRequest).getHeader(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP));

                Map<String, String> headerParams = new HashMap<>();
                if (!StringUtils.isEmpty(appKey)) {
                    headerParams.put(SdkConstant.CLOUDAPI_X_CA_KEY, appKey);
                }
                if (!StringUtils.isEmpty(version)) {
                    headerParams.put(SdkConstant.CLOUDAPI_X_CA_VERSION, version);
                }
                if (!StringUtils.isEmpty(timestamp)) {
                    headerParams.put(SdkConstant.CLOUDAPI_X_CA_TIMESTAMP, timestamp);
                }
                LOGGER.debug("Params of Header : " + headerParams);

                // get method (POST/GET)
                String requestMethod = httpRequest.getMethod();

                // get url without parameters
                String queryURL = httpRequest.getRequestURL().toString();

                // get all query parameters and value
                // Map<String, String> queryParams = getParameterMap(httpRequest);

                // get all attributes
                // Map<String, String> attributeMap = getAttributeMap(httpRequest);

                // verify signature
                LOGGER.debug("Generate signature...");
                String toBeVerifiedSign = SignUtil.sign(requestMethod, userDO.getAppSecret(), headerParams, queryURL);
                LOGGER.debug("To be verified sign : " + toBeVerifiedSign);
                if (sign.equals(toBeVerifiedSign)) {
                    // get role by app key
                    List<RoleDO> roleList = roleMapper.listRoleByAppId(appKey);
                    String[] roles = new String[roleList.size()];
                    int index = 0;
                    for (RoleDO role : roleList) {
                        roles[index++] = role.getRoleName();
                    }
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDO.getAppId(), userDO.getAppSecret(),
                            AuthorityUtils.createAuthorityList(roles));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                LOGGER.info("AppKey [{}] is not exists in system!", appKey);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
    private Map<String, String> getParameterMap(HttpServletRequest httpRequest){
        Map<String, String> queryParams = new HashMap<>();
        Enumeration<String> parameterNames = httpRequest.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String paramKey = parameterNames.nextElement();
            queryParams.put(paramKey, httpRequest.getParameter(paramKey));
        }
        return queryParams;
    }

    private Map<String, String> getAttributeMap(HttpServletRequest httpRequest) {
        Map<String, String> paramMap = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
            String content = IOUtils.read(reader);
            if(StringUtils.isEmpty(content)){
                return null;
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Content : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);

            // Convert JSON Object to Map
            Map<String,Object> jsonMap = (Map<String,Object>)jsonObject;
            if(jsonMap.isEmpty()){
                return null;
            }

            // Convert Object value to String value.
            paramMap = new HashMap<>();
            Iterator<String> iterator = jsonMap.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                paramMap.put(key, String.valueOf(jsonMap.get(key)));
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Content Map : " + paramMap);
            }
        } catch(Exception e){
            LOGGER.error("Get request body failed " + e.getMessage());
        }
        return paramMap;
    }*/
}