package com.opengroup.longmao.gwcommon.tools.sensitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opengroup.longmao.gwcommon.configuration.log.GwsLogger;
import com.opengroup.longmao.gwcommon.entity.po.SensitivePause;
import com.opengroup.longmao.gwcommon.entity.po.SensitiveWord;
import com.opengroup.longmao.gwcommon.enums.IsDeleteEnum;
import com.opengroup.longmao.gwcommon.enums.IsOrNotEnum;
import com.opengroup.longmao.gwcommon.repository.queryFilter.SensitiveWordQueryFilter;
import com.opengroup.longmao.gwcommon.repository.slave.SensitivePauseRepositorySlave;
import com.opengroup.longmao.gwcommon.repository.slave.SensitiveWordRepositorySlave;
import com.opengroup.longmao.gwcommon.service.RedisReadWriteService;
import com.opengroup.longmao.gwcommon.tools.result.ConfigConstant;

/**
 * 创建时间：2016年8月30日 下午3:01:12 思路： 创建一个FilterSet，枚举了0~65535的所有char是否是某个敏感词开头的状态
 * 判断是否是 敏感词开头 | | 是 不是 获取头节点 OK--下一个字 然后逐级遍历，DFA算法
 * @author andy
 * @version 2.2
 */
@Component
public class WordFilter {

	private final FilterSet set = new FilterSet(); // 存储首字
	private final Map<Integer, WordNode> nodes = new HashMap<Integer, WordNode>(1024, 1); // 存储节点
	private final Set<Integer> stopwdSet = new HashSet<>(); // 停顿词
	private final char SIGN = '*'; // 敏感词过滤替换
	private String prefix = ConfigConstant.SENSITIVE_FILTERING;
	private String realKey_word = ConfigConstant.SENSITIVE_FILTERING_WORD;
	private String realKey_pause = ConfigConstant.SENSITIVE_FILTERING_PAUSE;

	@Autowired
	private SensitivePauseRepositorySlave sensitivePauseRepositorySlave;
	
	@Autowired
	private SensitiveWordRepositorySlave sensitiveWordRepositorySlave;

	@Autowired
	private RedisReadWriteService redis;

	private void init() {
		// 获取敏感词
		// addSensitiveWord(readWordFromFile("wd.txt"));
		// addStopWord(readWordFromFile("stopwd.txt"));
		try {
			addSensitiveWord(readWordFromRedis());
			addStopWord(readPauseFromRedis());
		} catch (Exception e) {
			throw new RuntimeException("初始化过滤器失败");
		}
	}
	
	/**
	 * @Title: readWordFromRedis 
	 * @Description: 增加敏感词 
	 * @return List<String>
	 */
	private List<String> readWordFromRedis() {
		Map<String, String> word = redis.hashGet(realKey_word);
		List<String> wl = new ArrayList<String>();
		if (null == word || word.isEmpty()) {
			SensitiveWordQueryFilter query = new SensitiveWordQueryFilter();
			query.setIsUse(IsOrNotEnum.YES.getType());
			query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			//查询敏感数据敏感词汇
			List<SensitiveWord> l = sensitiveWordRepositorySlave.findAll(query);
			if (CollectionUtils.isNotEmpty(l)) {
				for (SensitiveWord w : l) {
					if (StringUtils.isNotBlank(w.getWord())) {
						wl.add(w.getWord());
						redis.hashSet(realKey_word, String.valueOf(w.getwId()), w.getWord());
					}
				}
				GwsLogger.info("敏感词汇Redis缓存更新");
			}
		} else {
			for (String value : word.values()) {
				wl.add(value);
			}
		}
		return wl;
	}
	
	/**
	 * @Title: readPauseFromRedis 
	 * @Description: 增加停顿词 
	 * @return List<String>
	 */
	private List<String> readPauseFromRedis() {
		//获取Redis停顿词
		//List<String> pl = cache.getList(prefix, "PAUSE");
		List<String> pl = redis.getList(prefix, realKey_pause);
		if (CollectionUtils.isEmpty(pl)) {
			SensitiveWordQueryFilter query = new SensitiveWordQueryFilter();
			query.setIsUse(IsOrNotEnum.YES.getType());
			query.setIsDelete(IsDeleteEnum.UN_DELETE.getVal());
			//查询敏感分隔停顿符数据敏感符
			List<SensitivePause> l = sensitivePauseRepositorySlave.findAll(query);
			if (CollectionUtils.isNotEmpty(l)) {
				for (SensitivePause p : l) {
					if (StringUtils.isNotBlank(p.getSymbol())) {
						pl.add(p.getSymbol());
					}
				}
				// 更新缓存
				//cache.setList(prefix, "PAUSE", sL);
				redis.setList(prefix, realKey_pause, pl);
				GwsLogger.info("敏感词汇分隔停顿符Redis缓存更新");
			}
		}
		return pl;
	}

