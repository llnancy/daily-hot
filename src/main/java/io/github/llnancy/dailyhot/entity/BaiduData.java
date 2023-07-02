package io.github.llnancy.dailyhot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * baidu data
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaiduData {

    private String title;

    private String desc;

    private String pic;

    private Integer hot;

    private String url;

    private String mobileUrl;
}
