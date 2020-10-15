package com.sola.contentcenter.service.impl.share;

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

}
