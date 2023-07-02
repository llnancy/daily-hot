package io.github.llnancy.dailyhot.service.impl;

import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.entity.ItHomeData;
import io.github.llnancy.dailyhot.service.AbstractGetDailyHotService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * it home daily hot service
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Service
public class ITHomeGetDailyHotService extends AbstractGetDailyHotService<String, ItHomeData> {

    private static final Pattern ITHOME_PATTERN = Pattern.compile("\\D");

    private static String replaceLink(String url) {
        String match = url.replaceAll(".*/(\\d+)\\.htm.*", "$1");
        return "https://www.ithome.com/0/" + match.substring(0, 3) + "/" + match.substring(3) + ".htm";
    }

    @Override
    public Flux<ItHomeData> doDailyHot(BaseGetExchangeClient<String> exchangeClient) {
        return exchangeClient.dailyHot()
                .flatMapMany(resp -> {
                    try {
                        Document doc = Jsoup.parse(resp);
                        return Flux.fromIterable(doc.select(".rank-name"))
                                .flatMap(rankName -> {
                                    String type = rankName.attr("data-rank-type");
                                    return Optional.ofNullable(rankName.nextElementSibling())
                                            .map(nextSibling -> Jsoup.parse(nextSibling.html()))
                                            .map(newDoc -> Flux.fromStream(newDoc.select(".placeholder").stream()))
                                            .orElseGet(Flux::empty)
                                            .map(placeholder -> ItHomeData.builder()
                                                    .title(placeholder.select(".plc-title").text())
                                                    .img(placeholder.select("img").attr("data-original"))
                                                    .time(placeholder.select(".post-time").text())
                                                    .type(rankName.text())
                                                    .typeName(type)
                                                    .hot(Integer.parseInt(ITHOME_PATTERN.matcher(placeholder.select(".review-num").text()).replaceAll("")))
                                                    .url(replaceLink(placeholder.select("a").attr("href")))
                                                    .mobileUrl(placeholder.select("a").attr("href"))
                                                    .build()
                                            );
                                });
                    } catch (Exception e) {
                        return Flux.error(e);
                    }
                });
    }
}
