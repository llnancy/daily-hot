package io.github.llnancy.dailyhot.factory;

import io.github.llnancy.dailyhot.enums.PlatformEnum;
import io.github.llnancy.dailyhot.service.DailyHotService;
import io.github.llnancy.dailyhot.service.impl.BaiduGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.BiliBiliGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.ITHomeGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.JueJinGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.Kr36DailyHotService;
import io.github.llnancy.dailyhot.service.impl.NewsQQGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.SSPaiGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.ThePaperGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.TieBaGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.TouTiaoGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.WeReadGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.WeiboGetDailyHotService;
import io.github.llnancy.dailyhot.service.impl.ZhiHuGetDailyHotService;
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
public class DailyHotServiceFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private final Map<PlatformEnum, Class<? extends DailyHotService<?>>> dhsMap = new HashMap<>();

    {
        dhsMap.put(PlatformEnum.BAIDU, BaiduGetDailyHotService.class);
        dhsMap.put(PlatformEnum.BILI_BILI, BiliBiliGetDailyHotService.class);
        dhsMap.put(PlatformEnum.IT_HOME, ITHomeGetDailyHotService.class);
        dhsMap.put(PlatformEnum.JUE_JIN, JueJinGetDailyHotService.class);
        dhsMap.put(PlatformEnum.NEWS_QQ, NewsQQGetDailyHotService.class);
        dhsMap.put(PlatformEnum.SS_PAI, SSPaiGetDailyHotService.class);
        dhsMap.put(PlatformEnum.THE_PAPER, ThePaperGetDailyHotService.class);
        dhsMap.put(PlatformEnum.TIE_BA, TieBaGetDailyHotService.class);
        dhsMap.put(PlatformEnum.TOU_TIAO, TouTiaoGetDailyHotService.class);
        dhsMap.put(PlatformEnum.WEIBO, WeiboGetDailyHotService.class);
        dhsMap.put(PlatformEnum.WE_READ, WeReadGetDailyHotService.class);
        dhsMap.put(PlatformEnum.ZHI_HU, ZhiHuGetDailyHotService.class);
        dhsMap.put(PlatformEnum.KR36, Kr36DailyHotService.class);
    }

    @SuppressWarnings("unchecked")
    public <D> DailyHotService<D> fetchDailyHotService(PlatformEnum platform) {
        Class<? extends DailyHotService<?>> clazz = dhsMap.get(platform);
        return (DailyHotService<D>) applicationContext.getBean(clazz);
    }
}
