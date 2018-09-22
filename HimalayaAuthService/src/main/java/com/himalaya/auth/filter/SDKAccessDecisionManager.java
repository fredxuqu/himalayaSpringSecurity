package com.himalaya.auth.filter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Created by xuqu on 2018/9/14.
 */
@Component
public class SDKAccessDecisionManager implements AccessDecisionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDKAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
    	LOGGER.debug("SDKAccessDecisionManager called!");
    	
    	if(configAttributes==null || configAttributes.isEmpty()){
            return;
        }
        for (ConfigAttribute connfigAttribute : configAttributes) {
            String needRoles = connfigAttribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()){
                if (needRoles.trim().equals(authority.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("Access Deny, No rights!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {

        // NOTE : supports method should be return true, otherwise system will raise
        // "AccessDecisionManager does not support secure object class: class org.springframework.security.web.FilterInvocation"
        // exception when start spring container.
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        // NOTE : supports method should be return true, otherwise system will raise
        // "AccessDecisionManager does not support secure object class: class org.springframework.security.web.FilterInvocation"
        // exception when start spring container.
        return true;
    }
}
