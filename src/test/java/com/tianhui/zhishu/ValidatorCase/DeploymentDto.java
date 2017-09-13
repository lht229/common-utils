package com.tianhui.zhishu.ValidatorCase;

import java.util.HashSet;
import java.util.Set;

import com.common.utils.utils.common.annotation.Validator;

/**
 * dto.
 *
 */
public class DeploymentDto {

    /**
     * 项目id.
     */
    @Validator(nullable = false)
    private Integer projectId;

    /**
     * 门店id,以逗号分隔.
     */
    @Validator(nullable = false)
    private String shopIds;

    /**
     * 项目类型.<br>
     * 1、自己创建的<br>
     * 2、被授权的.<br>
     */
    @Validator(nullable = false)
    private Integer from;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }

    /**
     * 接受门店id，返回set集合.
     */
    public Set<String> getShopIdSet() {
        Set<String> shopIdSet = new HashSet<String>();
        String[] split = shopIds.split(",");
        for (String str : split) {
            shopIdSet.add(str);
        }
        return shopIdSet;
    }

    @Override
    public String toString() {
        return "DeploymentDto [projectId=" + projectId + ", shopIds=" + shopIds
                + ", from=" + from + "]";
    }

}
