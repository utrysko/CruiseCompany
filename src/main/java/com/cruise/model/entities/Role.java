package com.cruise.model.entities;

import com.cruise.dto.UserDTO;

/**
 * Role entity enum. Matches table 'role' in database.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */

public enum Role {
    ADMIN, CLIENT;

    /**
     * Obtains the role by the userDTO.
     * @param userDTO to get role id
     * @return the role assigned to this role id
     */
    public static Role getRole(UserDTO userDTO) {
        int roleId = userDTO.getRoleId();
        return Role.values()[--roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
