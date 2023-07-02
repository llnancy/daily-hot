package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BasePostExchangeClient;
import io.github.llnancy.dailyhot.client.Kr36ExchangeClient.Kr36Response;
import io.github.llnancy.dailyhot.client.Kr36ExchangeClient.TemplateMaterial;
import io.github.llnancy.dailyhot.entity.Kr36Data;
import io.github.llnancy.dailyhot.service.AbstractPostDailyHotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;

/**
 * kr36 daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
@RequiredArgsConstructor
public class Kr36DailyHotService extends AbstractPostDailyHotService<String, Kr36Response, Kr36Data> {

    @Override
    protected Flux<Kr36Data> doDailyHot(BasePostExchangeClient<String, Kr36Response> exchangeClient) {
        String params = """
                {
                    "partner_id": "wap",
                    "param": {
                        "siteId": 1,
                        "platformId": 2
                    },
                    "timestamp": "%s"
                }
                """
                .formatted(Instant.now().toEpochMilli());
        return exchangeClient.dailyHot(params)
                .flatMapIterable(kr36Response -> kr36Response.getData().getHotRankList())
                .map(hotRank -> {
                    Long itemId = hotRank.getItemId();
                    TemplateMaterial tm = hotRank.getTemplateMaterial();
                    String url = "https://www.36kr.com/p/" + itemId;
                    return Kr36Data.builder()
                            .id(itemId)
                            .title(tm.getWidgetTitle())
                            .pic(tm.getWidgetImage())
                            .owner(tm.getAuthorName())
                            .hot(tm.getStatRead())
                            .data(tm)
                            .url(url)
                            .mobileUrl(url)
                            .build();
                });
    }
}
