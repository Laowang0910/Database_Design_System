package com.example.hospital.mapper;

import com.example.hospital.entity.DoctorUndiagnosedPatientView;
import com.example.hospital.entity.DoctorPatientVisit;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoctorPatientVisitMapper {

    // 查询医生未就诊患者视图中的所有记录
    @Select("SELECT `医生编号`, `医生姓名`, `患者编号`, `患者姓名`, `就诊状态` FROM `医生未就诊患者视图`")
    @Results({
        @Result(property = "doctorStaffId", column = "医生编号"),
        @Result(property = "doctorName", column = "医生姓名"),
        @Result(property = "patientId", column = "患者编号"),
        @Result(property = "patientName", column = "患者姓名"),
        @Result(property = "visitState", column = "就诊状态")
    })
    List<DoctorUndiagnosedPatientView> getAllUndiagnosedPatientViews();

    // 根据医生编号查询医生未就诊患者视图中的记录
    @Select("SELECT `医生编号`, `医生姓名`, `患者编号`, `患者姓名`, `就诊状态` FROM `医生未就诊患者视图` WHERE `医生编号` = #{staffId}")
    @Results({
        @Result(property = "doctorStaffId", column = "医生编号"),
        @Result(property = "doctorName", column = "医生姓名"),
        @Result(property = "patientId", column = "患者编号"),
        @Result(property = "patientName", column = "患者姓名"),
        @Result(property = "visitState", column = "就诊状态")
    })
    List<DoctorUndiagnosedPatientView> getUndiagnosedPatientViewsByStaffId(@Param("staffId") String staffId);

    // 更新医患就诊表中的记录状态为已就诊
    @Update("UPDATE 医患就诊表 SET state = '已就诊' WHERE staff_id = #{staffId} AND patient_id = #{patientId}")
    int updateStateToDiagnosed(@Param("staffId") String staffId, @Param("patientId") String patientId);

    // 向医患就诊表插入新记录，状态为未就诊
    @Insert("INSERT INTO 医患就诊表(staff_id, patient_id, state) VALUES (#{staffId}, #{patientId}, '未就诊')")
    int insertUndiagnosedVisit(DoctorPatientVisit visit);
} 