package net.wecash.server.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.common.util.FileUtil;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.facade.ImageFacade;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.user.dao.UserImageDAO;
import net.wecash.server.util.ImageUtils;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
public class ImageController extends HandleExceptionController {
	@Autowired
	FileDAO imageDAO;

	@Autowired
	UserImageDAO userImageDAO;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	ImageFacade imageFacade;

	@Autowired
	AuthValidater authValidater;
	
	@Value("${user.max.image}")
	private Integer maxImageNum;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@RequestMapping(value = "/user/{id}/image", method = RequestMethod.GET)
	public void getUserImage(
			HttpServletRequest request, 
			@PathVariable Long id,
			@RequestParam(required = false) String image,
			@RequestParam(required = false) Integer type,
			@RequestParam String access_token,
			HttpServletResponse response) throws Exception {
		
		authValidater.validateToken(access_token);
		UserImg ui = null;
		if(image != null && type == null){
			ui = imageFacade.getUserImgInfo(id, image);
		}else if(image == null && type != null){
			ui = imageFacade.getUserImgInfo(id, type);
		}else{
			throw new ErrorCodeException(new Error(ErrorCode.PARAMETER_VALUE_INVALID, "image or type"));
		}
		
		if (ui == null || ui.getImage() == null) {
			throw new ErrorCodeException(new Error(ErrorCode.IMAGE_DOSE_NOT_EXISTS));
		} else {
			String indexStr = ui.getImage();
			GridFSDBFile gridFSDBFile = imageDAO.getFile(new ObjectId(indexStr));
			if (gridFSDBFile != null && gridFSDBFile.getInputStream() != null) {
				InputStream is = gridFSDBFile.getInputStream();
				try {
					OutputStream os;
					response.setContentType("image/jpeg;charset=UTF-8");
					os = response.getOutputStream();
					FileUtil.writeStream(is, os);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				throw new ErrorCodeException(ErrorCode.IMAGE_DOSE_NOT_EXISTS);
			}
		}
	}

	@RequestMapping(value = "/user/{id}/image", method = RequestMethod.POST)
	public @ResponseBody
	Object addUserImage(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestParam Integer type,
			@RequestBody(required = true) byte[] body) throws Exception {
		
		authValidater.validateToken(id, access_token);
		List<String> images = userImageDAO.getAllUserImg(id);
		if(images == null || images.size() == 0){
			type = 1;
		}else if(images.size() <= 2){
			type = images.size() + 1;
		}else{
			if(type != 1 && type != 2 && type !=3){
				type = 1;
			}
		} 
		UserImg ui = imageFacade.getImgByType(id, type);
		if(body != null && body.length > 0){
			if(ui!=null){
				String oldIndex = ui.getImage();
				Object o = imageDAO.addFile(body);
				imageFacade.updateUserImageInfo(id, type, o.toString());
				ui.setImage(o.toString());
				try {
					imageDAO.deleteFile(new ObjectId(oldIndex));
				} catch (Exception e) {
				}
			}else{
				Object o = imageDAO.addFile(body);
				ui = new UserImg(id,o.toString(), type);
				imageFacade.saveUserImg(ui);
			}
		}else{
			throw new ErrorCodeException(new Error(ErrorCode.FILE_FORMAT_ERROR));
		}
		return new OnlyResultBean(ui);
	}

	@RequestMapping(value = "/user/{id}/image/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteUserImage(@PathVariable Long id,
			@RequestParam(required = true) String image,
			@RequestParam String access_token) throws Exception {
		
		authValidater.validateToken(id, access_token);
		UserImg ui = imageFacade.getUserImgInfo(id, image);
		if(ui != null && ui.getImage() != null && ui.getImage().equals(image)){
			imageFacade.deleteUserImg(id, image);
			imageDAO.deleteFile(new ObjectId(image));
		}else{
			throw new ErrorCodeException(ErrorCode.IMAGE_DOSE_NOT_EXISTS);
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/user/{id}/icon", method = RequestMethod.POST)
	public @ResponseBody
	Object addUserIcon(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestBody(required = true) byte[] icon) throws Exception {
		authValidater.validateToken(id, access_token);
		UserImg ui = null;
		if (icon != null && icon.length > 0) {
			ui = imageFacade.getUserIconInfo(id);
			Object o = imageDAO.addFile(icon);
			if (ui != null) {
				String oldIndex = ui.getImage();
				imageFacade.updateUserIconInfo(id, o.toString());
				ui.setImage(o.toString());
				try {
					imageDAO.deleteFile(new ObjectId(oldIndex));
				} catch (Exception e) {
				}
			} else {
				ui = new UserImg(id, o.toString(), 0);
				imageFacade.saveUserImg(ui);
			}
		} else {
			throw new ErrorCodeException(new Error(ErrorCode.FILE_FORMAT_ERROR));
		}

		return new OnlyResultBean(ui);
	}

	@RequestMapping(value = "/user/{id}/icon", method = RequestMethod.GET)
	public void getUserIcon(HttpServletRequest request, @PathVariable Long id,
			@RequestParam String access_token, HttpServletResponse response)
			throws Exception {

		authValidater.validateToken(access_token);

		UserImg ui = imageFacade.getUserIconInfo(id);

		if (ui == null || ui.getImage() == null) {
			throw new ErrorCodeException(new Error(ErrorCode.IMAGE_DOSE_NOT_EXISTS));
		} else {
			String indexStr = ui.getImage();
			GridFSDBFile gridFSDBFile = imageDAO.getFile(new ObjectId(indexStr));
			if (gridFSDBFile != null && gridFSDBFile.getInputStream() != null) {
				InputStream is = gridFSDBFile.getInputStream();
				try {
					OutputStream os;
					response.setContentType("image/jpeg;charset=UTF-8");
					os = response.getOutputStream();
					FileUtil.writeStream(is, os);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				throw new ErrorCodeException(ErrorCode.IMAGE_DOSE_NOT_EXISTS);
			}
		}
	}
	
	@RequestMapping(value = "/image/breviary", method = RequestMethod.GET)
	public void getReSizeImage(
			HttpServletRequest request, 
			@RequestParam String image,
			@RequestParam(required = false, defaultValue = "100") Integer height,
			@RequestParam(required = false, defaultValue = "100") Integer width,
			@RequestParam String access_token,
			HttpServletResponse response) throws Exception {
		GridFSDBFile gridFSDBFile = imageDAO.getFile(new ObjectId(image));
		if (gridFSDBFile != null && gridFSDBFile.getInputStream() != null) {
			InputStream is = gridFSDBFile.getInputStream();
			try {
				OutputStream os;
				response.setContentType("image/jpeg;charset=UTF-8");
				os = response.getOutputStream();
				ImageUtils.resize(is, os, height, width, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new ErrorCodeException(ErrorCode.IMAGE_DOSE_NOT_EXISTS);
		}
	}
}
