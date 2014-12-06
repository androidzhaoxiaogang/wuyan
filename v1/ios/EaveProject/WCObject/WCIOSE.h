//
//  WCIOSE.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <Foundation/Foundation.h>

#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)

#define  ISHeight [[UIScreen mainScreen] currentMode].size.height
#define  ISWidth [[UIScreen mainScreen] currentMode].size.width

#define  isiphone(X,Y) iPhone5 == NO ? (X) : (Y)



//-------------验证token是否过期--------------//

#define WC_Authentication_token  [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/oauth2/get_token_info?access_token=%@",IOS.access_token]]//fc356f38630079a19eb9cbeb495edd05

//****************3.用户接口**********************************//

//---------3.2. 授权登录验证---------//HTTP请求方式:POST

#define WC_AuthLogin [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/login"]]


//---------3.2.1 登出---------//HTTP请求方式:POST
#define WC_LogOut [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/logout?access_token=%@",IOS.userId,IOS.access_token]]


//---------3.3.	获取用户信息详情---------//HTTP请求方式:GET
#define WC_Get_the_user_information   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/detail?access_token=%@",IOS.userId,IOS.access_token]]

//---------3.4.	更新用户基本信息---------//HTTP请求方式:POST
#define WC_Update_user_basic_information   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/update?access_token=%@",IOS.userId,IOS.access_token]]

//---------3.4.1用户个人设置基本信息---------//HTTP请求方式:POST
#define WC_Update_user_set_information   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/update/all?access_token=%@",IOS.userId,IOS.access_token]]



//---------3.5.	上传用户简介图片---------//HTTP请求方式:POST
#define UPLOAD_SERVER_URL [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?type=%@&access_token=%@",IOS.userId,IOS.imageInforId,IOS.access_token]]


//---------3.5.	上传用户头像---------//HTTP请求方式:POST
#define UPLOAD_SERVER_USERURL [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/icon?access_token=%@",IOS.userId,IOS.access_token]]



//---------3.6.1 获取用户头像---------//HTTP请求方式:GET
#define WC_Get_the_user_HeadImage   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/icon?access_token=%@",IOS.userId,IOS.access_token]]

//---------3.6.	获取用户图片---------//HTTP请求方式:GET
#define WC_Get_the_user_image   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?access_token=%@&image=%@",IOS.userId,IOS.access_token,IOS.imageID]]

//---------3.7.	用户账单(未启用)---------//
#define WC_User_bills   @""

//****************4.标签管理接口*******************************//

//---------4.2.	添加标签---------//HTTP请求方式:POST
#define WC_Add_tags   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/tag?access_token=%@",IOS.access_token]]





//---------4.3.	获取标签---------//HTTP请求方式:GET
#define WC_Get_The_label   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/tag?access_token=%@",IOS.access_token]]//--****-



//---------4.4.	对用户添加标签---------//HTTP请求方式:POST
#define WC_Add_tags_to_the_user   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user-tags?access_token=%@&tags=%@",IOS.access_token,IOS.tags]]

//---------4.5.	取消用户标签---------//HTTP请求方式:DELETE
#define WC_Cancel_the_user_label   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user-tags?access_token=%@&tags=%@",IOS.access_token,IOS.tags]]

//---------4.6.	获取用户标签---------//HTTP请求方式:GET
#define WC_Get_the_user_label   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user-tags?access_token=%@&count=%d&page=%d",IOS.access_token]]

//****************5.房屋信息管理*******************************//

//---------5.2.	添加房屋信息---------//HTTP请求方式:POST
#define WC_Add_housing_information   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/room?access_token=%@",IOS.userId,IOS.access_token]]


//---------5.3.	获取房屋信息---------//HTTP请求方式:GET
#define WC_Get_housing_information   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/room?access_token=%@",IOS.userId,IOS.access_token]]







//****************6.系统公共图片接口****************************//

//---------6.2.---------//
#define WC_2   @""

//---------6.3.---------//
#define WC_3   @""

//---------6.4.---------//
#define WC_4   @""


//****************7.互动联系****8.	匹配接口************************//

//---------7.1.	申请互动---------//HTTP请求方式:POST
#define WC_Apply_for_interactive   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/tag?access_token=%@",IOS.access_token]]

//---------7.2.	接受互动---------//HTTP请求方式:POST
#define WC_Accept_the_interactive   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/tag?access_token=%@",IOS.access_token]]

//---------7.3.	留言---------//HTTP请求方式:POST
#define WC_Leave_a_message   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/tag?access_token=%@",IOS.access_token]]

//****************8.匹配接口****************************//

//有资料情况下
//-----------8.1.用户匹配列表------------//HTTP请求方式:GET
#define WC_The_user_matching_List   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/match?access_token=%@&cursor=%d&limit=%d",IOS.userId,IOS.access_token,IOS.cursor,IOS.limit]]

//-----------------8.1.1用户匹配详情------------//HTTP请求方式:GET
#define WC_The_user_matching_Detail   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/match/%@?access_token=%@",IOS.userId,IOS.MacthUserId,IOS.access_token]]

//---------------8.1.2获取匹配用户头像--------//
#define WC_The_user_matching_headImage   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/icon?access_token=%@",IOS.MacthUserId,IOS.access_token]]

//---------------8.1.2获取匹配用户简介图像--------//
#define WC_The_user_matching_InforImage   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?access_token=%@&image=%@",IOS.MacthUserId,IOS.access_token,IOS.imageInforId]]



//无资料情况下
//-----------8.2.精选用户列表------------//HTTP请求方式:GET
#define WC_The_user_matching_boutique   [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/boutique?access_token=%@",IOS.access_token]]

//-----------8.2.1精选用户的详情------------//HTTP请求方式:GET
#define WC_The_user_matching_boutique_Detail  [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/boutique/%@?access_token=%@",IOS.MacthUserId,IOS.access_token]]



//****************9.筛选接口****************************//



//----------获取活动列表--------//
#define WC_Activity_List [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/activity?access_token=%@&cursor=%d&limit=%d",IOS.access_token,IOS.cursor,IOS.limit]]

//----------获取活动封面图片--------//
#define WC_Activity_Image [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/activity/%@/cover?access_token=%@",IOS.activityStr_token,IOS.access_token]]


//----------获取活动图片详情--------//
#define WC_Activity_image_Detail [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/activity/%@/detail?access_token=%@",IOS.activityStr_id,IOS.access_token]]




//------------获取用户收藏列表---------------//
#define WC_collect_List [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/favorites/%@?access_token=%@",IOS.userId,IOS.access_token]]

//------------添加收藏用户---------------//
#define WC_collect_add [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/favorite/%@/add/%@?access_token=%@",IOS.userId,IOS.MacthUserId,IOS.access_token]]

//-------------删除收藏用户--------------//
#define WC_collect_delet [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/favorite/%@/cancel/%@?access_token=%@",IOS.userId,IOS.MacthUserId,IOS.access_token]]







@interface WCIOSE : NSObject

@end
