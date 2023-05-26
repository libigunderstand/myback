package com.rzon.myback.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pc
 */
@Data
@NoArgsConstructor
@TableName("user")
public class User extends Base implements Serializable {

    @NotNull
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String username;

    private String tickname;

    private String password;

    private Integer age;

    private Integer gender;

}
