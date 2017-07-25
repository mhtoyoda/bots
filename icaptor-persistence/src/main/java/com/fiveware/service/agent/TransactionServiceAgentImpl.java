package com.fiveware.service.agent;

import com.fiveware.model.Agent;
import com.fiveware.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by valdisnei on 13/07/17.
 */
@Service
@Transactional
class TransactionServiceAgentImpl{

    @Autowired()
    private AgentRepository agentRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    protected Agent saveAgent(Agent agent) {
        agentRepository.save(agent);
        return agent;
    }

}
