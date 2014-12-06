package net.wecash.server.facade;

import java.util.List;

import net.wecash.common.service.HashCacheNames;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.tag.dao.TagDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TagFacade {

	@Autowired
	TagDAO tagDAO;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	private String tagCacheName = HashCacheNames.TAG;

	public Object groupTypesByNames(List<String> names) {
		return tagDAO.groupTypesByNames(names);
		 
	}

	public List<Tag> getTagsByIdsAndType(List<Long> ids,String name, Integer type,Integer first,Integer Max) {
		return tagDAO.getTagsByIdsAndType(ids,name, type,first,Max);
	}

	public boolean isTagNameExists(String name) {
		return tagDAO.isTagNameExists(name);
		 
	}

	@SuppressWarnings("unchecked")
	public void addTag(Tag tag) {
		Assert.notNull(tag);
		tagDAO.addTag(tag);
		redisTemplate.opsForHash().put(tagCacheName, tag.getId(), tag);
	}

	@SuppressWarnings("unchecked")
	public void updateTag(Tag tag) {
		Assert.notNull(tag);
		Assert.notNull(tag.getId());
		tagDAO.updateTag(tag);
		tag = tagDAO.getTag(tag.getId());
		redisTemplate.opsForHash().put(tagCacheName, tag.getId(), tag);
	}

	@SuppressWarnings("unchecked")
	public void deleteTag(Long id) {
		Assert.notNull(id);
		tagDAO.deleteTag(id);
		redisTemplate.opsForHash().delete(tagCacheName, id);
	}

	@SuppressWarnings("unchecked")
	public Tag getTag(Long id) {
		Assert.notNull(id);
		Tag tag = null;
		Object o = redisTemplate.opsForHash().get(tagCacheName, id);
		if(o == null){
			tag = tagDAO.getTag(id);
		}else{
			tag = (Tag)o;
		}
		return tag;
	}

	public Tag getTagByName(String tagName) {
		return tagDAO.getTagByName(tagName);
	}

	public long getAll(String name, Integer type) {
		return tagDAO.getAll(name,type);
	}
	
}
