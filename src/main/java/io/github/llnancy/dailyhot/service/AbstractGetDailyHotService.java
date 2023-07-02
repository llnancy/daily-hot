package io.github.llnancy.dailyhot.service;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.enums.PlatformEnum;
import io.github.llnancy.dailyhot.factory.ExchangeClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

/**
 * abstract get method daily hot service impl
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
public abstract class AbstractGetDailyHotService<R, D> implements DailyHotService<D> {

    @Autowired
    private ExchangeClientFactory clientFactory;

    @Override
    public Flux<D> dailyHot(PlatformEnum platform) {
        BaseGetExchangeClient<R> exchangeClient = clientFactory.fetchGetExchangeClient(platform);
        return doDailyHot(exchangeClient);
    }

    protected abstract Flux<D> doDailyHot(BaseGetExchangeClient<R> exchangeClient);
}
