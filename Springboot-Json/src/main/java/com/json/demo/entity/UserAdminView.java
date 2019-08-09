package com.json.demo.entity;


import com.fasterxml.jackson.annotation.JsonView;
import com.json.demo.common.View;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class UserAdminView {

	public interface UserSimpView{};

	private Integer id;
	@JsonView(value = View.Base.class )
	private String username;
	@JsonView(value = View.Base.class )
	private String password;

	@JsonView(value = View.Base.class )
	private String note;
	@JsonView(value = View.Base.class )
	private Map<String,String> map;
	@JsonView(value = View.Base.class )
	private String []  ss;
	@JsonView(value = View.Base.class )
	private int [] intDemo;
	@JsonView(value = View.Base.class )
	private Integer b =null;
	@JsonView(value = View.Base.class )
	private boolean bbbb ;
	@JsonView(value = View.Base.class )
	private List<String> dd;
}
