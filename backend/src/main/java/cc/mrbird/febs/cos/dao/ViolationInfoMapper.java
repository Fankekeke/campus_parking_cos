package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ViolationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface ViolationInfoMapper extends BaseMapper<ViolationInfo> {

    /**
     * 分页获取违规信息
     *
     * @param page          分页对象
     * @param violationInfo 违规信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryViolationPage(Page<ViolationInfo> page, @Param("violationInfo") ViolationInfo violationInfo);
}
