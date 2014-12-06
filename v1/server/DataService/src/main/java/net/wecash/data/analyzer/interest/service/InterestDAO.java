/**
 * 
 */
package net.wecash.data.analyzer.interest.service;

import java.util.List;

/**
 * @author franklin.li
 * 
 */
public interface InterestDAO {

	public List<InterestEncodeBean> gets(List<Long> userIds, String interestEncodeQueryRegex, Integer limit);

	public InterestEncodeBean get(Long userId);

	public void update(InterestEncodeBean ib);

	public void add(InterestEncodeBean ib);

	public List<InterestEncodeBean> gets(List<Long> userIds);

}
