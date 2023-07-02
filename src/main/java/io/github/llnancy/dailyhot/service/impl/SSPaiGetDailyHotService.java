package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.SSPaiExchangeClient.SSPaiResponse;
import io.github.llnancy.dailyhot.entity.SSPaiData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * SSPai daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class SSPaiGetDailyHotService extends AbstractGetDailyHotService<SSPaiResponse, SSPaiData> {

    @Override
    public Flux<SSPaiData> doDailyHot(BaseGetExchangeClient<SSPaiResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(SSPaiResponse::getData)
                .map(item -> {
                    Integer id = item.getId();
                    String url = "https://sspai.com/post/" + id;
                    return SSPaiData.builder()
                            .id(id)
                            .title(item.getTitle())
                            .desc(item.getSummary())
                            .pic("https://cdn.sspai.com/" + item.getBanner())
                            .owner(item.getAuthor().getNickname())
                            .hot(item.getLikeCount())
                            .url(url)
                            .mobileUrl(url)
                            .build();
                });
    }
}
