package io.github.llnancy.dailyhot.service;

import io.github.llnancy.dailyhot.enums.PlatformEnum;
import reactor.core.publisher.Flux;

/**
 * daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
public interface DailyHotService<D> {

    Flux<D> dailyHot(PlatformEnum platform);
}
