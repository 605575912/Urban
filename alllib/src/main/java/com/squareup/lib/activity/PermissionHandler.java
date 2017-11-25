package com.squareup.lib.activity;

import com.squareup.lib.utils.IProguard;

/**
 * Created by liangzhenxiong on 2017/10/14.
 */

public interface PermissionHandler extends IProguard.ProtectClassAndMembers {
    /**
     * @param grantedpermissions 获得授权的权限
     * @param denied_permissions 被拒的权限
     */
    void onPermissionsResult(String[] grantedpermissions, String[] denied_permissions);

}
