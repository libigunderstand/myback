package com.rzon.myback.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class Base<T> extends Model {
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected LocalDateTime create_time;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime update_time;
}
