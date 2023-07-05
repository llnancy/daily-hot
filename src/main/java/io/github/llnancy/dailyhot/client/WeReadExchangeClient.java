package io.github.llnancy.dailyhot.client;

import io.github.llnancy.dailyhot.client.WeReadExchangeClient.WeReadResponse;
import io.github.llnancy.httpexchange.core.HttpExchangeClient;
import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 微信读书
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@HttpExchangeClient(baseUrl = "https://weread.qq.com")
public interface WeReadExchangeClient extends BaseGetExchangeClient<WeReadResponse> {

    @GetExchange("/web/bookListInCategory/rising?maxIndex=0&rank=1")
    @Override
    Mono<WeReadResponse> dailyHot();

    @Data
    class WeReadResponse {

        private List<Book> books;
    }

    @Data
    class Book {

        private BookInfo bookInfo;

        private Integer readingCount;
    }

    @Data
    class BookInfo {

        private String bookId;

        private String title;

        private String intro;

        private String cover;
    }
}
