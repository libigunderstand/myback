package com.rzon.myback.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

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

    @NotBlank(message = "tickname: 不能为空")
    @Length(min = 6, max = 20, message = "tickname：长度为6-20个字符")
    @TableField(value = "tickname")
    private String tickname;

    @NotBlank(message = "password: 不能为空")
    @Length(min = 6, max = 20)
    @TableField(value = "password")
    private String password;

    private Integer age;

    private Integer gender;

}
