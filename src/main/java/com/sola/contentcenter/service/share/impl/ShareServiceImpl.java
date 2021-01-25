package com.sola.contentcenter.service.share.impl;

import java.util.UUID;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.contentcenter.domain.constant.ConstantCode;
import com.sola.contentcenter.domain.dto.admin.ShareAuditDTO;
import com.sola.contentcenter.domain.entity.rocketmq.RocketmqTransactionLog;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.domain.enums.AuditStatusEnum;
import com.sola.contentcenter.domain.message.UserAddBonusMsgDTO;
import com.sola.contentcenter.mapper.share.ShareMapper;
import com.sola.contentcenter.service.rocketmq.IRocketmqTransactionLogService;
import com.sola.contentcenter.service.share.IShareService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 分享表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-09-21
 */
@Slf4j
@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements IShareService {

    @Autowired(required = false)
    private RocketMQTemplate mqTemplate;
    @Autowired(required = false)
    private IRocketmqTransactionLogService rocketmqTransactionLogService;

    @Override
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        Share share = this.baseMapper.selectById(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法 分享不存在");
        }
        if (!AuditStatusEnum.NOT_YET.toString().equals(share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法 当前状态非未审批");
        }

        String transactionId = UUID.randomUUID().toString();

        this.mqTemplate.sendMessageInTransaction(ConstantCode.AddBonusTransactionGroup, ConstantCode.AddBonusTop,
            MessageBuilder.withPayload(UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build())
                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId).setHeader("share_id", share.getId()).build(),
            auditDTO);

        // 审核资源 修改状态
        /*share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        share.setReason(auditDTO.getReason());
        this.baseMapper.updateById(share);*/

        // 更新发布人积分
        /*mqTemplate.convertAndSend(ConstantCode.AddBonusTop,
            UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build());*/
        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer shareId, ShareAuditDTO auditDTO) {
        Share share = this.baseMapper.selectById(shareId);
        share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        share.setReason(auditDTO.getReason());
        this.baseMapper.updateById(share);
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer shareId, ShareAuditDTO auditDTO, String transactionId) {
        auditByIdInDB(shareId, auditDTO);

        rocketmqTransactionLogService
            .save(RocketmqTransactionLog.builder().transactionId(transactionId).log("用户积分mq日志").build());
    }

}
