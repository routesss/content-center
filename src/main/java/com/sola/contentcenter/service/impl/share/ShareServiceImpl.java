package com.sola.contentcenter.service.impl.share;

import com.sola.contentcenter.domain.dto.admin.ShareAuditDTO;
import com.sola.contentcenter.domain.enums.AuditStatusEnum;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.mapper.share.ShareMapper;
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

    @Override
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        Share share = this.baseMapper.selectById(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法 分享不存在");
        }
        if (AuditStatusEnum.NOT_YET.toString().equals(auditDTO.getAuditStatusEnum().toString())) {
            throw new IllegalArgumentException("参数非法 当前状态非未审批");
        }

        //审核资源 修改状态
        share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        share.setReason(auditDTO.getReason());
        this.baseMapper.updateById(share);

        //更新发布人积分

        return share;
    }
}
