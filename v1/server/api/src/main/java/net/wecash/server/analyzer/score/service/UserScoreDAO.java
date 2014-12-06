package net.wecash.server.analyzer.score.service;

import java.util.List;

import net.wecash.server.analyzer.score.AnalyzerScoreBean;

/**
 * @author franklin.li
 * 
 */
public interface UserScoreDAO {

	public List<AnalyzerScoreBean> getScores(Long userId, Boolean flag, int cursor, int limit);

	public void addScores(Long userId, List<AnalyzerScoreBean> asbs);

	public AnalyzerScoreBean getScore(Long userId, Long targetId);

	public void readScoresList(Long userId, List<Long> targetIds);

	public boolean needReMatch(Long userId);

	public Long getScoresCount(Long userId);

}
