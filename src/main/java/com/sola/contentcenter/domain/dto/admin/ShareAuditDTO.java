package com.sola.contentcenter.domain.dto.admin;

import com.sola.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

@Data
public class ShareAuditDTO {
    /**
     *审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 审核原因
     */
    private String reason;
}
