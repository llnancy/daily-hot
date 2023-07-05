package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.TieBaExchangeClient.TieBaResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 百度贴吧
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://tieba.baidu.com")
public interface TieBaExchangeClient extends BaseGetExchangeClient<TieBaResponse> {

    @GetExchange("/hottopic/browse/topicList")
    @Override
    Mono<TieBaResponse> dailyHot();

    @Data
    class TieBaResponse {

        private Integer errno;

        private String errmsg;

        private TieBaRespData data;
    }

    @Data
    class TieBaRespData {

        @JsonProperty("bang_topic")
        private BangTopic bangTopic;
    }

    @Data
    class BangTopic {

        @JsonProperty("topic_list")
        private List<Topic> topicList;
    }

    @Data
    class Topic {

        @JsonProperty("topic_id")
        private Integer topicId;

        @JsonProperty("topic_name")
        private String topicName;

        @JsonProperty("topic_desc")
        private String topicDesc;

        @JsonProperty("topic_pic")
        private String topicPic;

        @JsonProperty("discuss_num")
        private Integer discussNum;

        @JsonProperty("topic_url")
        private String topicUrl;
    }
}
