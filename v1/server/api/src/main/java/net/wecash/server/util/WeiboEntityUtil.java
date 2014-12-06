package net.wecash.server.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WeiboEntityUtil {
	@SuppressWarnings("rawtypes")
	public static List<String> tagFormat(List<Map> weiboTags) {
		List<String> names = new ArrayList<>();
		for (Map map : weiboTags) {
			Iterator i = map.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry entty = (Map.Entry) i.next();
				if (!entty.getKey().equals("weight")
						&& !entty.getKey().equals("flag")) {
					names.add(entty.getValue().toString());
				}
			}
		}
		return names;
	}
}
