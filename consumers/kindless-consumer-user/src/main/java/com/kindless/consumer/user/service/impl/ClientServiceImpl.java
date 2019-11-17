package com.kindless.consumer.user.service.impl;

import com.kindless.provider.user.domain.Client;
import com.kindless.provider.user.repository.ClientRepository;
import com.kindless.consumer.user.service.ClientService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class ClientServiceImpl extends ServiceSupport<Client, Long> implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    protected QueryableRepository<Client, Long> getRepository() {
        return clientRepository;
    }

    @Override
    protected Class<Client> getDomainClass() {
        return clientRepository.getDomainClass();
    }

}
