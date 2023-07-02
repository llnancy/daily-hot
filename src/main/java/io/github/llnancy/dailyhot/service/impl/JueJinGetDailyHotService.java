package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.JueJinExchangeClient;
import io.github.llnancy.dailyhot.client.JueJinExchangeClient.JueJinResponse;
import io.github.llnancy.dailyhot.entity.JueJinData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


/**
 * jue jin daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class JueJinGetDailyHotService extends AbstractGetDailyHotService<JueJinResponse, JueJinData> {

    @Override
    public Flux<JueJinData> doDailyHot(BaseGetExchangeClient<JueJinResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(JueJinResponse::getData)
                .map(item -> {
                    JueJinExchangeClient.Content content = item.getContent();
                    String contentId = content.getContentId();
                    String url = "https://juejin.cn/post/" + contentId;
                    return JueJinData.builder()
                            .id(contentId)
                            .title(content.getTitle())
                            .hot(item.getContentCounter().getHotRank())
                            .url(url)
                            .mobileUrl(url)
                            .build();
                });
    }
}
