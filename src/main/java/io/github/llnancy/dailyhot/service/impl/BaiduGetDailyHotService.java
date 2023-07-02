package io.github.llnancy.dailyhot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.entity.BaiduData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * baidu daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@RequiredArgsConstructor
@Service
public class BaiduGetDailyHotService extends AbstractGetDailyHotService<String, BaiduData> {

    private static final Pattern BAIDU_PATTERN = Pattern.compile("<!--s-data:(.*?)-->");

    private final ObjectMapper objectMapper;

    @Override
    public Flux<BaiduData> doDailyHot(BaseGetExchangeClient<String> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapMany(resp -> {
                    Matcher matcher = BAIDU_PATTERN.matcher(resp);
                    if (!matcher.find()) {
                        return Flux.empty();
                    }
                    String matchResult = matcher.group(1);
                    JsonNode jsonObject;
                    try {
                        jsonObject = objectMapper.readValue(matchResult, JsonNode.class)
                                .path("data")
                                .path("cards")
                                .get(0)
                                .path("content");
                    } catch (JsonProcessingException e) {
                        return Flux.error(e);
                    }
                    return Flux.fromIterable(jsonObject)
                            .map(node -> {
                                String title = node.path("query").asText();
                                return BaiduData.builder()
                                        .title(title)
                                        .desc(node.path("desc").asText())
                                        .pic(node.path("img").asText())
                                        .hot(node.path("hotScore").asInt())
                                        .url("https://www.baidu.com/s?wd=" + URLEncoder.encode(title, StandardCharsets.UTF_8))
                                        .mobileUrl(node.path("url").asText())
                                        .build();
                            });
                });
    }
}
