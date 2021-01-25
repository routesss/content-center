package com.sola.contentcenter.service.notice.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sola.contentcenter.domain.entity.notice.Notice;
import com.sola.contentcenter.mapper.notice.NoticeMapper;
import com.sola.contentcenter.service.notice.INoticeService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-15
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
