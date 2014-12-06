package net.wecash.server.controller;

import java.util.List;

import javax.validation.Valid;

import net.wecash.common.bean.BasicResultBean;
import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.amqp.AmqpMessageSender;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.TagCreateBean;
import net.wecash.server.bean.TagUpdateBean;
import net.wecash.server.bean.UserTagReturnBean;
import net.wecash.server.facade.TagFacade;
import net.wecash.server.facade.UserTagFacade;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.tag.funny.FunnyTag;
import net.wecash.server.tag.funny.Bean.FunnyTagCreateBean;
import net.wecash.server.tag.funny.dao.FunnyTagDAO;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author xkk
 * 
 */
@Controller
public class TagController extends HandleExceptionController {
	@Autowired
	AuthValidater authValidater;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	TagFacade tagFacade;
	@Autowired
	UserTagFacade userTagFacade;
	@Autowired
	AmqpMessageSender amqpMessageSender;
	@Autowired
	FunnyTagDAO funnyTagDAO;

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TagController.class);

	@RequestMapping(value = "/tag", method = RequestMethod.POST)
	public @ResponseBody
	Object add(@RequestParam(required = false) String access_token,
			@Valid @RequestBody TagCreateBean tagCreateBean) {
		authValidater.validateSystemToken(access_token);
		Tag tag = mapper.convertValue(tagCreateBean, Tag.class);
		if (tagFacade.isTagNameExists(tag.getName())) {
			throw new ErrorCodeException(new Error(ErrorCode.TAG_NUM_ALREADY_EXISTS, tag.getName()));
		}
		tagFacade.addTag(tag);
		return new OnlyResultBean(tag);
	}

	@RequestMapping(value = "/tag/{id}/update", method = RequestMethod.POST)
	public @ResponseBody
	Object update(@PathVariable Long id,
			@RequestParam(required = false) String access_token,
			@Valid @RequestBody TagUpdateBean tagUpdateBean) {

		 authValidater.validateSystemToken(access_token);
		Tag tag = mapper.convertValue(tagUpdateBean, Tag.class);
		if (tag == null) {
			throw new ErrorCodeException(new Error(ErrorCode.TAG_NUM_DOES_NOT_EXISTS));
		}
		tag.setId(id);
		tagFacade.updateTag(tag);
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/tag/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object delete(@PathVariable Long id,
			@RequestParam(required = false) String access_token) {
		 authValidater.validateSystemToken(access_token);
		tagFacade.deleteTag(id);
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/tag/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object get(@PathVariable Long id,
			@RequestParam(required = false) String access_token) {
		authValidater.validateToken(access_token);
		Tag tag = tagFacade.getTag(id);
		return new OnlyResultBean(tag);
	}

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public @ResponseBody
	Object gets(@RequestParam(required = false) List<Long> ids,
			@RequestParam(required = false) Integer type,
			@RequestParam String access_token,
			@RequestParam (required = false) String name,
			@RequestParam(required = false, defaultValue = "0") Integer cursor,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		// TODO:返回cursor游标之后的集合
		authValidater.validateToken(access_token);
		List<Tag> tags=tagFacade.getTagsByIdsAndType(ids, name, type, cursor, limit > 10 ? 10 : limit);
		long total=tagFacade.getAll(name, type);
		return new BasicResultBean(total,cursor,limit,tags);
	}

	@RequestMapping(value = "/user-tag/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object userTag(@PathVariable Long id,
			@RequestParam(required = true) String access_token) {
		authValidater.validateToken(id, access_token);
		List<UserTagReturnBean> list = userTagFacade.getTagInfo(id);
		return new OnlyResultBean(list);
	}

	@RequestMapping(value = "/user-tag/{id}", method = RequestMethod.POST)
	public @ResponseBody
	Object usetTagSave(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@RequestParam(required = true) List<Long> tags) {
		authValidater.validateToken(id, access_token);
		for (Long tag : tags) {
			Tag tagBean= userTagFacade.getTag(tag);
			if(tagBean!=null){
				userTagFacade.save(id, tag);
			}else{
				throw new ErrorCodeException(ErrorCode.TAG_NUM_DOES_NOT_EXISTS);
			}
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/user-tag/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object userTagDelete(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@RequestParam(required = true) List<Long> tags) {
		authValidater.validateToken(id, access_token);
		userTagFacade.delete(id, tags);
		return new OnlyResultBean("ok");
	}
	
	@RequestMapping(value = "/tags/funnny", method = RequestMethod.GET)
	public @ResponseBody
	Object funnyTag(
			@RequestParam(required = true) String access_token) {
		authValidater.validateToken(access_token);
		return new OnlyResultBean(funnyTagDAO.getFunnyTags());
	}
	@RequestMapping(value = "/tags/funny", method = RequestMethod.POST)
	public @ResponseBody
	Object addFunnyTag(
			@RequestParam(required = true) String access_token,
			@Valid @RequestBody FunnyTagCreateBean ftcb) {
		authValidater.validateSystemToken(access_token);
		FunnyTag ft = mapper.convertValue(ftcb, FunnyTag.class);
		funnyTagDAO.addFunnyTag(ft);
		return new OnlyResultBean("ok");
	}
	@RequestMapping(value = "/tags/funny/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteFunnyTag(
			@PathVariable ObjectId id,
			@RequestParam(required = true) String access_token) {
		authValidater.validateSystemToken(access_token);
		funnyTagDAO.deleteFunnyTag(id);
		return new OnlyResultBean("ok");
	}
}
