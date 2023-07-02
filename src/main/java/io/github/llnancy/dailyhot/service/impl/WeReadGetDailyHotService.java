package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.WeReadExchangeClient;
import io.github.llnancy.dailyhot.client.WeReadExchangeClient.WeReadResponse;
import io.github.llnancy.dailyhot.entity.WeReadData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * we-read daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class WeReadGetDailyHotService extends AbstractGetDailyHotService<WeReadResponse, WeReadData> {

    @Override
    public Flux<WeReadData> doDailyHot(BaseGetExchangeClient<WeReadResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(WeReadResponse::getBooks)
                .map(item -> {
                    WeReadExchangeClient.BookInfo bookInfo = item.getBookInfo();
                    return WeReadData.builder()
                            .id(bookInfo.getBookId())
                            .title(bookInfo.getTitle())
                            .desc(bookInfo.getIntro())
                            .pic(bookInfo.getCover())
                            .hot(item.getReadingCount())
                            .build();
                });
    }
}
