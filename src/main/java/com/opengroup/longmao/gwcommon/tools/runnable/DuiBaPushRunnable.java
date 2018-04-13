/**
 * @Title: DuiBaPushRunnable.java 
 * @Package com.opengroup.longmao.gwcommon.tools.runnable 
 * @Description:
 * @author Mr.Zhu
 * @version V1.5
 */
package com.opengroup.longmao.gwcommon.tools.runnable;

import java.io.IOException;

import com.opengroup.longmao.gwcommon.tools.http.HttpRequest;

/**
 * @ClassName: DuiBaPushRunnable
 * @Description: 兑吧充值结果推送线程
 * @author Mr.Zhu
 */
public class DuiBaPushRunnable implements Runnable {
	private String url;
	private String param;

	public DuiBaPushRunnable(String url, String param) {
		this.url = url;
		this.param = param;
	}

	public void sleep() {
		// 睡眠10分钟 300000
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: run
	 * @Description: TODO
	 */
	@Override
	public void run() {
		try {
			String content = null;
			for (int i = 0; i < 4; i++) {
				sleep();
				//content = HttpURLConnectionRequest.sendHttp(url, param, method);
				content = HttpRequest.httpPOST(url, param);
				if ("ok".equalsIgnoreCase(content)) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
