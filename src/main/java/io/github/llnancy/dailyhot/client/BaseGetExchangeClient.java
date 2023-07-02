package io.github.llnancy.dailyhot.client;

import reactor.core.publisher.Mono;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
public interface BaseGetExchangeClient<R> {

    Mono<R> dailyHot();
}
