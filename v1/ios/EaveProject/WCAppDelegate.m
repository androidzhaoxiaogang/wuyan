
//
//  WCAppDelegate.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCAppDelegate.h"
#import "WCTabBarView.h"

#import "WCApplyViewController.h"
#import "MobClick.h"
#import "EMError.h"



#import <ShareSDK/ShareSDK.h>


static WCAppDelegate *appDelegate;

@implementation WCAppDelegate


+(WCAppDelegate*)delegate
{
    if (appDelegate == nil)
    {
        appDelegate = [[WCAppDelegate alloc]init];
    }
    
    return appDelegate;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.window.backgroundColor = [UIColor whiteColor];
    
    
    //====百度推送
    
    [BPush setupChannel:launchOptions];
    
    [BPush setDelegate:self];
    
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge|UIRemoteNotificationTypeSound|UIRemoteNotificationTypeAlert];
    
    //====新浪微博第三方登陆
    [WeiboSDK enableDebugMode:YES];
    [WeiboSDK registerApp:KSinaAppKey];
    
    
    
    
    [Model JudgeNetwork];
    
    
    NSString *tempPath = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"]stringByAppendingPathComponent:@"Temp"];
    
    NSFileManager *fm = [NSFileManager defaultManager];
    [fm createDirectoryAtPath:tempPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    
    IOS.tempPath = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"]stringByAppendingPathComponent:@"Temp"];
    
    IOS.autoLandFilePath = [[NSHomeDirectory()stringByAppendingPathComponent:@"/Documents"]stringByAppendingFormat:@"/landing.plist"];
    
    if ([fm fileExistsAtPath:IOS.autoLandFilePath] == NO)
    {
        NSDictionary *dictionary = [NSDictionary dictionary];
        [dictionary writeToFile:IOS.autoLandFilePath atomically:YES];
    }
    
    

    if(![[NSUserDefaults standardUserDefaults] boolForKey:@"firstLaunch"])
    {
    
        IOS.firstLogin = YES;
        IOS.ISFirst = YES;
        self.window.rootViewController = [[WCTabBarView alloc]init];
        [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"firstLaunch"];
    }
    else
    {
        
        
        if (Model.netWork > 0)
        {
            
            NSFileManager *flm = [NSFileManager defaultManager];
            
            if ([flm fileExistsAtPath:IOS.autoLandFilePath])
            {
                self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
            }
            
            if ( self.autoLandDictionary.allKeys.count != 0)
            {
                IOS.userId = [self.autoLandDictionary objectForKey:@"userid"];
                IOS.access_token = [self.autoLandDictionary objectForKey:@"accesstoken"];
                
                NSLog(@"userId === %@ \n access_token ===%@", IOS.userId,IOS.access_token);
                
                [self onlyLogin];
                
                
                self.window.rootViewController = [[WCTabBarView alloc]init];
                
                IOS.ISFirst = NO;
                
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"hiden" object:nil userInfo:nil];

            }
            else
            {
                IOS.ISFirst = YES;
                
                self.window.rootViewController = [[WCTabBarView alloc]init];
                
            }
            
            
        }
        else
        {
            
            [Model presentSheet:alertdisNet];
        }
        

    }
    
    
 

    
    
    
  
 //--------------------------------------------///
    
    
    [[NSNotificationCenter defaultCenter] addObserver:self
        selector:@selector(loginStateChange:)
        name:KNOTIFICATION_LOGINCHANGE
        object:nil];
    
    if ([UIDevice currentDevice].systemVersion.floatValue >= 7.0) {
        [[UINavigationBar appearance] setBarTintColor:RGBACOLOR(78, 188, 211, 1)];
        [[UINavigationBar appearance] setTitleTextAttributes:
         [NSDictionary dictionaryWithObjectsAndKeys:RGBACOLOR(245, 245, 245, 1), NSForegroundColorAttributeName, [UIFont fontWithName:@ "HelveticaNeue-CondensedBlack" size:21.0], NSFontAttributeName, nil]];
    }


    
#if !TARGET_IPHONE_SIMULATOR
    UIRemoteNotificationType notificationTypes = UIRemoteNotificationTypeBadge |
    UIRemoteNotificationTypeSound |
    UIRemoteNotificationTypeAlert;
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:notificationTypes];
#endif
 
#pragma SDK注册 APNS文件的名字, 需要与后台上传证书时的名字一一对应
    NSString *apnsCertName = nil;
#if DEBUG
    //apnsCertName = @"WuyanZhengshu";
