package com.common.utils.enity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.common.utils.controller.validate.ListNotHasNull;

/**
 * 项目问卷奖励.
 *
 * @author:haitao.liu
 *
 */
public class ProjectSurveyRewardDto {

    @NotNull(message = ("项目ID不能为空"))
    // @NotNull(message=("{project.id.null}"))
    private Integer projectId;

    // @NotBlank(message = ("问卷ID不能为空"))
    private String surveyId;

    @ListNotHasNull(message = "问卷ID不能为空")
    // @ListNotHasNull(message="{project.surveyId.null}")
    private List<String> surveyIdList;

    @NotBlank(message = ("奖励名称不能"))
    // @NotBlank(message=("{project.reward.name.null}"))
    private String rewardName;

     @NotNull(message="{project.reward.id.null}")
//    @NotNull(message = ("奖励ID不能为空"))
    private Integer rewardId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public List<String> getSurveyIdList() {
        return surveyIdList;
    }

    public void setSurveyIdList(List<String> surveyIdList) {
        this.surveyIdList = surveyIdList;
    }

}
