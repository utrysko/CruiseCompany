package com.cruise.model;

import com.cruise.dto.UserDTO;

/**
 * Role entity class.
 *
 * @author Vasyl Utrysko.
 */

public enum Role {
    ADMIN, CLIENT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[--roleId];
    }
    public static Role getRole(UserDTO userDTO) {
        int roleId = userDTO.getRoleId();
        return Role.values()[--roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
