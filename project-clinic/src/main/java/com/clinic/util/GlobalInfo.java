package com.clinic.util;

import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.entity.Visit;
import com.clinic.service.UserRole;

public final class GlobalInfo {
    private GlobalInfo() {}

    public static UserRole USER_ROLE;

    public static Patient PATIENT;

    public static Doctor DOCTOR;

    public static Visit VISIT;


}
