package io.github.llnancy.dailyhot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Getter
@AllArgsConstructor
public enum PlatformEnum {

    KR36(RequestMethod.POST),

    BAIDU,

    BILI_BILI,

    IT_HOME,

    JUE_JIN,

    NEWS_QQ,

    SS_PAI,

    THE_PAPER,

    TIE_BA,

    TOU_TIAO,

    WEIBO,

    WE_READ,

    ZHI_HU,

    ;

    private final RequestMethod method;

    PlatformEnum() {
        this.method = RequestMethod.GET;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase().replaceAll("_", "");
    }
}
