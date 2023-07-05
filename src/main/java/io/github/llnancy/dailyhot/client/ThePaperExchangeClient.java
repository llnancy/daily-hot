package io.github.llnancy.dailyhot.client;

import io.github.llnancy.dailyhot.client.ThePaperExchangeClient.ThePaperResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 澎湃新闻
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://cache.thepaper.cn")
public interface ThePaperExchangeClient extends BaseGetExchangeClient<ThePaperResponse> {

    @GetExchange("/contentapi/wwwIndex/rightSidebar")
    @Override
    Mono<ThePaperResponse> dailyHot();

    @Data
    class ThePaperResponse {

        private Integer resultCode;

        private String resultMsg;

        private Long systemTime;

        private ThePaperRespData data;
    }

    @Data
    class ThePaperRespData {

        private List<HotNew> hotNews;
    }

    @Data
    class HotNew {

        private String contId;

        private String name;

        private String pic;

        private String praiseTimes;

        private String pubTime;
    }
}
