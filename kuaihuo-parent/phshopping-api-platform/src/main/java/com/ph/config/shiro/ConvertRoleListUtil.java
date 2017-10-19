package com.ph.config.shiro;

import com.ph.shopping.facade.permission.vo.BtnVO;
import com.ph.shopping.facade.permission.vo.MenuVO;
import com.ph.shopping.facade.permission.vo.RoleVO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertRoleListUtil {

    /**
     * 将角色List转换成Set<String>
     *
     * @param roles
     * @return
     */
    public static Set<String> ConvertRoleListToSet(List<RoleVO> roles) {
        Set<String> set = new HashSet<String>();
        for (RoleVO role : roles) {
            set.add(role.getRoleCode().toString());
        }
        return set;
    }

    /**
     * 将角色权限List转换成List<String>
     *
     * @param permissions
     */
    public static List<String> ConvertPermissionListToStringList(List<MenuVO> permissions) {
        List<String> list = new ArrayList<String>();
        //---这里用的菜单表，后面要改成菜单权限表和按钮权限表
        for (MenuVO permission : permissions) {

            String permissionName = permission.getMenuUrl();

            if (permissionName != null && !"".equals(permissionName.trim())) {
                list.add(permissionName);
            }
        }
        return list;
    }


    /**
     * 将按钮权限List转换成List<String>
     *
     * @param permissions
     *
     * @return
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/15 13:54
     */
    public static List<String> ConvertBtnPermissionListToStringList(List<BtnVO> permissions) {
        List<String> list = new ArrayList<String>();
        //---这里用的按钮表
        for (BtnVO permission : permissions) {

            String permissionName = permission.getBtnCode();

            if (permissionName != null && !"".equals(permissionName.trim())) {
                list.add(permissionName);
            }
        }
        return list;
    }
}
