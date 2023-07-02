package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.NewsQQExchangeClient.NewsQQResponse;
import io.github.llnancy.dailyhot.entity.NewsQQData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * news-qq daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class NewsQQGetDailyHotService extends AbstractGetDailyHotService<NewsQQResponse, NewsQQData> {

    @Override
    public Flux<NewsQQData> doDailyHot(BaseGetExchangeClient<NewsQQResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(newsQQResponse -> newsQQResponse.getIdlist().get(0).getNewslist())
                .skip(1L)
                .map(item -> {
                    String id = item.getId();
                    return NewsQQData.builder()
                            .id(id)
                            .title(item.getTitle())
                            .desc(item.get_abstract())
                            .descSm(item.getNlpAbstract())
                            .hot(item.getReadCount())
                            .pic(item.getMiniProShareImage())
                            .url("https://new.qq.com/rain/a/" + id)
                            .mobileUrl("https://view.inews.qq.com/a/" + id)
                            .build();
                });
    }
}
