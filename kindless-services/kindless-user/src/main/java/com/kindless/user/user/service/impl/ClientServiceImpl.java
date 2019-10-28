package com.kindless.user.user.service.impl;

import com.kindless.apis.domain.core.Client;
import com.kindless.user.user.repository.ClientRepository;
import com.kindless.user.user.service.ClientService;
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
