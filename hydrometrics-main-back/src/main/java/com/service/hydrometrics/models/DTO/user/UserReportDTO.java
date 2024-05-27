package com.service.hydrometrics.models.DTO.user;

import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.utils.UtilsMethods;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Getter
public class UserReportDTO implements Serializable {
    private final long id;
    private final String username;
    private final String first_name;
    private final String last_name;
    private final String email;
    private final String role;
    private final int enabled;
    private final String creation_date;
    private final String modification_date;

    public UserReportDTO(long id,
                         String username,
                         String first_name,
                         String last_name,
                         String email,
                         String role,
                         Boolean enabled,
                         Long creation_date,
                         Long modification_date) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
        this.enabled =  enabled? 1 : 0;
        this.creation_date = UtilsMethods.longTimeStampToFormatString(String.valueOf(creation_date));
        this.modification_date = UtilsMethods.longTimeStampToFormatString(String.valueOf(modification_date));
    }
}