#else
    apnsCertName = @"Eave_Release";
#endif
    [[EaseMob sharedInstance] registerSDKWithAppKey:@"1097562465#wuyan" apnsCertName:apnsCertName];
    [[EaseMob sharedInstance] enableBackgroundReceiveMessage];
    
#if DEBUG
        [[EaseMob sharedInstance] enableUncaughtExceptionHandler];
#endif
    //以下一行代码的方法里实现了自动登录，异步登录，需要监听[didLoginWithInfo: error:]
    //demo中此监听方法在MainViewController中
    [[EaseMob sharedInstance] application:application didFinishLaunchingWithOptions:launchOptions];
    
#pragma 注册为SDK的ChatManager的delegate (及时监听到申请和通知)
    [[EaseMob sharedInstance].chatManager removeDelegate:self];
    [[EaseMob sharedInstance].chatManager addDelegate:self delegateQueue:nil];
    
    //demo coredata, .pch中又相关头文件引用
    [MagicalRecord setupCoreDataStackWithStoreNamed:[NSString stringWithFormat:@"%@.sqlite", @"UIDemo"]];
    
    [self loginStateChange:nil];
    

    
    
//-------------------------------------------------/-/
    
    
    
    //======ShareSDK分享
    [ShareSDK registerApp:@"16d5d080492d"];
    
    //添加新浪微博应用
    
    [ShareSDK connectSinaWeiboWithAppKey:@"1865421835" appSecret:@"ee36da899e97f8e7ebb3e4f896c88407" redirectUri:@"https://api.weibo.com/oauth2/default.html"];
    
    //添加腾讯微博应用
    [ShareSDK connectTencentWeiboWithAppKey:@"801527713"
                                  appSecret:@"7045815e890f062e7caf3d00e5a8c268"
                                redirectUri:@"https://itunes.apple.com/cn/app/"];
    
    
    
    //添加微信应用
    [ShareSDK connectWeChatWithAppId:@"wxfc1f41c9ae31cafe"  //此参数为申请的微信AppID
                           wechatCls:[WXApi class]];
    
    //导入微信需要的外部库类型，如果不需要微信分享可以不调用此方法
    [ShareSDK importWeChatClass:[WXApi class]];

    
    [self.window makeKeyAndVisible];
    
    return YES;
}

-(void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    [BPush registerDeviceToken:deviceToken];
    
    NSLog(@"Register device token: %@\n", deviceToken);
    
    [BPush bindChannel];
    
    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
	[[EaseMob sharedInstance] application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
    
}

-(void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
#pragma SDK方法调用
    [[EaseMob sharedInstance] application:application didReceiveLocalNotification:notification];
}





-(void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
     NSLog(@"未能获得令牌，错误提示error:=====%@", error);
}

-(void)onMethod:(NSString *)method response:(NSDictionary *)data
{
    NSDictionary *res = [[NSDictionary alloc]initWithDictionary:data];
    
    if([BPushRequestMethod_Bind isEqualToString:method])
    {
        NSString *appid = [res valueForKey:BPushRequestRequestIdKey];
        
        NSString *userid = [res valueForKey:BPushRequestUserIdKey];
        
        NSString *channelid = [res valueForKey:BPushRequestChannelIdKey];
        
        self.PushappId = appid;
        self.PushchannelId = channelid;
        self.PushuserId = userid;
        
        NSLog(@"appid====%@ channelid ====%@ userid ==== %@",appid,channelid,userid);
        
    }
    
    
    NSLog(@"%@ return返回结果: \n%@", method, [data description]);
    
    
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
	[[EaseMob sharedInstance] applicationWillResignActive:application];
    
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"applicationDidEnterBackground" object:nil];

    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
    [[EaseMob sharedInstance] applicationDidEnterBackground:application];
    
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
    [[EaseMob sharedInstance] applicationWillEnterForeground:application];
    
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:0];
    
    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
    [[EaseMob sharedInstance] applicationDidBecomeActive:application];
    
    
}

- (void)applicationWillTerminate:(UIApplication *)application
{

    // 让SDK得到App目前的各种状态，以便让SDK做出对应当前场景的操作
    [[EaseMob sharedInstance] applicationWillTerminate:application];
}


#pragma mark - IChatManagerDelegate 好友变化

