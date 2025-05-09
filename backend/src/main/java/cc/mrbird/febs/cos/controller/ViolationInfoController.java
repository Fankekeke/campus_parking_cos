package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.entity.ViolationInfo;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/violation-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ViolationInfoController {

    private final IViolationInfoService violationInfoService;

    private final IUserInfoService userInfoService;

    private final IVehicleInfoService vehicleInfoService;

    private final IMailService mailService;

    private final TemplateEngine templateEngine;

    private final IMessageInfoService messageInfoService;

    /**
     * 分页获取违规信息
     *
     * @param page          分页对象
     * @param violationInfo 违规信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ViolationInfo> page, ViolationInfo violationInfo) {
        return R.ok(violationInfoService.queryViolationPage(page, violationInfo));
    }

    /**
     * 违规信息详情
     *
     * @param id 违规ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(violationInfoService.getById(id));
    }

    /**
     * 违规信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(violationInfoService.list());
    }

    /**
     * 新增违规信息
     *
     * @param violationInfo 违规信息
     * @return 结果
     */
    @PostMapping
    public R save(ViolationInfo violationInfo) {
        violationInfo.setStatus("0");
        if (StrUtil.isNotEmpty(violationInfo.getVehicleNo())) {
            VehicleInfo vehicleInfo = vehicleInfoService.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, violationInfo.getVehicleNo()));
            if (vehicleInfo != null) {
                violationInfo.setUserId(vehicleInfo.getUserId());
            }
        }
        if (violationInfo.getUserId() != null) {
            UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getId, violationInfo.getUserId()));
            // 发送消息
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setUserId(userInfo.getId());
            messageInfo.setContent("您好，您的车辆 " + violationInfo.getVehicleNo() + " 发生停车违规行为，" + violationInfo.getContent() + "， 请联系管理员进行处理");
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            messageInfoService.save(messageInfo);
            if (StrUtil.isNotEmpty(userInfo.getEmail())) {
                Context context = new Context();
                context.setVariable("today", DateUtil.formatDate(new Date()));
                context.setVariable("custom", userInfo.getName() + "您好，您的车辆 " + violationInfo.getVehicleNo() + " 发生停车违规行为，" + violationInfo.getContent() + "， 请联系管理员进行处理");
                String emailContent = templateEngine.process("registerEmail", context);
                mailService.sendHtmlMail(userInfo.getEmail(), DateUtil.formatDate(new Date()) + "违规提示", emailContent);
            }
        }
        violationInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(violationInfoService.save(violationInfo));
    }

    /**
     * 修改违规信息
     *
     * @param violationInfo 违规信息
     * @return 结果
     */
    @PutMapping
    public R edit(ViolationInfo violationInfo) {
        return R.ok(violationInfoService.updateById(violationInfo));
    }

    /**
     * 删除违规信息
     *
     * @param ids ids
     * @return 违规信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(violationInfoService.removeByIds(ids));
    }
}
