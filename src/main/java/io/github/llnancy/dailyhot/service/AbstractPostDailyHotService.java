package io.github.llnancy.dailyhot.service;

import io.github.llnancy.dailyhot.client.BasePostExchangeClient;
import io.github.llnancy.dailyhot.enums.PlatformEnum;
import io.github.llnancy.dailyhot.factory.ExchangeClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

/**
 * abstract post method daily hot service impl
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
public abstract class AbstractPostDailyHotService<BODY, R, D> implements DailyHotService<D> {

    @Autowired
    private ExchangeClientFactory clientFactory;

    @Override
    public Flux<D> dailyHot(PlatformEnum platform) {
        BasePostExchangeClient<BODY, R> exchangeClient = clientFactory.fetchPostExchangeClient(platform);
        return doDailyHot(exchangeClient);
    }

    protected abstract Flux<D> doDailyHot(BasePostExchangeClient<BODY, R> exchangeClient);
}
