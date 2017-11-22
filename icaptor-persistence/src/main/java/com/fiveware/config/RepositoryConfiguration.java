package com.fiveware.config;

import com.fiveware.model.*;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.Parameters;
import org.springframework.context.annotation.Bean;
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
        config.exposeIdsFor(Task.class);
        config.exposeIdsFor(ItemTask.class);
        config.exposeIdsFor(Agent.class);
        config.exposeIdsFor(Server.class);
        config.exposeIdsFor(Parameter.class);
        config.exposeIdsFor(ItemTask.class);
        config.exposeIdsFor(AgentParameter.class);
        config.exposeIdsFor(StatuProcessTask.class);
        config.exposeIdsFor(StatuProcessItemTask.class);
        config.exposeIdsFor(RecentActivity.class);
        config.exposeIdsFor(IcaptorUser.class);
        config.exposeIdsFor(BotFormatter.class);
        config.exposeIdsFor(TaskFile.class);
    }

    @Bean
    public Parameters parameters(){
        return new Parameters();
    }



}
