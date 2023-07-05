package io.github.llnancy.dailyhot.client;

import io.github.llnancy.dailyhot.client.Kr36ExchangeClient.Kr36Response;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 36kr
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://gateway.36kr.com")
public interface Kr36ExchangeClient extends BasePostExchangeClient<String, Kr36Response> {

    @PostExchange(url = "/api/mis/nav/home/nav/rank/hot", contentType = MediaType.APPLICATION_JSON_VALUE)
    @Override
    Mono<Kr36Response> dailyHot(@RequestBody String params);

    @Data
    class Kr36Response {

        private Integer code;

        private Kr36RespData data;
    }

    @Data
    class Kr36RespData {

        private List<HotRank> hotRankList;
    }

    @Data
    class HotRank {

        private Long itemId;

        private Integer itemType;

        private TemplateMaterial templateMaterial;

        private String route;

        private Integer siteId;
    }

    @Data
    class TemplateMaterial {

        private Long itemId;

        private Integer templateType;

        private String widgetImage;

        private Long publishTime;

        private String widgetTitle;

        private String authorName;

        private Integer statRead;

        private Integer statCollect;

        private Integer statComment;

        private Integer statPraise;

        private String statFormat;
    }
}
