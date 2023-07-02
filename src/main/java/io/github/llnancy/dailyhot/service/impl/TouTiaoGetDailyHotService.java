package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.TouTiaoExchangeClient.TouTiaoResponse;
import io.github.llnancy.dailyhot.entity.TouTiaoData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * tou-tiao daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class TouTiaoGetDailyHotService extends AbstractGetDailyHotService<TouTiaoResponse, TouTiaoData> {

    @Override
    public Flux<TouTiaoData> doDailyHot(BaseGetExchangeClient<TouTiaoResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(TouTiaoResponse::getData)
                .map(item -> {
                    String clusterId = item.getClusterId();
                    return TouTiaoData.builder()
                            .id(item.getClusterId())
                            .title(item.getTitle())
                            .pic(item.getImage().getUrl())
                            .hot(item.getHotValue())
                            .url("https://www.toutiao.com/trending/" + clusterId)
                            .mobileUrl("https://api.toutiaoapi.com/feoffline/amos_land/new/html/main/index.html?topic_id=" + clusterId)
                            .build();
                });
    }
}
