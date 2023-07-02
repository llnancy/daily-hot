package io.github.llnancy.dailyhot.client;

import io.github.llnancy.httpexchange.core.ExchangeClient;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * baidu
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@ExchangeClient(baseUrl = "https://top.baidu.com")
public interface BaiduExchangeClient extends BaseGetExchangeClient<String> {

    @GetExchange(url = "/board?tab=realtime")
    @Override
    Mono<String> dailyHot();
}
