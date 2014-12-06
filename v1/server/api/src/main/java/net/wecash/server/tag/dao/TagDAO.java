package net.wecash.server.tag.dao;

import java.util.List;

import net.wecash.server.mysql.model.Tag;

public interface TagDAO {

	public void deleteTag(Long id);

	public Tag getTag(Long id);

	public Tag getTagByName(String name);

	public void updateTag(Tag tag);

	public void addTag(Tag tag);

	public boolean isTagNameExists(String name);

	public Object groupTypesByNames(List<String> names);

	List<Tag> getTagsByIdsAndType(List<Long> ids,String name, Integer type, int first, int Max);

	public Object groupTypesByUserId(Long userId);

	public long getAll(String name, Integer type);

}
