package com.smoms.service.impl;

import com.smoms.mapper.ProviderMapper;
import com.smoms.pojo.Provider;
import com.smoms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> findAllProivders() {
        return providerMapper.selAllProvider();
    }

    @Override
    public Provider findProviderById(int ProviderId) {
        return providerMapper.selProviderById(ProviderId);
    }

    @Override
    public int addProvider(Provider provider) {
        return providerMapper.insProvider(provider);
    }

    @Override
    public List<Provider> findProviderByTerm(String proCode, String proName) {
        return providerMapper.selProviderByTerm(proCode,proName);
    }

    @Override
    public boolean reomveProviderById(Integer id) {
        return providerMapper.delProviderById(id);
    }

    @Override
    public int modifyProviderById(Provider pro, int proId) {
        return providerMapper.updProviderById(pro,proId);
    }
}
