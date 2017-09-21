package com.fiveware.config;

import com.fiveware.model.Bot;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(ScopeParameter.class);
        config.exposeIdsFor(TypeParameter.class);
        config.exposeIdsFor(Bot.class);

    }
}
