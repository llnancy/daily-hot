package io.github.llnancy.dailyhot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * the paper data
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThePaperData {

    private String id;

    private String title;

    private String pic;

    private String hot;

    private String time;

    private String url;

    private String mobileUrl;
}
