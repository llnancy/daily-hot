package io.github.llnancy.dailyhot.factory;

import io.github.llnancy.dailyhot.client.BaiduExchangeClient;
import io.github.llnancy.dailyhot.client.BaseGetExchangeClient;
import io.github.llnancy.dailyhot.client.BasePostExchangeClient;
import io.github.llnancy.dailyhot.client.BiliBiliExchangeClient;
import io.github.llnancy.dailyhot.client.ITHomeExchangeClient;
import io.github.llnancy.dailyhot.client.JueJinExchangeClient;
import io.github.llnancy.dailyhot.client.Kr36ExchangeClient;
import io.github.llnancy.dailyhot.client.NewsQQExchangeClient;
import io.github.llnancy.dailyhot.client.SSPaiExchangeClient;
import io.github.llnancy.dailyhot.client.ThePaperExchangeClient;
import io.github.llnancy.dailyhot.client.TieBaExchangeClient;
import io.github.llnancy.dailyhot.client.TouTiaoExchangeClient;
import io.github.llnancy.dailyhot.client.WeReadExchangeClient;
import io.github.llnancy.dailyhot.client.WeiboExchangeClient;
import io.github.llnancy.dailyhot.client.ZhiHuExchangeClient;
import io.github.llnancy.dailyhot.enums.PlatformEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Component
public class ExchangeClientFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private final Map<PlatformEnum, Class<? extends BaseGetExchangeClient<?>>> gm = new HashMap<>();

    private final Map<PlatformEnum, Class<? extends BasePostExchangeClient<?, ?>>> pm = new HashMap<>();

    {
        gm.put(PlatformEnum.BAIDU, BaiduExchangeClient.class);
        gm.put(PlatformEnum.BILI_BILI, BiliBiliExchangeClient.class);
        gm.put(PlatformEnum.IT_HOME, ITHomeExchangeClient.class);
        gm.put(PlatformEnum.JUE_JIN, JueJinExchangeClient.class);
        gm.put(PlatformEnum.NEWS_QQ, NewsQQExchangeClient.class);
        gm.put(PlatformEnum.SS_PAI, SSPaiExchangeClient.class);
        gm.put(PlatformEnum.THE_PAPER, ThePaperExchangeClient.class);
        gm.put(PlatformEnum.TIE_BA, TieBaExchangeClient.class);
        gm.put(PlatformEnum.TOU_TIAO, TouTiaoExchangeClient.class);
        gm.put(PlatformEnum.WEIBO, WeiboExchangeClient.class);
        gm.put(PlatformEnum.WE_READ, WeReadExchangeClient.class);
        gm.put(PlatformEnum.ZHI_HU, ZhiHuExchangeClient.class);
        pm.put(PlatformEnum.KR36, Kr36ExchangeClient.class);
    }

    @SuppressWarnings("unchecked")
    public <T> BaseGetExchangeClient<T> fetchGetExchangeClient(PlatformEnum platform) {
        Class<? extends BaseGetExchangeClient<?>> clazz = gm.get(platform);
        return (BaseGetExchangeClient<T>) applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public <BODY, T> BasePostExchangeClient<BODY, T> fetchPostExchangeClient(PlatformEnum platform) {
        Class<? extends BasePostExchangeClient<?, ?>> clazz = pm.get(platform);
        return (BasePostExchangeClient<BODY, T>) applicationContext.getBean(clazz);
    }
}
