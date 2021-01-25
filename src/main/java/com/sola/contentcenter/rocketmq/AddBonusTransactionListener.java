package com.sola.contentcenter.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.sola.contentcenter.domain.constant.ConstantCode;
import com.sola.contentcenter.domain.dto.admin.ShareAuditDTO;
import com.sola.contentcenter.domain.entity.rocketmq.RocketmqTransactionLog;
import com.sola.contentcenter.service.rocketmq.IRocketmqTransactionLogService;
import com.sola.contentcenter.service.share.IShareService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RocketMQTransactionListener(txProducerGroup = ConstantCode.AddBonusTransactionGroup)
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private IShareService shareService;
    @Autowired
    private IRocketmqTransactionLogService rocketmqTransactionLogService;

    /**
     * 执行本地事务
     * 
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {

        MessageHeaders headers = message.getHeaders();

        ShareAuditDTO auditDTO = (ShareAuditDTO)o;

        String transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf(headers.get("share_id").toString());

        try {
            this.shareService.auditByIdWithRocketMqLog(shareId, auditDTO, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务执行结果
     * 
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        RocketmqTransactionLog rocketmqTransactionLog =
            rocketmqTransactionLogService.getLogByTransactionId(transactionId);
        if (rocketmqTransactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
