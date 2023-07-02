package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.WeiboExchangeClient.WeiboResponse;
import io.github.llnancy.dailyhot.entity.WeiboData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * weibo daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class WeiboGetDailyHotService extends AbstractGetDailyHotService<WeiboResponse, WeiboData> {

    @Override
    public Flux<WeiboData> doDailyHot(BaseGetExchangeClient<WeiboResponse> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapIterable(weiboResponse -> weiboResponse.getData().getRealtime())
                .map(item -> {
                    String key = Optional.ofNullable(item.getWordScheme()).orElseGet(() -> "#" + item.getWord());
                    String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);
                    String url = "https://s.weibo.com/weibo?q=" + encodedKey + "&t=31&band_rank=1&Refer=top";
                    return WeiboData.builder()
                            .title(item.getWord())
                            .desc(key)
                            .hot(item.getRawHot())
                            .url(url)
                            .mobileUrl(url)
                            .build();
                });
    }
}
