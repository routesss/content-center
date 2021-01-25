package com.sola.contentcenter.service.rocketmq;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sola.contentcenter.domain.entity.rocketmq.RocketmqTransactionLog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-22
 */
public interface IRocketmqTransactionLogService extends IService<RocketmqTransactionLog> {
    RocketmqTransactionLog getLogByTransactionId(String transactionId);
}
