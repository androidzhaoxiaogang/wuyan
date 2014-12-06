//
//  WCIOS.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <Foundation/Foundation.h>

#define IOS [WCIOS getIntance]



@interface WCIOS : NSObject

+(WCIOS*)getIntance;


@property(nonatomic)BOOL firstLogin;//判断是否第一次授权

@property(nonatomic)BOOL ISFirst;//判断是否登录登出

@property(nonatomic)float level;//资料完整度

@property(nonatomic)BOOL Auth;

//-----用户第三方登录传参变量
@property(nonatomic)int type ;//是哪个第三方类型

@property(nonatomic,strong)NSString *uid;//第三方平台用户UId

@property(nonatomic,strong)NSString *third_token;//第三方平台用户标识

@property(nonatomic,strong)NSDictionary *ThirdDic;//第三方登录返回的用户信息字典

//--------用户
@property(nonatomic,strong)NSString *userName;
@property(nonatomic,strong)NSString *userId;//平台返的userId;
@property(nonatomic,strong)NSString *access_token;//平台用户标识

@property(nonatomic)int site;//图片序号
@property(nonatomic,strong)NSString *userImageType;//上图片类别  是用户的头像icon 用户的简介图像image
@property(nonatomic,strong)NSString *imageID;//用户imageId;
@property(nonatomic,strong)NSString *imageInforId;//用户简介imageId
@property(nonatomic,strong)NSDictionary *ResultUserDictionary;//用户信息返回字典

@property(nonatomic,strong)NSString *tags;

@property(nonatomic,strong)NSDictionary *mathDictionary;//匹配用户的数据


@property(nonatomic,strong)NSString *activityStr_token;//活动图片封面标识
@property(nonatomic,strong)NSString *activityStr_id;//进入详细活动图片标识
@property(nonatomic,strong)NSString *activityStr_namePath;//进入详细活动图片本地保存路径标识

@property(nonatomic,strong)NSString *tempPath;//下载图片存在本地地址路径
@property(nonatomic,strong)NSString *autoLandFilePath;//自动登录文件保存路径

@property(nonatomic)BOOL ISLogin;//判断是否登陆

@property(nonatomic)int cursor;//匹配用户数据从第几个加载
@property(nonatomic)int limit;//加载几个


@property(nonatomic,strong)NSString *chatlocalPath;//聊天中点击看大图本地路径

@property(nonatomic,strong)NSArray *macthBigIamgeArray;//匹配人的简介图片数组

@property(nonatomic,strong)NSString *MacthUserId;//匹配的用户userID；
@property(nonatomic)BOOL ISY;//判断匹配数据是假数据还是加载的数据

@property(nonatomic)int ISp;//本地数据判断是匹配的第一个还是的二个


@property(nonatomic,strong)NSString *minPrice;//租金要求下限
@property(nonatomic,strong)NSString *maxPrice;//租金要求上限


@property(nonatomic,strong)NSMutableData *ReceiveData;

@property(nonatomic,strong)NSDictionary *dataDictionary;//异步请求数据返回字典

@property(nonatomic,strong)NSString *WuYouNameString;//呜友的名字

@end
