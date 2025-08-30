package com.example.hospital.mapper;

import com.example.hospital.entity.DoctorView;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 医生视图数据访问层接口
 */
@Mapper
public interface DoctorViewMapper {
    
    /**
     * 获取所有医生视图信息
     */
    @Select("SELECT * FROM doctor_view")
    List<DoctorView> getAllDoctorViews();

    /**
     * 根据员工ID查询医生视图信息
     */
    @Select("SELECT * FROM doctor_view WHERE staff_id = #{staffId}")
    DoctorView getDoctorViewById(String staffId);

    /**
     * 根据科室查询医生视图信息
     */
    @Select("SELECT * FROM doctor_view WHERE department = #{department}")
    List<DoctorView> getDoctorViewsByDepartment(String department);

    /**
     * 根据职称查询医生视图信息
     */
    @Select("SELECT * FROM doctor_view WHERE title = #{title}")
    List<DoctorView> getDoctorViewsByTitle(String title);

    /**
     * 根据姓名模糊查询医生视图信息
     */
    @Select("SELECT * FROM doctor_view WHERE staff_name LIKE CONCAT('%', #{staffName}, '%')")
    List<DoctorView> getDoctorViewsByName(String staffName);
} 