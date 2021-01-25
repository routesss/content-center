package com.sola.contentcenter.service.rocketmq.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.contentcenter.domain.entity.rocketmq.RocketmqTransactionLog;
import com.sola.contentcenter.mapper.rocketmq.RocketmqTransactionLogMapper;
import com.sola.contentcenter.service.rocketmq.IRocketmqTransactionLogService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-22
 */
@Service
public class RocketmqTransactionLogServiceImpl extends ServiceImpl<RocketmqTransactionLogMapper, RocketmqTransactionLog>
    implements IRocketmqTransactionLogService {

    public RocketmqTransactionLog getLogByTransactionId(String transactionId) {
        LambdaQueryWrapper<RocketmqTransactionLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RocketmqTransactionLog::getTransactionId, transactionId);
        return this.baseMapper.selectOne(lambdaQueryWrapper);
    }
}
