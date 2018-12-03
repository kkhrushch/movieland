package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.security.RolePermission;

import java.util.List;

public interface SecurityDao {

    List<RolePermission> getRolePermissions();

}