- (void)didReceiveBuddyRequest:(NSString *)username
                       message:(NSString *)message
{
    if (!username) {
        return;
    }
    if (!message)
    {
        message = [NSString stringWithFormat:@"%@ 添加你为好友", username];
    }
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:@{@"title":username, @"username":username, @"applyMessage":message, @"applyStyle":[NSNumber numberWithInteger:ApplyStyleFriend]}];
    [[WCApplyViewController  shareController] addNewApply:dic];
}




#pragma mark - IChatManagerDelegate 群组变化

- (void)didReceiveGroupInvitationFrom:(NSString *)groupId
                              inviter:(NSString *)username
                              message:(NSString *)message
{
    if (!groupId || !username) {
        return;
    }
    
    NSString *groupName = groupId;
    if (!message || message.length == 0) {
        message = [NSString stringWithFormat:@"%@ 邀请你加入群组\'%@\'", username, groupName];
    }
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:@{@"title":groupName, @"groupId":groupId, @"username":username, @"applyMessage":message, @"applyStyle":[NSNumber numberWithInteger:ApplyStyleGroupInvitation]}];
    [[WCApplyViewController shareController] addNewApply:dic];
}

//接收到入群申请
- (void)didReceiveApplyToJoinGroup:(NSString *)groupId
                         groupname:(NSString *)groupname
                     applyUsername:(NSString *)username
                            reason:(NSString *)reason
{
    if (!groupId || !username) {
        return;
    }
    
    if (!reason || reason.length == 0) {
        reason = [NSString stringWithFormat:@"%@ 申请加入群组\'%@\'", username, groupname];
    }
    else{
        reason = [NSString stringWithFormat:@"%@ 申请加入群组\'%@\'：%@", username, groupname, reason];
    }
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:@{@"title":groupname, @"groupId":groupId, @"username":username, @"groupname":groupname, @"applyMessage":reason, @"applyStyle":[NSNumber numberWithInteger:ApplyStyleJoinGroup]}];
    [[WCApplyViewController shareController] addNewApply:dic];
}

- (void)didReceiveRejectApplyToJoinGroupFrom:(NSString *)fromId
                                   groupname:(NSString *)groupname
                                      reason:(NSString *)reason
{
    if (!reason || reason.length == 0) {
        reason = [NSString stringWithFormat:@"被拒绝加入群组\'%@\'", groupname];
    }
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"申请提示" message:reason delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
    [alertView show];
}

- (void)group:(EMGroup *)group didLeave:(EMGroupLeaveReason)reason error:(EMError *)error
{
    NSString *tmpStr = group.groupSubject;
    NSString *str;
    if (!tmpStr || tmpStr.length == 0) {
        NSArray *groupArray = [[EaseMob sharedInstance].chatManager groupList];
        for (EMGroup *obj in groupArray) {
            if ([obj.groupId isEqualToString:group.groupId]) {
                tmpStr = obj.groupSubject;
                break;
            }
        }
    }
    
    if (reason == eGroupLeaveReason_BeRemoved) {
        str = [NSString stringWithFormat:@"你被从群组\'%@\'中踢出", tmpStr];
    }
    if (str.length > 0) {
        TTAlertNoTitle(str);
    }
}


#pragma mark - push

- (void)didBindDeviceWithError:(EMError *)error
{
    if (error) {
       // TTAlertNoTitle(@"消息推送与设备绑定失败");
    }
}
#pragma mark - private

-(void)loginStateChange:(NSNotification *)notification
{
}



//--------------------------------------//
-(void)didReceiveWeiboRequest:(WBBaseRequest *)request
{
    
}

