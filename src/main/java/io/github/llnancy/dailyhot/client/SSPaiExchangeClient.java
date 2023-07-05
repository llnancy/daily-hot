package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.SSPaiExchangeClient.SSPaiResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 少数派
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://sspai.com")
public interface SSPaiExchangeClient extends BaseGetExchangeClient<SSPaiResponse> {

    @GetExchange("/api/v1/article/tag/page/get?limit=40&tag=热门文章")
    @Override
    Mono<SSPaiResponse> dailyHot();

    @Data
    class SSPaiResponse {

        private Integer error;

        private String msg;

        private List<SSPaiRespData> data;
    }

    @Data
    class SSPaiRespData {

        private Integer id;

        private String title;

        private String summary;

        private String banner;

        private Author author;

        @JsonProperty("like_count")
        private Integer likeCount;
    }

    @Data
    class Author {

        private Integer id;

        private String slug;

        private String avatar;

        private String nickname;
    }
}
