package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.WeiboExchangeClient.WeiboResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 微博
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://weibo.com")
public interface WeiboExchangeClient extends BaseGetExchangeClient<WeiboResponse> {

    @GetExchange("/ajax/side/hotSearch")
    @Override
    Mono<WeiboResponse> dailyHot();

    @Data
    class WeiboResponse {

        private WeiboRespData data;
    }

    @Data
    class WeiboRespData {

        private List<RealTime> realtime;
    }

    @Data
    class RealTime {

        @JsonProperty("word_scheme")
        private String wordScheme;

        private String word;

        @JsonProperty("raw_hot")
        private Integer rawHot;
    }
}