- (void)didReceiveWeiboResponse:(WBBaseResponse *)response
{
    

    if ([response isKindOfClass:WBAuthorizeResponse.class])
    {
        token = [(WBAuthorizeResponse *)response accessToken];
        NSDictionary *Userdic = response.userInfo;
        openId = [Userdic objectForKey:@"uid"];
        
        if (openId !=nil)
        {
    
            IOS.uid = openId;
            IOS.third_token = token;
            IOS.type = 1;
            
            NSMutableDictionary *requestDic = [NSMutableDictionary dictionary];
            [requestDic setObject:IOS.uid forKey:@"uid"];
            [requestDic setObject:IOS.third_token forKey:@"third_token"];
            
            
            if (self.PushappId != nil && self.PushchannelId != nil)
            {
                [requestDic setObject:self.PushuserId forKey:@"user_push_id"];
                [requestDic setObject:self.PushchannelId forKey:@"channel_push_id"];
            }
            
            
            [requestDic setObject:@"1" forKey:@"type"];
            [requestDic setObject:@"0264a94959afb8d8" forKey:@"client_id"];
            [requestDic setObject:@"95fc6be00264a94959afb8d8ec6704fc" forKey:@"client_secret"];
            
            
            NSDictionary *disAuthDic = [Model Login:WC_AuthLogin andRequestMutableDic:requestDic andRequestMethod:@"POST"];
            
    
            NSLog(@"登陆返回数据====%@",disAuthDic);
            
            NSDictionary *membersDic = disAuthDic[@"result"];
            
            
            if (membersDic != nil)
            {
                IOS.userId = [NSString stringWithFormat:@"%@", membersDic[@"userId"]];
                IOS.access_token = membersDic[@"token"];
                
                NSLog(@"IOS.userId === %@ \n IOS.access_token ==== %@",IOS.userId,IOS.access_token);
                
                level = membersDic[@"infoIntegrityLevel"];
                
                NSString *ISd = membersDic[@"firstLogin"];
                
                
                if ([ISd boolValue])
                {
                    
                    [self Registration_and_login];
                    
                    IOS.firstLogin = YES;
                    
                }
                else
                {
                    [self onlyLogin];
                    
                }

                
                    [self authLogin];

                
                    [[NSNotificationCenter defaultCenter] postNotificationName:@"hiden" object:nil userInfo:nil];
            
            }
            else
            {
                [Model presentSheet:@"授权登录失败"];
            }
        
            
        }
        
        
    }
  
    
    
    
    
    
}



- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    
    return [WeiboSDK handleOpenURL:url delegate:self];
    

}



-(void)Registration_and_login
{
    [[EaseMob sharedInstance].chatManager asyncRegisterNewAccount:IOS.userId
        password:@"wuyan1097562465"
            withCompletion:
     ^(NSString *username, NSString *password, EMError *error)
     {
         if (!error)
         {
             
             // TTAlertNoTitle(@"注册成功,请登录");
             
             NSLog(@"注册成功,请登录");
             
             [self onlyLogin];
         }
         else
         {
             /*
             switch (error.errorCode)
             {
                 case EMErrorServerNotReachable:
                     TTAlertNoTitle(@"连接服务器失败!");
                     break;
                 case EMErrorServerDuplicatedAccount:
                     TTAlertNoTitle(@"您注册的用户已存在!");
                     break;
                 case EMErrorServerTimeout:
                     TTAlertNoTitle(@"连接服务器超时!");
                     break;
                 default:
                     TTAlertNoTitle(@"注册失败");
                     break;
             }*/
             
             
             NSLog(@"注册失败");
             
             
         }
     } onQueue:nil];
    
   
}


-(void)onlyLogin
{
    [[EaseMob sharedInstance].chatManager asyncLoginWithUsername:IOS.userId
            password:@"wuyan1097562465"
            completion:^(NSDictionary *loginInfo, EMError *error)
     {
         if (loginInfo && !error)
         {
             [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATION_LOGINCHANGE object:@YES];
             
             NSLog(@"登录成功");
             
         }else
         {
             /*
             switch (error.errorCode) {
                     
                     
                 case EMErrorServerNotReachable:
                     TTAlertNoTitle(@"连接服务器失败!");
                     break;
                 case EMErrorServerAuthenticationFailure:
                     TTAlertNoTitle(@"用户名或密码错误");
                     break;
                 case EMErrorServerTimeout:
                     TTAlertNoTitle(@"连接服务器超时!");
                     break;
                 default:
                     TTAlertNoTitle(@"登录失败");
                     break;
                     
                     
             }*/
             
             NSLog(@"登录失败");

             
         }
     } onQueue:nil];

}


-(void)authLogin
{
    self.autoLandDictionary = [NSMutableDictionary dictionary];
    
    NSFileManager *flm = [NSFileManager defaultManager];
    
    if ([flm fileExistsAtPath:IOS.autoLandFilePath])
    {
        self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
    }

    
    [self.autoLandDictionary setObject:IOS.userId forKey:@"userid"];
    [self.autoLandDictionary setObject:IOS.access_token forKey:@"accesstoken"];
    
    if ([level intValue]>0.6)
    {
        [self.autoLandDictionary setObject:@"P" forKey:@"P"];
    }
    else
    {
        
    [self.autoLandDictionary setObject:@"J" forKey:@"P"];
        
    }
    
    [self.autoLandDictionary setObject:@"0" forKey:@"shake"];

    [self.autoLandDictionary writeToFile:IOS.autoLandFilePath atomically:YES];

    self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
        
}




@end
