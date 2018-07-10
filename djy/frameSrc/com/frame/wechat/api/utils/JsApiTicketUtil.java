package com.frame.wechat.api.utils;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.frame.wechat.api.MpApi;
import com.frame.wechat.api.json.JsApiTicket;

/**
 * 访问JsApiTicket，定时刷新
 * @author puzd
 *
 */
public class JsApiTicketUtil {
	private static ScheduledExecutorService timer = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactory() {
				@Override
				public Thread newThread(Runnable run) {
					Thread t = new Thread(run);
					t.setDaemon(true);
					return t;
				}
			});

	static {
		init();
	}
	
	public static String getTicketStr() {
		return queryJsApiTicket().getTicket();
	}
	
	public static synchronized String refreshAndGetTicket() {
		JsApiTicket tk = queryJsApiTicket();
		if (tk == null
				|| (System.currentTimeMillis() - tk.getCreateTime() > 10000)) {
			refreshTicket();
		}
		return getTicketStr();
	}
	
	private static void refreshTicket() {
		try {
			JsApiTicket jsApiTicket = MpApi.getJsApiTicket();
			saveJsApiTicket(jsApiTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void init() {
		if (queryJsApiTicket() == null) {
			refreshTicket();
		}
		initTimer(queryJsApiTicket());
	}

	/**
	 * 定时刷新ticket
	 */
	private static void initTimer(JsApiTicket tk) {
		// 刷新频率：有效期的三分之二
		long refreshTime = tk.getExpires_in() * 2 / 3;
		
		// 延迟时间100秒内随机
		long delay = (long) (100 * (new Random().nextDouble()));
		
		timer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				JsApiTicket jsApiTicket = queryJsApiTicket();
				// 200秒内只刷新一次，防止分布式集群定时任务同一段时间内重复刷新
				if (jsApiTicket == null
						|| (System.currentTimeMillis() - jsApiTicket.getCreateTime() > 200000)) {
					refreshTicket();
				}
			}
		}, delay, refreshTime, TimeUnit.SECONDS);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				timer.shutdown();
			}
		}));
	}
	
	
	private static JsApiTicket ticket;
	
	
	public static JsApiTicket queryJsApiTicket() {
		return ticket;
	}
	
	
	private static void saveJsApiTicket(JsApiTicket jsApiTicket) {
		ticket = jsApiTicket;
	}
	
	
}
