package com.bdy.seckill.service;

import com.alibaba.druid.util.StringUtils;
import com.bdy.seckill.common.CodeMsg;
import com.bdy.seckill.common.exception.GlobalException;
import com.bdy.seckill.dao.MiaoshaUserDao;
import com.bdy.seckill.domain.MiaoshaUser;
import com.bdy.seckill.domain.vo.LoginVo;
import com.bdy.seckill.utils.commons.MD5Util;
import com.bdy.seckill.utils.commons.UUIDUtil;
import com.bdy.seckill.utils.redis.MiaoshaUserKey;
import com.bdy.seckill.utils.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
	
	public static final String COOIE_NAME_TOKEN ="token";

	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	

	@Autowired
	RedisService redisService;

	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}


	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//通过redis做分布式session
		String token = UUIDUtil.uuid();
		addCookie(response,token,user);
		return true;
	}


	public MiaoshaUser getUserByToken(HttpServletResponse response,String token) {
		if (StringUtils.isEmpty(token)){
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		if (user != null){
			addCookie(response,token,user);
		}
		return user;
	}

	private void addCookie(HttpServletResponse response,String token,MiaoshaUser user){

		redisService.set(MiaoshaUserKey.token,token,user);
		Cookie cookie = new Cookie(COOIE_NAME_TOKEN,token);
		cookie.setPath("/");
		cookie.setMaxAge(MiaoshaUserKey.TOKEN_EXPIRE);
		response.addCookie(cookie);
	}
}
