package net.wecash.data.analyzer.corpus.source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.wecash.common.third.ThirdSnsType;
import net.wecash.data.analyzer.corpus.ConfigContext;
import net.wecash.data.collector.WeiboRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeiboCorpus implements BasicCorpus{
	
	@Autowired
	WeiboRequestService weiboRequestService;

	@Override
	public boolean canGet(int type) {
		return type == ThirdSnsType.WEIBO;
	}

	@Override
	public List<String> getTimelinesCorpus(ConfigContext cc) throws Exception {
		List<String> timelines = getSourceCorpusFromTimelines(weiboRequestService.getUserTimeline(cc.getUid(), cc.getToken()));
		return timelines;
	}
	
	@Override
	public List<String> getFavoritesCorpus(ConfigContext cc) throws Exception {
		List<String> favorites = getSourceCorpusFromFavorites(weiboRequestService.getUserFavorites(cc.getUid(), cc.getToken()));
		return favorites;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getTags(ConfigContext cc) throws Exception {
		List<Map> list = weiboRequestService.getUserFavorites(cc.getUid(), cc.getToken());
		List<String> tags1 = getTagsFromFavorites(list);
		List<String> tags2 = getTagsFromWeibo(weiboRequestService.getUserTagsFromWeibo(cc.getUid(), cc.getToken()));
		tags1.addAll(tags2);
		return tags1;
	}
	
	@Override
	public Map<String, Object> getUserInfo(ConfigContext cc) throws Exception {
		return weiboRequestService.getUserInfo(cc.getUid(), cc.getToken());
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getFriends(ConfigContext cc) throws Exception {
		return weiboRequestService.getFriendsBilateral(cc.getUid(), cc.getToken());
	}
	
	
	@SuppressWarnings("rawtypes")
	private List<String> getSourceCorpusFromTimelines(List<Map> timelines){
		List<String> statuses = new ArrayList<>();
		if(timelines != null){
			for(Map m : timelines){
				statuses.add(m.get("text").toString());
				if(m.get("retweeted_status") != null){
					Map reTweeted = (Map)m.get("retweeted_status");
					statuses.add(reTweeted.get("text").toString());
				}
			}
		}
		return statuses;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getTagsFromFavorites(List<Map> favorites){
		List<String> tagsList = new ArrayList<>();
		if(favorites != null){
			for(Map m : favorites){
				List<Map> tags = (List)m.get("tags");
				for(Map tm : tags){
					tagsList.add(tm.get("tag").toString());
				}
			}
		}
		return tagsList;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> getTagsFromWeibo(List<Map> normalTags){
		List<String> tagsList = new ArrayList<>();
		if(normalTags != null){
			for (Map map : normalTags) {
				Iterator i = map.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry entty = (Map.Entry) i.next();
					if (!entty.getKey().equals("weight") && !entty.getKey().equals("flag")) {
						tagsList.add(entty.getValue().toString());
					}
				}
			}
		}
		return tagsList;
	}
	
	@SuppressWarnings("rawtypes")
	private List<String> getSourceCorpusFromFavorites(List<Map> favorites){
		List<String> favoritesList = new ArrayList<>();
		if(favorites != null){
			for(Map m : favorites){
				Map status = (Map)m.get("status");
				favoritesList.add(status.get("text").toString());
			}
		}
		return favoritesList;
	}
}
