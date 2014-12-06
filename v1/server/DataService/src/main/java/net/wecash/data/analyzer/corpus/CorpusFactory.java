package net.wecash.data.analyzer.corpus;

import java.util.List;
import java.util.Map;

import net.wecash.data.analyzer.corpus.source.BasicCorpus;
import net.wecash.data.analyzer.corpus.source.WeiboCorpus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author franklin.li
 *
 */
@Component
public class CorpusFactory {
//	private static Logger logger = LoggerFactory.getLogger(CorpusFactory.class);
    @Autowired
    WeiboCorpus weiboCorpus;
    
    public Map<String, Object> getSnsUserInfo(String uid, String token, int type){
    	Map<String, Object> userInfo = null;
    	BasicCorpus bc = getCorpus(type);
    	ConfigContext cc = new ConfigContext();
    	cc.setToken(token);
    	cc.setType(type);
    	cc.setUid(uid);
    	try{
    		userInfo = bc.getUserInfo(cc);
    	}catch (Exception e) {
    		e.printStackTrace();
    		
		}
    	return userInfo;
    }
    
    @SuppressWarnings("rawtypes")
	public List<Map> getSnsFriends(String uid, String token, int type){
    	List<Map> snsFriends = null;
    	BasicCorpus bc = getCorpus(type);
    	ConfigContext cc = new ConfigContext();
    	cc.setToken(token);
    	cc.setType(type);
    	cc.setUid(uid);
    	try{
    		snsFriends = bc.getFriends(cc);
    	}catch (Exception e) {
    		e.printStackTrace();
    		
		}
    	return snsFriends;
    }
    
    public List<String> getSnsFavoritesCorpus(String uid, String token, int type){
    	List<String> corpusList = null;
    	BasicCorpus bc = getCorpus(type);
    	ConfigContext cc = new ConfigContext();
    	cc.setToken(token);
    	cc.setType(type);
    	cc.setUid(uid);
    	try{
    		corpusList = bc.getFavoritesCorpus(cc);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return corpusList;
    }
    
    public List<String> getSnsTimelinesCorpus(String uid, String token, int type){
    	List<String> corpusList = null;
    	BasicCorpus bc = getCorpus(type);
    	ConfigContext cc = new ConfigContext();
    	cc.setToken(token);
    	cc.setType(type);
    	cc.setUid(uid);
    	try{
    		corpusList = bc.getTimelinesCorpus(cc);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return corpusList;
    }
    
    public List<String> getSnsTags(String uid, String token, int type){
    	List<String> tagList = null;
    	BasicCorpus bc = getCorpus(type);
    	ConfigContext cc = new ConfigContext();
    	cc.setToken(token);
    	cc.setType(type);
    	cc.setUid(uid);
    	try{
    		tagList = bc.getTags(cc);
    	}catch (Exception e) {
    		e.printStackTrace();
    		
		}
    	return tagList;
    }
    
	public BasicCorpus getCorpus(int type) {
		switch (type) {
		case 1:
			return weiboCorpus;

		default:
			return weiboCorpus;
		}
	}
    
}
