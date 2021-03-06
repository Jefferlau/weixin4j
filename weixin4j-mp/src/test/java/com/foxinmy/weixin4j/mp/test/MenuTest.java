package com.foxinmy.weixin4j.mp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 自定义菜单测试
 * 
 * @className MenuTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class MenuTest extends TokenTest {

	private MenuApi menuApi;
	private List<Button> btnList;

	@Before
	public void init() {
		menuApi = new MenuApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		btnList = new ArrayList<Button>();
		String domain = "http://wx.jdxg.doubimeizhi.com";
		btnList.add(new Button("立即下单", domain, ButtonType.view));

		btnList.add(new Button("个人中心", domain + "/user", ButtonType.view));

		Button button = new Button("小哥介绍", domain, ButtonType.view);
		button.pushSub(new Button("小哥介绍", "http://mp.weixin.qq.com/s?__biz=MzI2MTA5OTM4OQ==&mid=400990970&idx=1&sn=5c7fd72e782c49f7c933b91c63eddc80#rd", ButtonType.view));
		button.pushSub(new Button("服务流程", "FLOW", ButtonType.click));
		button.pushSub(new Button("在线客服", "KF", ButtonType.click));
		btnList.add(button);

		JsonResult result = menuApi.createMenu(btnList);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void create1() throws WeixinException {
		btnList = new ArrayList<Button>();

		Button b1 = new Button("我要订餐", "ORDERING", ButtonType.click);
		btnList.add(b1);
		Button b2 = new Button("查询订单", "http://www.lendocean.com/order/list",
				ButtonType.view);
		btnList.add(b2);
		Button b3 = new Button("最新资讯", "NEWS", ButtonType.click);
		btnList.add(b3);
		JsonResult result = menuApi.createMenu(btnList);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void get() throws WeixinException {
		btnList = menuApi.getMenu();
		for (Button btn : btnList) {
			System.out.println(btn);
		}
		Assert.assertEquals(3, btnList.size());
		// Button [name=我的门店, type=view,
		// content=http://dianzhang.canyi.net/setting/index, subs=[]]
		// Button [name=每日签到, type=click, content=CHECKIN, subs=[]]
		// Button [name=今日订单, type=null, content=null, subs=[Button [name=今日订单,
		// type=view, content=http://dianzhang.canyi.net/order/index, subs=[]],
		// Button [name=营业统计, type=view,
		// content=http://dianzhang.canyi.net/stats/index, subs=[]]]]

	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = menuApi.deleteMenu();
		Assert.assertEquals(0, result.getCode());
	}
}
