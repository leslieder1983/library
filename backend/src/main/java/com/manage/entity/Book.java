package com.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@Getter
@Setter
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id，主键号
     */
    private String id;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 教材分类
     */
    private Integer bookKind;

    private BigDecimal bookPrice;

    /**
     * 作者
     */
    private String author;

    /**
     * 书号
     */
    private String isbn;

    private LocalDateTime createTime;

    /**
     * 库存量
     */
    private Long inventory;

    /**
     * 出版社
     */
    private String press;


}
