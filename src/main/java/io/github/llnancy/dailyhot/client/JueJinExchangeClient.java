package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.JueJinExchangeClient.JueJinResponse;
import io.github.llnancy.httpexchange.core.ExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 掘金
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@ExchangeClient(baseUrl = "https://api.juejin.cn")
public interface JueJinExchangeClient extends BaseGetExchangeClient<JueJinResponse> {

    @GetExchange("/content_api/v1/content/article_rank?category_id=1&type=hot")
    @Override
    Mono<JueJinResponse> dailyHot();

    @Data
    class JueJinResponse {

        private List<JueJinRespData> data;
    }

    @Data
    class JueJinRespData {

        private Content content;

        @JsonProperty("content_counter")
        private ContentCounter contentCounter;

    }

    @Data
    class Content {

        @JsonProperty("content_id")
        private String contentId;

        private String title;
    }

    @Data
    class ContentCounter {

        @JsonProperty("hot_rank")
        private Integer hotRank;
    }
}
