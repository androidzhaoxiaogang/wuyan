package net.wecash.server.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.wecash.common.bean.ServerInfoBean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object home(Locale locale) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);
        return new ServerInfoBean("api", formattedDate);
    }
}