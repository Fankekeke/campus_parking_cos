package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ViolationInfo;
import cc.mrbird.febs.cos.dao.ViolationInfoMapper;
import cc.mrbird.febs.cos.service.IViolationInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class ViolationInfoServiceImpl extends ServiceImpl<ViolationInfoMapper, ViolationInfo> implements IViolationInfoService {

    /**
     * 分页获取违规信息
     *
     * @param page          分页对象
     * @param violationInfo 违规信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryViolationPage(Page<ViolationInfo> page, ViolationInfo violationInfo) {
        return baseMapper.queryViolationPage(page, violationInfo);
    }
}
