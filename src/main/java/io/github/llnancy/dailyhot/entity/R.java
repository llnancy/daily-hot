package io.github.llnancy.dailyhot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * result
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class R<T> {

    private Integer code;

    private String message;

    private String from;

    private LocalDateTime requestTime;

    private T data;
}
