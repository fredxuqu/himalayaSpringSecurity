package com.himalaya.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.himalaya.auth.service.impl.SDKSecurityMetadataSourceService;


/**
 * Created by xuqu on 2018/9/13.
 */
@Component
public class SDKFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private SDKSecurityMetadataSourceService sdkSecurityMetadataSourceService;

    @Autowired
    public void setMyAccessDecisionManager(SDKAccessDecisionManager sdkAccessDecisionManager) {
        super.setAccessDecisionManager(sdkAccessDecisionManager);
    }

//    private static final Logger LOGGRE = LoggerFactory.getLogger(SDKFilterSecurityInterceptor.class);

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return sdkSecurityMetadataSourceService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
        //里面调用SDKSecurityMetadataSourceService的getAttributes(Object object)这个方法获取fi对应的所有权限
        //再调用SDKAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
    }
}
