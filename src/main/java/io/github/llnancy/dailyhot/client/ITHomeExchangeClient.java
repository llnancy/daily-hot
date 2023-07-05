package io.github.llnancy.dailyhot.client;

import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * IT 之家
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://m.ithome.com")
public interface ITHomeExchangeClient extends BaseGetExchangeClient<String> {

    @GetExchange("/rankm/")
    @Override
    Mono<String> dailyHot();
}
