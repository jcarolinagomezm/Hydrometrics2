package com.service.hydrometrics.services;

import com.service.hydrometrics.models.DB.entity.User;
import com.service.hydrometrics.models.DTO.user.UserDTO;
import com.service.hydrometrics.models.DTO.user.UserReportDTO;

import java.util.List;

public interface IUserService {

    boolean userExist(User user);

    List<UserDTO> getAllUsers();

    UserDTO saveUser(User user);

    UserDTO updateUser(User user);

    User getUser(String username);

    List<UserReportDTO> getUserReports();
}
