package com.opengroup.longmao.gwcommon.tools.sig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.tls.sigcheck.tls_sigcheck;

// 由于生成 sig 和校验 sig 的接口使用方法类似，故此处只是演示了生成 sig 的接口调用
// 使用的编译命令是
// javac -encoding utf-8 Demo.java
// 使用的运行命令是
// java Demo
@Configuration
public class TlsSigUtils {
	
	@Value("${qcloud.tls.jni_lib_path}")
	private String jniLibPath;
	
	@Value("${qcloud.tls.pri_key_path}")
	private String priKeyPath;
	
	@Value("${qcloud.tls.pub_key_path}")
	private String pubKeyPath;
	
	@Value("${qcloud.tls.sdk_app_id}")
	private String sdkAppId;
	
	
	public String createSig(String id) {
		return getSig(sdkAppId.trim(), id);
	}
	
	public boolean checkSig(String sig, String id) {
		return checkSignature(sig, sdkAppId.trim(), id);
	}

	/**
	 * 生成 SIG
	 * @param sdkappid
	 * @param id
	 * @return String
	 */
	public String getSig(String sdkappid, String id) {
		tls_sigcheck demo = new tls_sigcheck();
		// 使用前请修改动态库的加载路径
		demo.loadJniLib(jniLibPath);

		BufferedReader br = null;
		StringBuilder strBuilder;
		try {
			File priKeyFile = new File(priKeyPath);
			strBuilder = new StringBuilder();

			br = new BufferedReader(new FileReader(priKeyFile));
			String s = "";
			while ((s = br.readLine()) != null) {
				strBuilder.append(s + '\n');
			}
			String priKey = strBuilder.toString();
			int ret = demo.tls_gen_signature_ex2(sdkappid, id, priKey);
			if (0 != ret) {
				GwsLogger.info("ret={},errmsg={}", ret, demo.getErrMsg());
				return null;
			} else {
				GwsLogger.info("ret={},InitTime={},ExpireTime={}", ret, demo.getInitTime(), demo.getExpireTime());
				return demo.getSig();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 校验SIG在效性
	 * @param sig
	 * @param sdkappid
	 * @param id
	 * @return
	 */
	public boolean checkSignature(String sig, String sdkappid, String id) {
		tls_sigcheck demo = new tls_sigcheck();
		// 使用前请修改动态库的加载路径
		demo.loadJniLib(jniLibPath);

		BufferedReader br = null;
		StringBuilder strBuilder;
		try {
			File pubKeyFile = new File(pubKeyPath);
			br = new BufferedReader(new FileReader(pubKeyFile));
			strBuilder = new StringBuilder();
			String s = "";
			while ((s = br.readLine()) != null) {
				strBuilder.append(s + '\n');
			}
			br.close();
			String pubKey = strBuilder.toString();
			int ret = demo.tls_check_signature_ex2(sig, pubKey, sdkappid, id);
			if (0 != ret) {
				GwsLogger.info("ret={},errmsg={}", ret, demo.getErrMsg());
				return false;
			} else {
				GwsLogger.info("nverify ok:expire time={},init time={}", demo.getExpireTime(), demo.getInitTime());
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void demo() throws Exception {
		tls_sigcheck demo = new tls_sigcheck();

		// 使用前请修改动态库的加载路径
		demo.loadJniLib(jniLibPath);

		File priKeyFile = new File(priKeyPath);
		StringBuilder strBuilder = new StringBuilder();
		String s = "";

		BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
		while ((s = br.readLine()) != null) {
			strBuilder.append(s + '\n');
		}
		br.close();
		String priKey = strBuilder.toString();
		GwsLogger.info("priKey={}", priKey);
		int ret = demo.tls_gen_signature_ex2("1400000267", "xiaojun", priKey);

		if (0 != ret) {
			GwsLogger.info("ret={},errmsg={}", ret, demo.getErrMsg());
		} else {
			GwsLogger.info("sig={}", ret, demo.getSig());
		}

		File pubKeyFile = new File(pubKeyPath);
		br = new BufferedReader(new FileReader(pubKeyFile));
		strBuilder.setLength(0);
		while ((s = br.readLine()) != null) {
			strBuilder.append(s + '\n');
		}
		br.close();
		String pubKey = strBuilder.toString();
		GwsLogger.info("pubKey={}", pubKey);
		ret = demo.tls_check_signature_ex2(demo.getSig(), pubKey, "1400000267", "xiaojun");
		if (0 != ret) {
			GwsLogger.info("ret={},errmsg={}", ret, demo.getErrMsg());
		} else {
			GwsLogger.info("nverify ok:expire time={},init time={}", demo.getExpireTime(), demo.getInitTime());
		}
	}

}
