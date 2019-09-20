package com.springboot.enmu.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.springboot.enmu.config.PlatformType;
import lombok.Data;



import java.io.Serializable;


@TableName("version")
@Data
public class App extends Model<App> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("platform_type")
	private PlatformType platformType;


}
