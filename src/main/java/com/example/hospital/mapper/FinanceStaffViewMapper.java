package com.example.hospital.mapper;

import com.example.hospital.entity.FinanceStaffView;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 财务人员视图数据访问层接口
 */
@Mapper
public interface FinanceStaffViewMapper {
    
    /**
     * 获取所有财务人员视图信息
     */
    @Select("SELECT * FROM finance_staff_view")
    List<FinanceStaffView> getAllFinanceStaffViews();

    /**
     * 根据用户ID查询财务人员视图信息
     */
    @Select("SELECT * FROM finance_staff_view WHERE user_id = #{userId}")
    FinanceStaffView getFinanceStaffViewById(String userId);

    /**
     * 根据用户名查询财务人员视图信息
     */
    @Select("SELECT * FROM finance_staff_view WHERE username = #{username}")
    FinanceStaffView getFinanceStaffViewByUsername(String username);

    /**
     * 根据状态查询财务人员视图信息
     */
    @Select("SELECT * FROM finance_staff_view WHERE status = #{status}")
    List<FinanceStaffView> getFinanceStaffViewsByStatus(String status);
} 