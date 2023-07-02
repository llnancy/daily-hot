package io.github.llnancy.dailyhot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ITHome data
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItHomeData {

    private String title;

    private String img;

    private String time;

    private String type;

    private String typeName;

    private Integer hot;

    private String url;

    private String mobileUrl;
}
