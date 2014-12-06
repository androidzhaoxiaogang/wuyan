/**
 * 
 */
package net.wecash.server.tag.funny.dao;

import java.util.List;

import org.bson.types.ObjectId;

import net.wecash.server.tag.funny.FunnyTag;

/**
 * @author franklin.li
 * 
 */
public interface FunnyTagDAO {

	public List<FunnyTag> getFunnyTags();

	public void addFunnyTag(FunnyTag funnyTag);

	public void deleteFunnyTag(ObjectId id);

	public List<FunnyTag> getFunnyTags(Integer clazz, Integer type, Integer value);

	public List<FunnyTag> getFunnyTags(Integer clazz, List<Integer> types, Integer value);
}
