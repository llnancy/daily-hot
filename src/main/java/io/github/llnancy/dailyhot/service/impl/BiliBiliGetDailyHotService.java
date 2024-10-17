package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.BiliBiliExchangeClient.BiliBiliRespData;
import io.github.llnancy.dailyhot.client.BiliBiliExchangeClient.BiliBiliResponse;
import io.github.llnancy.dailyhot.entity.BiliBiliData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Optional;

/**
 * bili bili daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class BiliBiliGetDailyHotService extends AbstractGetDailyHotService<BiliBiliResponse, BiliBiliData> {

    @Override
    public Flux<BiliBiliData> doDailyHot(BaseGetExchangeClient<BiliBiliResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(biliBiliResponse -> {
                    BiliBiliRespData data = biliBiliResponse.getData();
                    return Optional.ofNullable(biliBiliResponse.getData())
                            .map(BiliBiliRespData::getList)
                            .orElse(Collections.emptyList());
                })
                .map(item -> {
                    String id = item.getBvid();
                    return BiliBiliData.builder()
                            .id(id)
                            .title(item.getTitle())
                            .desc(item.getDesc())
                            .pic(item.getPic().replaceFirst("http:", "https:"))
                            .owner(item.getOwner().getName())
                            .hot(item.getStat().getView())
                            .url(Optional.ofNullable(item.getShortLinkV2()).orElse("https://b23.tv/" + id))
                            .mobileUrl("https://m.bilibili.com/video/" + id)
                            .build();
                });
    }
}
