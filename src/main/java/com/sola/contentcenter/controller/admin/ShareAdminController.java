package com.sola.contentcenter.controller.admin;

import com.sola.contentcenter.domain.dto.admin.ShareAuditDTO;
import com.sola.contentcenter.domain.entity.share.Share;
import com.sola.contentcenter.service.share.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    private IShareService shareService;

    @PutMapping("/audit/{id}")
    public Share auditById(@PathVariable(value = "id") Integer id, @RequestBody ShareAuditDTO auditDTO){
        // TODO 认证 授权
        return shareService.auditById(id, auditDTO);
    }
}
