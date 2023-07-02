package io.github.llnancy.dailyhot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.entity.ZhiHuData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * zhi-hu daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
@RequiredArgsConstructor
public class ZhiHuGetDailyHotService extends AbstractGetDailyHotService<String, ZhiHuData> {

    private static final Pattern ZHIHU_PATTERN = Pattern.compile("<script id=\"js-initialData\" type=\"text/json\">(.*?)</script>");

    private final ObjectMapper objectMapper;

    @Override
    public Flux<ZhiHuData> doDailyHot(BaseGetExchangeClient<String> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapMany(resp -> {
                    Matcher matcher = ZHIHU_PATTERN.matcher(resp);
                    if (!matcher.find()) {
                        return Flux.empty();
                    }
                    String matchResult = matcher.group(1);
                    JsonNode hotList;
                    try {
                        hotList = objectMapper.readTree(matchResult)
                                .path("initialState")
                                .path("topstory")
                                .path("hotList");
                    } catch (JsonProcessingException e) {
                        return Flux.error(e);
                    }
                    return Flux.fromIterable(hotList)
                            .map(node -> {
                                JsonNode target = node.path("target");
                                String url = target.path("link").path("url").asText();
                                return ZhiHuData.builder()
                                        .title(target.path("titleArea").path("text").asText())
                                        .desc(target.path("excerptArea").path("text").asText())
                                        .pic(target.path("imageArea").path("text").asText())
                                        .hot(target.path("metricsArea").path("text").asInt() * 10000)
                                        .url(url)
                                        .mobileUrl(url)
                                        .build();
                            });
                });
    }
}
