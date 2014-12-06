package net.wecash.server.sns.dao;

import net.wecash.server.mysql.model.SnsAuthInfo;

public interface ThirdSnsDAO {

	public SnsAuthInfo getSnsInfo(String uid);

	public void deleteSnsInfo(String uid, int type);

	public void updateSnsInfo(String uid, String token);

	public void addSnsInfo(SnsAuthInfo sai);

	public boolean isThirdIdExists(String uid);

	public Long getUserIdByUidFromSnsAuthInfo(String uid);

	public void deleteSnsInfoByUserId(Long userId);

}
