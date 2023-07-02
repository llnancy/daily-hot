package io.github.llnancy.dailyhot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.llnancy.dailyhot.client.NewsQQExchangeClient.NewsQQClientCodecConfigurerConsumer;
import io.github.llnancy.dailyhot.client.NewsQQExchangeClient.NewsQQResponse;
import io.github.llnancy.httpexchange.core.ClientCodecConfigurerConsumer;
import io.github.llnancy.httpexchange.core.ExchangeClient;
import lombok.Data;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * 腾讯新闻
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@ExchangeClient(baseUrl = "https://r.inews.qq.com", codecConfigurerConsumer = NewsQQClientCodecConfigurerConsumer.class)
public interface NewsQQExchangeClient extends BaseGetExchangeClient<NewsQQResponse> {

    @GetExchange("/gw/event/hot_ranking_list?page_size=50")
    @Override
    Mono<NewsQQResponse> dailyHot();

    /**
     * 腾讯新闻返回的数据包体太大，正常调用会出现以下异常
     * Caused by: org.springframework.core.io.buffer.DataBufferLimitException: Exceeded limit on max bytes to buffer : 262144
     * 此处配置下 maxInMemorySize
     */
    @Component
    class NewsQQClientCodecConfigurerConsumer implements ClientCodecConfigurerConsumer {

        @Override
        public Consumer<ClientCodecConfigurer> consumer() {
            return configurer -> configurer.defaultCodecs()
                    .maxInMemorySize(500 * 1024);
        }
    }

    @Data
    class NewsQQResponse {

        private Integer ret;

        private List<IDList> idlist;
    }

    @Data
    class IDList {

        private List<News> newslist;
    }

    @Data
    class News {

        private String id;

        private String title;

        @JsonProperty("abstract")
        private String _abstract;

        private String nlpAbstract;

        private Integer readCount;

        private String miniProShareImage;
    }
}
