package io.github.llnancy.dailyhot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * news qq data
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsQQData {

    private String id;

    private String title;

    private String desc;

    private String descSm;

    private Integer hot;

    private String pic;

    private String url;

    private String mobileUrl;
}
