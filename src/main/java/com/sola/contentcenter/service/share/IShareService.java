package com.sola.contentcenter.service.share;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sola.contentcenter.domain.dto.admin.ShareAuditDTO;
import com.sola.contentcenter.domain.entity.share.Share;

/**
 * <p>
 * 分享表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-09-21
 */
public interface IShareService extends IService<Share> {

    Share auditById(Integer id, ShareAuditDTO auditDTO);

    void auditByIdInDB(Integer shareId, ShareAuditDTO auditDTO);

    void auditByIdWithRocketMqLog(Integer shareId, ShareAuditDTO auditDTO, String transactionId);
}
