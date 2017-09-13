package com.common.utils.enity;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.common.utils.controller.validate.CheckPassword;
import com.common.utils.controller.validate.Forbidden;

//{"name":"subAdmin","mobile":"18516557591","email":"tec-intern01@mytianhui.com","password":"123454","confirPassword":"123454","created":"2017-07-25"}
@CheckPassword()
public class User {

    /**
     * id.
     */
    private Integer id;

    @Min(value = 18, message = "年龄最小值必须是18")
    @Max(value = 60, message = "年龄最大值必须是60")
    private Integer age;

    /**
     * 用户名.
     */
    @NotNull(message = "用户名不能为空")
    @Forbidden()
    @Length(min = 5, max = 20, message = "用户名长度必须在5-20之间")
    @Pattern(regexp = "^[a-zA-Z_]\\w{4,19}$", message = "用户名必须以字母下划线开头，可由字母数字下划线组成")
    private String name;

    /**
     * 手机号.
     */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机格式不正确")
    private String mobile;

    /**
     * 登录邮箱.
     */
    @Email(message = "email地址无效！")
    private String email;

    /**
     * 密码.
     */
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间！")
    private String password;

    /**
     * 确认密码.
     */
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间！")
    private String confirPassword;

    /**
     * 创建时间.
     */
    @Future
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirPassword() {
        return confirPassword;
    }

    public void setConfirPassword(String confirPassword) {
        this.confirPassword = confirPassword;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