	/**
	 * 增加敏感词
	 * @param path
	 * @return
	 */
//	private List<String> readWordFromFile(String path) {
//		List<String> words;
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new InputStreamReader(WordFilter.class.getClassLoader().getResourceAsStream(path)));
//			words = new ArrayList<String>(1200);
//			for (String buf = ""; (buf = br.readLine()) != null;) {
//				if (buf == null || buf.trim().equals(""))
//					continue;
//				words.add(buf);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			try {
//				if (br != null)
//					br.close();
//			} catch (IOException e) {
//			}
//		}
//		return words;
//	}

	/**
	 * 增加停顿词
	 * @param words
	 */
	private void addStopWord(final List<String> words) {
		if (CollectionUtils.isNotEmpty(words)) {
			char[] chs;
			for (String curr : words) {
				chs = curr.toCharArray();
				for (char c : chs) {
					stopwdSet.add(charConvert(c));
				}
			}
		}
	}

	/**
	 * 添加DFA节点
	 * @param words
	 */
	private void addSensitiveWord(final List<String> words) {
		if (CollectionUtils.isNotEmpty(words)) {
			char[] chs;
			int fchar;
			int lastIndex;
			WordNode fnode; // 首字母节点
			for (String curr : words) {
				chs = curr.toCharArray();
				fchar = charConvert(chs[0]);
				if (!set.contains(fchar)) {// 没有首字定义
					set.add(fchar);// 首字标志位 可重复add,反正判断了，不重复了
					fnode = new WordNode(fchar, chs.length == 1);
					nodes.put(fchar, fnode);
				} else {
					fnode = nodes.get(fchar);
					if (!fnode.isLast() && chs.length == 1) {
						fnode.setLast(true);
					}
				}
				lastIndex = chs.length - 1;
				for (int i = 1; i < chs.length; i++) {
					fnode = fnode.addIfNoExist(charConvert(chs[i]), i == lastIndex);
				}
			}
		}
	}

	/**
	 * 过滤判断 将敏感词转化为成屏蔽词
	 * @param src
	 * @return
	 */
	public final String doFilter(final String src) {
		char[] chs = src.toCharArray();
		int length = chs.length;
		int currc;
		int k;
		WordNode node;
		for (int i = 0; i < length; i++) {
			currc = charConvert(chs[i]);
			if (!set.contains(currc)) {
				continue;
			}
			node = nodes.get(currc);
			if (null == node)
				continue;
			boolean couldMark = false;
			int markNum = -1;
			if (node.isLast()) {
				couldMark = true;
				markNum = 0;
			}
			k = i;
			for (; ++k < length;) {
				int temp = charConvert(chs[k]);
				if (stopwdSet.contains(temp))
					continue;
				node = node.querySub(temp);
				if (null == node)
					break;
				if (node.isLast()) {
					couldMark = true;
					markNum = k - i;
				}
			}
			if (couldMark) {
				for (k = 0; k <= markNum; k++) {
					chs[k + i] = SIGN;
				}
				i = i + markNum;
			}
		}

		return new String(chs);
	}

	/**
	 * 是否包含敏感词
	 * @param src
	 * @return
	 */
	public final boolean isContains(final String src) {
		//空判断,空判断非敏感
		if (StringUtils.isBlank(src)) {
			return false;
		}
		//敏感词汇和停顿符号初始化
		init();
		char[] chs = src.toCharArray();
		int length = chs.length;
		int currc;
		int k;
		WordNode node;
		for (int i = 0; i < length; i++) {
			currc = charConvert(chs[i]);
			if (!set.contains(currc)) {
				continue;
			}
			node = nodes.get(currc);
			if (null == node)
				continue;
			boolean couldMark = false;
			if (node.isLast()) {
				couldMark = true;
			}
			k = i;
			for (; ++k < length;) {
				int temp = charConvert(chs[k]);
				if (stopwdSet.contains(temp))
					continue;
				node = node.querySub(temp);
				if (null == node)
					break;
				if (node.isLast()) {
					couldMark = true;
				}
			}
			if (couldMark) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 大写转化为小写 全角转化为半角
	 * @param src
	 * @return
	 */
	private int charConvert(char src) {
		int r = BCConvert.qj2bj(src);
		return (r >= 'A' && r <= 'Z') ? r + 32 : r;
	}

}