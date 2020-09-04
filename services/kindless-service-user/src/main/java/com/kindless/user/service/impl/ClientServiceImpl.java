package com.kindless.user.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.user.Client;
import com.kindless.user.repository.ClientRepository;
import com.kindless.user.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@RequiredArgsConstructor
@Service
public class ClientServiceImpl extends ServiceSupport<Client, Long> implements ClientService {

    private final ClientRepository clientRepository;

}
