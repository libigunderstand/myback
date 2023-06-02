package com.rzon.myback.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author pc
 */
@Data
@NoArgsConstructor
@TableName("user")
public class User extends Base implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @NotBlank(message = "username: 不能为空")
    @Pattern(regexp = "^[一-龥]+$", message = "username：必须为中文")
    @Length(min = 2, max = 10, message = "username：长度为2-10个字符")
    @TableField(value = "username")
    private String username;

    @TableField(value = "org_id")
    private String org_id;

    @NotBlank(message = "tickname: 不能为空")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "tickname: 只能为字母组合")
    @Length(min = 6, max = 20, message = "tickname：长度为6-20个字符")
    @TableField(value = "tickname")
    private String tickname;

    @NotBlank(message = "password: 不能为空")
    @Length(min = 6, max = 20)
    @TableField(value = "password")
    private String password;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "gender") //0 女,1 男
    private Integer gender;

    @TableField(value = "login_time")
    private LocalDateTime login_time;
}
