package com.bdy.seckill.domain.vo;


import com.bdy.seckill.common.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

	@NotNull
	@IsMobile
	private String mobile;

	@NotNull
	@Length(min = 32)
	private String password;
}
