package net.wecash.data.analyzer.corpus.source;

import java.util.List;
import java.util.Map;

import net.wecash.data.analyzer.corpus.ConfigContext;

public interface BasicCorpus {
    public boolean canGet(int type);

    public List<String> getTags(ConfigContext cc) throws Exception;

    public Map<String, Object> getUserInfo(ConfigContext cc) throws Exception;

    public List<Map> getFriends(ConfigContext cc) throws Exception;

    public List<String> getTimelinesCorpus(ConfigContext cc) throws Exception;

    public List<String> getFavoritesCorpus(ConfigContext cc) throws Exception;
}
