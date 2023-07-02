package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.TieBaExchangeClient.TieBaResponse;
import io.github.llnancy.dailyhot.entity.TieBaData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * tie-ba daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class TieBaGetDailyHotService extends AbstractGetDailyHotService<TieBaResponse, TieBaData> {

    @Override
    public Flux<TieBaData> doDailyHot(BaseGetExchangeClient<TieBaResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(tieBaResponse -> tieBaResponse.getData().getBangTopic().getTopicList())
                .map(item -> {
                    String topicUrl = item.getTopicUrl();
                    return TieBaData.builder()
                            .id(item.getTopicId())
                            .title(item.getTopicName())
                            .desc(item.getTopicDesc())
                            .pic(item.getTopicPic())
                            .hot(item.getDiscussNum())
                            .url(topicUrl)
                            .mobileUrl(topicUrl)
                            .build();
                });
    }
}
