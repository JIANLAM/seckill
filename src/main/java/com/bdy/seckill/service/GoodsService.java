package com.bdy.seckill.service;

import com.bdy.seckill.dao.GoodsDao;
import com.bdy.seckill.domain.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.findGoodsList();
	}


}
