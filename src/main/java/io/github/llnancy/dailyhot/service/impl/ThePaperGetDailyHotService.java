package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.ThePaperExchangeClient.ThePaperResponse;
import io.github.llnancy.dailyhot.entity.ThePaperData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * the-paper daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class ThePaperGetDailyHotService extends AbstractGetDailyHotService<ThePaperResponse, ThePaperData> {

    @Override
    public Flux<ThePaperData> doDailyHot(BaseGetExchangeClient<ThePaperResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(thePaperResponse -> thePaperResponse.getData().getHotNews())
                .map(item -> {
                    String contId = item.getContId();
                    return ThePaperData.builder()
                            .id(contId)
                            .title(item.getName())
                            .pic(item.getPic())
                            .hot(item.getPraiseTimes())
                            .time(item.getPubTime())
                            .url("https://www.thepaper.cn/newsDetail_forward_" + contId)
                            .mobileUrl("https://m.thepaper.cn/newsDetail_forward_" + contId)
                            .build();
                });
    }
}
