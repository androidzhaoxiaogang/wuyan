package net.wecash.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wecash.common.bean.BasicResultBean;
import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.common.util.DateUtils;
import net.wecash.common.util.FileUtil;
import net.wecash.server.activity.dao.ActivityDAO;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.Activity;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.gridfs.GridFSDBFile;

@Controller
public class ActivityController extends HandleExceptionController {

	@Autowired
	ActivityDAO activityDAO;
	@Autowired
	AuthValidater authValidater;
	@Autowired
	FileDAO imageDAO;

	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	public @ResponseBody
	Object getActivity(@RequestParam String access_token,
			@RequestParam(required = false, defaultValue = "0") Integer cursor,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		authValidater.validateToken(access_token);
		List<Activity> list = activityDAO.getActivity(cursor, limit > 10 ? 10
				: limit);
		long total=activityDAO.getActivityAll();
		return new BasicResultBean(total,cursor,limit,list);
	}

	@RequestMapping(value = "/activity/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object getActivity(@PathVariable Long id, @RequestParam String access_token) {
		authValidater.validateToken(access_token);
		Activity activity = activityDAO.getActivity(id);
		return new OnlyResultBean(activity);
	}

	@RequestMapping(value = "/activity/{id}/cover", method = RequestMethod.GET)
	public void getActivityImg(HttpServletRequest request,
			@PathVariable Long id, @RequestParam String access_token,
			HttpServletResponse response) throws Exception {
		authValidater.validateToken(access_token);
		Activity a = activityDAO.getActivity(id);
		if (a != null && a.getCover() != null) {
			GridFSDBFile gridFSDBFile = imageDAO.getFile(new ObjectId(a.getCover()));
			if (gridFSDBFile != null && gridFSDBFile.getInputStream() != null) {
				InputStream is = gridFSDBFile.getInputStream();
				try {
					OutputStream os;
					response.setContentType("image/jpeg;charset=UTF-8");
					os = response.getOutputStream();
					FileUtil.writeStream(is, os);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new ErrorCodeException(ErrorCode.FILE_DOES_NOT_EXISTS);
			}
		} else {
			throw new ErrorCodeException(ErrorCode.RESOURCE_DOES_NOT_EXISTS,
					"activity cover");
		}
	}

	@RequestMapping(value = "/activity/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteActivity(@PathVariable Long id,
			@RequestParam String access_token) throws Exception {
		authValidater.validateSystemToken(access_token);// TODO:should admin
														// power
		Activity a = activityDAO.getActivity(id);
		if (a == null) {
			throw new ErrorCodeException(new Error(
					ErrorCode.RESOURCE_DOES_NOT_EXISTS, "activity"));
		} else {
			activityDAO.deleteActivity(id);
			if (a.getCover() != null) {
				imageDAO.deleteFile(new ObjectId(a.getCover()));
			}
			if(a.getDetails()!=null){
				imageDAO.deleteFile(new ObjectId(a.getDetails()));
			}
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/activity/{id}/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateActivity(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestParam(required = false) String describe,
			@RequestParam(required = false) String expire_time,
			@RequestBody(required = false) byte[] cover) throws Exception {
		authValidater.validateSystemToken(access_token);// TODO:should admin
														// power
		Activity activity = activityDAO.getActivity(id);
		if (activity != null) {
			activity.setDescribes(describe);
			activity.setExpireTime(DateUtils.formateDate(expire_time));
			activityDAO.updateActivity(activity, cover);
		} else {
			throw new ErrorCodeException(new Error(ErrorCode.FILE_FORMAT_ERROR));
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	public @ResponseBody
	Object saveActivity(@RequestParam String access_token,
			@RequestParam(required = false) String describe,
			@RequestParam String expire_time,
			@RequestBody(required = true) byte[] body) throws Exception {
		authValidater.validateSystemToken(access_token);// TODO:should admin
		// power
		if (body != null && body.length > 0) {
			activityDAO.saveActivity(DateUtils.formateDate(expire_time),
					describe, body);
		} else {
			throw new ErrorCodeException(new Error(ErrorCode.FILE_FORMAT_ERROR));
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/activity/{id}/detail", method = RequestMethod.GET)
	public void getActivityDetailFile(@PathVariable Long id,
			@RequestParam String access_token, HttpServletResponse response)
			throws Exception {
		authValidater.validateToken(access_token);
		Activity a = activityDAO.getActivity(id);
		if (a != null && a.getDetails() != null) {
			GridFSDBFile gridFSDBFile = imageDAO.getFile(new ObjectId(a
					.getDetails()));
			if (gridFSDBFile != null && gridFSDBFile.getInputStream() != null) {
				InputStream is = gridFSDBFile.getInputStream();
				String filename = gridFSDBFile.getFilename();
				try {
					OutputStream os;
					response.setHeader("filename", filename);
					response.setContentType("image/jpeg;charset=UTF-8");
					os = response.getOutputStream();
					FileUtil.writeStream(is, os);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new ErrorCodeException(ErrorCode.FILE_DOES_NOT_EXISTS);
			}
		} else {
			throw new ErrorCodeException(ErrorCode.RESOURCE_DOES_NOT_EXISTS,
					"activity detail file");
		}
	}

	@RequestMapping(value = "/activity/{id}/detail", method = RequestMethod.POST)
	public @ResponseBody
	Object saveActivityDetailFile(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestParam String fileName,
			@RequestBody(required = true) byte[] body) throws Exception {
		authValidater.validateSystemToken(access_token);
		if (body != null && body.length > 0) {
			Activity a = activityDAO.getActivity(id);
			boolean type=FileUtil.fileValidationType(body);
			if (type) {
				if (a != null) {
					activityDAO.addActivityDetail(fileName, a, body);
				} else {
					throw new ErrorCodeException(
							ErrorCode.RESOURCE_DOES_NOT_EXISTS, "activity");
				}
			} else {
				
				throw new ErrorCodeException(new Error(
						ErrorCode.FILE_FORMAT_ERROR));
			}

		} else {
			throw new ErrorCodeException(ErrorCode.FILE_DOES_NOT_EXISTS);
		}
		return new OnlyResultBean("ok");
	}
}
