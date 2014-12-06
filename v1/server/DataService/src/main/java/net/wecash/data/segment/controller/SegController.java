package net.wecash.data.segment.controller;

import java.util.List;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.data.segment.SegmentAnalyzer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SegController extends HandleExceptionController{
	
	@RequestMapping(value = "/seg", method = RequestMethod.GET)
	public @ResponseBody
	Object getUserContact(
			@RequestParam String seg) {
		SegmentAnalyzer sa = new SegmentAnalyzer();
		List<String> l = sa.getSegedList(sa.segText(seg));
		return new OnlyResultBean(l);
	}
}
