package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.TouTiaoExchangeClient.TouTiaoResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 头条
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://www.toutiao.com")
public interface TouTiaoExchangeClient extends BaseGetExchangeClient<TouTiaoResponse> {

    @GetExchange("/hot-event/hot-board/?origin=toutiao_pc")
    @Override
    Mono<TouTiaoResponse> dailyHot();

    @Data
    class TouTiaoResponse {

        private List<TouTiaoRespData> data;
    }

    @Data
    class TouTiaoRespData {

        @JsonProperty("ClusterId")
        private String clusterId;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("Image")
        private Image image;

        @JsonProperty("HotValue")
        private String hotValue;

        @JsonProperty("ClusterIdStr")
        private String clusterIdStr;
    }

    @Data
    class Image {

        private String url;
    }
}
