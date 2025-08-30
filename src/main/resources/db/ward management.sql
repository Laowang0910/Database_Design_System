-- ----------------------------
-- View structure for doctor_patient_view
-- ----------------------------
DROP VIEW IF EXISTS `doctor_patient_view`;
CREATE VIEW `doctor_patient_view` AS
SELECT 
    d.staff_id AS doctor_id,
    d.staff_name AS doctor_name,
    p.patient_id,
    p.patient_name,
    p.gender,
    p.age,
    p.diagnosis,
    p.patient_status,
    v.state AS visit_state,
    p.admission_time,
    p.dsicharge_time
FROM 
    医护人员表 d
    JOIN 医患就诊表 v ON d.staff_id = v.staff_id
    JOIN 患者信息表 p ON v.patient_id = p.patient_id
WHERE 
    d.position = '医生';

-- ----------------------------
-- View structure for ward_occupancy_view
-- ----------------------------
DROP VIEW IF EXISTS `ward_occupancy_view`;
CREATE VIEW `ward_occupancy_view` AS
SELECT 
    w.ward_id,
    w.ward_name,
    w.ward_type,
    w.floor_number,
    w.total_beds,
    w.available_beds,
    COUNT(DISTINCT pb.bed_id) as occupied_beds,
    w.ward_status,
    GROUP_CONCAT(DISTINCT b.bed_number) as occupied_bed_numbers
FROM 
    病房信息表 w
    LEFT JOIN 床位信息表 b ON w.ward_id = b.ward_id
    LEFT JOIN 患者床位信息表 pb ON b.bed_id = pb.bed_id AND pb.assign_status = '有效'
GROUP BY 
    w.ward_id, w.ward_name, w.ward_type, w.floor_number, 
    w.total_beds, w.available_beds, w.ward_status;

-- ----------------------------
-- View structure for patient_treatment_view
-- ----------------------------
DROP VIEW IF EXISTS `patient_treatment_view`;
CREATE VIEW `patient_treatment_view` AS
SELECT 
    p.patient_id,
    p.patient_name,
    p.diagnosis,
    c.treatment_plan,
    d.staff_name AS doctor_name,
    n.staff_name AS nurse_name,
    pr.medication_name,
    pr.dosage,
    pr.frequency,
    pr.start_date,
    pr.end_date,
    pr.notes AS prescription_notes
FROM 
    患者信息表 p
    LEFT JOIN 病例表 c ON p.patient_id = c.patient_id
    LEFT JOIN 医护人员表 d ON c.doctor_id = d.staff_id
    LEFT JOIN 医护人员表 n ON c.nurse_id = n.staff_id
    LEFT JOIN prescriptions pr ON p.patient_id = pr.patient_id;

-- ----------------------------
-- View structure for staff_schedule_view
-- ----------------------------
DROP VIEW IF EXISTS `staff_schedule_view`;
CREATE VIEW `staff_schedule_view` AS
SELECT 
    s.schedule_id,
    m.staff_id,
    m.staff_name,
    m.department,
    m.position,
    w.ward_id,
    w.ward_name,
    s.schedlue_date,
    s.shift_type,
    s.duty_status,
    w.total_beds,
    w.available_beds
FROM 
    医护人员排班表 s
    JOIN 医护人员表 m ON s.staff_id = m.staff_id
    JOIN 病房信息表 w ON s.ward_id = w.ward_id
ORDER BY 
    s.schedlue_date, s.shift_type; 