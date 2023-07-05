package io.github.llnancy.dailyhot.client;

import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * 知乎
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(
        baseUrl = "https://www.zhihu.com",
        defaultHeaderKey = "User-Agent",
        defaultHeaderValues = {"Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1"}
)
public interface ZhiHuExchangeClient extends BaseGetExchangeClient<String> {

    @GetExchange("/hot")
    @Override
    Mono<String> dailyHot();
}
