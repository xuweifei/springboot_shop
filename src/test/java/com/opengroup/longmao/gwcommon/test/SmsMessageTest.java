package com.opengroup.longmao.gwcommon.test;

import com.opengroup.longmao.gwcommon.enums.SmsTypeEnum;
import com.opengroup.longmao.gwcommon.service.impl.LmSmsMessageServiceImpl;
import com.opengroup.longmao.gwcommon.test.base.BaseTestCase;
import com.opengroup.longmao.gwcommon.test.base.TestInject;

public class SmsMessageTest extends BaseTestCase{
	@TestInject
	private LmSmsMessageServiceImpl LmSmsMessageServiceImpl;
	
	public void testSendMessage() {
		try {
			LmSmsMessageServiceImpl.sendLmSmsMessage("1111",null,"13456781709",SmsTypeEnum.VERIFY_CODE.getVal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
