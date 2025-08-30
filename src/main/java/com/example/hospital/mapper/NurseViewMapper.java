package com.example.hospital.mapper;

import com.example.hospital.entity.NurseView;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 护士视图数据访问层接口
 */
@Mapper
public interface NurseViewMapper {
    
    /**
     * 获取所有护士视图信息
     */
    @Select("SELECT * FROM nurse_view")
    List<NurseView> getAllNurseViews();

    /**
     * 根据员工ID查询护士视图信息
     */
    @Select("SELECT * FROM nurse_view WHERE staff_id = #{staffId}")
    NurseView getNurseViewById(String staffId);

    /**
     * 根据科室查询护士视图信息
     */
    @Select("SELECT * FROM nurse_view WHERE department = #{department}")
    List<NurseView> getNurseViewsByDepartment(String department);

    /**
     * 根据职称查询护士视图信息
     */
    @Select("SELECT * FROM nurse_view WHERE title = #{title}")
    List<NurseView> getNurseViewsByTitle(String title);

    /**
     * 根据姓名模糊查询护士视图信息
     */
    @Select("SELECT * FROM nurse_view WHERE staff_name LIKE CONCAT('%', #{staffName}, '%')")
    List<NurseView> getNurseViewsByName(String staffName);
} 