package com.sola.contentcenter.domain.entity.rocketmq;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-01-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RocketmqTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事务id
     */
    @TableField("transaction_Id")
    private String transactionId;

    /**
     * 日志
     */
    private String log;

}
