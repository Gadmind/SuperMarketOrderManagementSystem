package com.smoms.service;

import com.smoms.pojo.Provider;

import java.util.List;

public interface ProviderService {
    List<Provider> findAllProivders();

    Provider findProviderById(int ProviderId);

    int addProvider(Provider provider);

    List<Provider> findProviderByTerm(String proCode, String proName);

    boolean reomveProviderById(Integer id);

    int modifyProviderById(Provider pro, int proId);
}
