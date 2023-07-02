package io.github.llnancy.dailyhot.entity;

import io.github.llnancy.dailyhot.client.Kr36ExchangeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 36kr data
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/6/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Kr36Data {

    private Long id;

    private String title;

    private String pic;

    private String owner;

    private Integer hot;

    private Kr36ExchangeClient.TemplateMaterial data;

    private String url;

    private String mobileUrl;
}
