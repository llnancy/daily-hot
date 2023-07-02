package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.httpexchange.core.ExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static io.github.llnancy.dailyhot.client.BiliBiliExchangeClient.BiliBiliResponse;

/**
 * BiliBili
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@ExchangeClient(baseUrl = "https://api.bilibili.com")
public interface BiliBiliExchangeClient extends BaseGetExchangeClient<BiliBiliResponse> {

    @GetExchange("/x/web-interface/ranking/v2")
    @Override
    Mono<BiliBiliResponse> dailyHot();

    @Data
    class BiliBiliResponse {
        private BiliBiliRespData data;
    }

    @Data
    class BiliBiliRespData {
        private List<BiliBiliDataItem> list;
    }

    @Data
    class BiliBiliDataItem {
        private String pic;
        private String title;
        private String desc;
        private Owner owner;
        private Stat stat;
        @JsonProperty("short_link_v2")
        private String shortLinkV2;
        private String bvid;
    }

    @Data
    class Owner {
        private String name;
    }

    @Data
    class Stat {
        private Integer view;
    }
}
