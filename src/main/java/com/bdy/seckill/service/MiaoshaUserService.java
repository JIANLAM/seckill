package com.bdy.seckill.service;

import com.bdy.seckill.dao.MiaoshaUserDao;
import com.bdy.seckill.domain.MiaoshaUser;
import com.bdy.seckill.domain.vo.LoginVo;
import com.bdy.seckill.utils.commons.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserService {
	

	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	

	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}


	public boolean login( LoginVo loginVo) {
		if(loginVo == null) {
			return false;
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			return false;
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			return false;
		}

		return true;
	}


}
