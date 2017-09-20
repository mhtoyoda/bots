package com.fiveware.security.token;

import com.fiveware.security.model.IcaptorUserDetail;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;


public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        IcaptorUserDetail userSystem = (IcaptorUserDetail) authentication.getPrincipal();

        Map<String, Object> addInfo = new HashMap<>();
        addInfo.put("name", userSystem.getDisplayName());
        addInfo.put("idUser", userSystem.getUserId());


        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
        return accessToken;
    }

}