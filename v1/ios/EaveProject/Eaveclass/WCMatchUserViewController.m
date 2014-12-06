//
//  WCMatchUserViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCMatchUserViewController.h"
#import "WCMatchDetailViewController.h"
#import "WCPersonalViewController.h"
#import "WCBigImageShowViewController.h"
#import "WCAboutViewController.h"
#import "WCAddFriendViewController.h"
#import "WCMyCollectViewController.h"

#import "WCUserDataViewController.h"
#import "TGRImageZoomAnimationController.h"
#import "TGRImageViewController.h"


#define  HBCellIdentifier @"HBCellIdentifier"
static BOOL ShowMenu = YES;



@interface WCMatchUserViewController ()<UIAlertViewDelegate,UIViewControllerTransitioningDelegate,NSURLConnectionDataDelegate>

@end

@implementation WCMatchUserViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
     
        
    }
    return self;
}


-(void)viewWillAppear:(BOOL)animated
{
    
    self.navigationController.navigationBarHidden = YES;

    [[NSNotificationCenter defaultCenter] postNotificationName:@"showCustomTabBar" object:nil];

    
    
    self.autoLandDictionary = [NSMutableDictionary dictionary];
    
    NSFileManager *flm = [NSFileManager defaultManager];
    
    if ([flm fileExistsAtPath:IOS.autoLandFilePath])
    {
        self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
    }
    
    MM = self.autoLandDictionary[@"P"];
    SS = [self.autoLandDictionary[@"shake"] intValue];
    
    
}


-(void)disAgainView
{
    
    [self adddataforView];
    
    if (IOS.firstLogin)
    {
        firstLanchShowImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
        firstLanchShowImageView.image = [UIImage imageNamed:@"wc_show_1"];
        firstLanchShowImageView.backgroundColor = [UIColor clearColor];
        [self.view addSubview:firstLanchShowImageView];
        
        [self performSelector:@selector(hidenLanchShowImageView1) withObject:nil afterDelay:3.0];
        
        IOS.firstLogin = NO;
        
    }
    
    
}


-(void)hidenLanchShowImageView1
{
    firstLanchShowImageView.image = [UIImage imageNamed:@"wc_show_2"];
    
    [self performSelector:@selector(hidenLanchShowImageView2) withObject:nil afterDelay:3.0];
    
    
}
-(void)hidenLanchShowImageView2
{
    firstLanchShowImageView.image = [UIImage imageNamed:@"wc_show_3"];
    
    [self performSelector:@selector(hidenLanchShowImageView3) withObject:nil afterDelay:3.0];
}

-(void)hidenLanchShowImageView3
{
    firstLanchShowImageView.hidden = YES;
}



-(void)viewDidDisappear:(BOOL)animated
{
    userView.frame = CGRectMake(320, 20, 320,isiphone(480-64, 568-64));
    userView.hidden = YES;

}


- (void)viewDidLoad
{
    [super viewDidLoad];
    
 
    //------------------------------------------//
    
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_nv_1"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(105, 20, 110, 44)];
    disLabel.text = @"匹配用户推荐";
    disLabel.textAlignment = NSTextAlignmentCenter;
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
    UIButton *backFButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backFButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backFButton];
    [backFButton addTarget:self action:@selector(backFbuttonClick:) forControlEvents:UIControlEventTouchUpInside];
    

    
    UIButton *userClcikMenuButton = [UIButton buttonWithType:UIButtonTypeCustom];
    userClcikMenuButton.frame = CGRectMake(270, 20, 50, 44);
    
    [self.view addSubview:userClcikMenuButton];
    
    [userClcikMenuButton addTarget:self action:@selector(showMenuView:) forControlEvents:UIControlEventTouchUpInside];
    

    
    
    [[UIApplication sharedApplication] setApplicationSupportsShakeToEdit:YES];
    [self becomeFirstResponder];
    
    
    
    DisTagArray = [NSArray arrayWithObjects:@"同是地球人...",@"都不拘小节",@"都爱干净",@"都轻度洁癖",@"都重度洁癖",@"相同地理位置",@"都不想被打扰",@"处于动静之间",@"都想要更多互动",@"相同行业",@"喜欢音乐",@"喜欢运动",@"喜欢摄影/绘画/艺术",@"喜欢动漫或游戏",@"喜欢美食",@"喜欢旅游与生活",@"爱阅读",@"喜欢影视",@"热心公益",@"关注IT与金融",@"喜欢宠物", nil];
    
    //-------------
  
    
    [self disSubView];
    [self adddataforView];

    
    act = [[wscustomActivity alloc]init];
    act.center = self.view.center;
    [self.view addSubview:act];
    

     [Model JudgeNetwork];
     
     if (Model.netWork != 0)
     {
         
     NSData *data = [NSData dataWithContentsOfURL:WC_Get_the_user_information];
     
     
     NSDictionary *personDic = [data JSONValue];
     
     NSDictionary *userDic = personDic[@"result"];
     
     IOS.ResultUserDictionary = userDic;
         
     }
     else
     {
         
     [Model presentSheet:alertdisNet];
         
     }

    
    
    
}




-(void)viewDidAppear:(BOOL)animated
{

    
    act.hidden = YES;
    

     [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(disAgainView) name:@"hiden" object:nil];
    
     [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(disAgainView) name:@"Please_Complete" object:nil];

   
    
}







-(void)disSubView
 {
     
     
 matchUserScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-114, 568-114))];
 matchUserScrollView.contentSize = CGSizeMake(320, isiphone(435, 455));
 matchUserScrollView.showsVerticalScrollIndicator = NO;
 matchUserScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
 [self.view addSubview:matchUserScrollView];
 
     
     UIImageView *bguserImage = [[UIImageView alloc]initWithFrame:CGRectMake(23, 11, 63, 63)];
     bguserImage.image = [UIImage imageNamed:@"wc_user_bg"];
    [matchUserScrollView addSubview:bguserImage];
     
     
     
     
  self.userBgImageView = [[UIImageView alloc]initWithFrame:CGRectMake(24, 12, 60, 60)];
     
    [Model setImageView:self.userBgImageView addRoundRectWidth:1.f color:[UIColor blackColor]];
  [matchUserScrollView addSubview:self.userBgImageView];
 
     
 
     userHomeStateImageView = [[UIImageView alloc]initWithFrame:CGRectMake(260,1,60,21)];
     [matchUserScrollView addSubview:userHomeStateImageView];

     
 
 
    self.sexImageView = [[UIImageView alloc]initWithFrame:CGRectMake(98, 24, 15, 15)];
    [matchUserScrollView addSubview:self.sexImageView];
     

 
 
 
 NameLabel = [[UILabel alloc]initWithFrame:CGRectMake(118, 20, 150, 24)];
 NameLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
 NameLabel.font = [UIFont systemFontOfSize:17];
 
 [matchUserScrollView addSubview: NameLabel];
 
 
 introduceLabel = [[UILabel alloc]initWithFrame:CGRectMake(98, 48, 180, 21)];
 introduceLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
 introduceLabel.font = [UIFont systemFontOfSize:12];
 [matchUserScrollView addSubview: introduceLabel];
 
     
     
 
     self.image1 = [[UIImageView alloc]initWithFrame:CGRectMake(22, 81, 134, 175)];
     self.image1.image = [UIImage imageNamed:@"wc_macth_p1"];
     
     self.image1.layer.cornerRadius = 8;
     self.image1.layer.masksToBounds = YES;
     [matchUserScrollView addSubview:self.image1];
     self.image2 = [[UIImageView alloc]initWithFrame:CGRectMake(165, 81, 134, 83)];
     self.image2.image = [UIImage imageNamed:@"wc_macth_p2"];
     self.image2.layer.cornerRadius = 8;
     self.image2.layer.masksToBounds = YES;
     [matchUserScrollView addSubview:self.image2];
     self.image3 = [[UIImageView alloc]initWithFrame:CGRectMake(165, 173, 134, 83)];
     self.image3.image = [UIImage imageNamed:@"wc_macth_p2"];
     self.image3.layer.cornerRadius = 8;
     self.image3.layer.masksToBounds = YES;

     [matchUserScrollView addSubview:self.image3];
     
     
 ImageButton1 =[UIButton buttonWithType:UIButtonTypeCustom];
 ImageButton1.frame = CGRectMake(22, 81, 134, 175);
 ImageButton1.tag = 1;
 [matchUserScrollView addSubview:ImageButton1];
 [ImageButton1 addTarget:self action:@selector(disBigImageShow:) forControlEvents:UIControlEventTouchUpInside];
     
 
     
 ImageButton2 =[UIButton buttonWithType:UIButtonTypeCustom];
 ImageButton2.frame = CGRectMake(165, 81, 134, 83);
 ImageButton2.tag = 2;
 [matchUserScrollView addSubview:ImageButton2];
 [ImageButton2 addTarget:self action:@selector(disBigImageShow:) forControlEvents:UIControlEventTouchUpInside];
 
 ImageButton3 =[UIButton buttonWithType:UIButtonTypeCustom];
 ImageButton3.frame = CGRectMake(165, 173, 134, 83);
 ImageButton3.backgroundColor = [UIColor clearColor];
 ImageButton3.tag = 3;
 [matchUserScrollView addSubview:ImageButton3];
 [ImageButton3 addTarget:self action:@selector(disBigImageShow:) forControlEvents:UIControlEventTouchUpInside];
 
 
//----------------------------------------------//
     
     
 
 UIImageView *lineImageView =[[UIImageView alloc]initWithFrame:CGRectMake(0, 269, 320, 1)];
 lineImageView.image = [UIImage imageNamed:@"wc_matchuser_line"];
 [matchUserScrollView addSubview:lineImageView];
 

 
 UIImageView *tagImageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(38, 283, 243, 25)];
 tagImageView1.image = [UIImage imageNamed:@"wc_matchuser_bgBule"];
 [matchUserScrollView addSubview:tagImageView1];
 
 tagLabel1 = [[UILabel alloc]initWithFrame:CGRectMake(38, 283, 243, 25)];
 tagLabel1.font = [UIFont systemFontOfSize:13];
 tagLabel1.textAlignment = NSTextAlignmentCenter;
 tagLabel1.textColor = [UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
 [matchUserScrollView addSubview:tagLabel1];
 
     UIButton *tagButton1 = [UIButton buttonWithType:UIButtonTypeCustom];
     tagButton1.tag = 1;
     tagButton1.frame = CGRectMake(38, 283, 243, 25);
     [matchUserScrollView addSubview:tagButton1];
     [tagButton1 addTarget:self action:@selector(tagButtonClcik:) forControlEvents:UIControlEventTouchUpInside];
     
     
 
 UIImageView *tagImageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(38, 318, 243, 25)];
 tagImageView2.image = [UIImage imageNamed:@"wc_matchuser_bgRed"];
 [matchUserScrollView addSubview:tagImageView2];
 
 tagLabel2 = [[UILabel alloc]initWithFrame:CGRectMake(38, 318, 243, 25)];
 tagLabel2.font = [UIFont systemFontOfSize:13];
 tagLabel2.textAlignment = NSTextAlignmentCenter;
 tagLabel2.textColor = [UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
 [matchUserScrollView addSubview:tagLabel2];
 
 
     UIButton *tagButton2 = [UIButton buttonWithType:UIButtonTypeCustom];
     tagButton2.tag = 2;
     tagButton2.frame = CGRectMake(38, 318, 243, 25);
     [matchUserScrollView addSubview:tagButton2];
     [tagButton2 addTarget:self action:@selector(tagButtonClcik:) forControlEvents:UIControlEventTouchUpInside];
     
 
 UIImageView *tagImageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(38, 353, 243, 25)];
 tagImageView3.image = [UIImage imageNamed:@"wc_matchuser_bgBule"];
 [matchUserScrollView addSubview:tagImageView3];
 
 tagLabel3 = [[UILabel alloc]initWithFrame:CGRectMake(38, 353, 243, 25)];
 tagLabel3.font = [UIFont systemFontOfSize:13];
 tagLabel3.textAlignment = NSTextAlignmentCenter;
 
 tagLabel3.textColor = [UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
 [matchUserScrollView addSubview:tagLabel3];
 
 
     UIButton *tagButton3 = [UIButton buttonWithType:UIButtonTypeCustom];
     tagButton3.tag = 3;
     tagButton3.frame = CGRectMake(38, 353, 243, 25);
     [matchUserScrollView addSubview:tagButton3];
     [tagButton3 addTarget:self action:@selector(tagButtonClcik:) forControlEvents:UIControlEventTouchUpInside];
     
     
     
     
     
     
 
 Wubutton = [UIButton buttonWithType:UIButtonTypeCustom];
 Wubutton.frame = CGRectMake(85, 399, 150, 35);
 
 [Wubutton setImage:[UIImage imageNamed:@"zw_matchuser_wu"] forState:UIControlStateNormal];
 [matchUserScrollView addSubview:Wubutton];
 
     [Wubutton addTarget:self action:@selector(chatViewButtonClick:) forControlEvents:UIControlEventTouchUpInside];
 
 
 
 UITapGestureRecognizer *Tap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(TapGestureC:)];
 Tap.numberOfTapsRequired = 1;
 
 [matchUserScrollView addGestureRecognizer:Tap];
 

     
     
//--------------------------------//
     
     userView = [[UIView alloc]initWithFrame:CGRectMake(320, 64, 320, isiphone(480-64, 568-64))];
     userView.backgroundColor = [UIColor clearColor];
     [self.view addSubview:userView];
     
     userClickImageView = [[UIImageView alloc]initWithFrame:CGRectMake(320-140, -1, 140, 174)];
     userClickImageView.image = [UIImage imageNamed:@"wc_person_menu"];
     [userView addSubview:userClickImageView];
     
     
     
     UITapGestureRecognizer *menuTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(menuClickTap:)];
     menuTap.numberOfTouchesRequired = 1;
     [userView addGestureRecognizer:menuTap];
     
     
     UIButton *setButton =[UIButton buttonWithType:UIButtonTypeCustom];
     setButton.frame = CGRectMake(320-140, 0, 140, 43);
     setButton.backgroundColor =[UIColor clearColor];
     
     [userView addSubview:setButton];
     
     [setButton addTarget:self action:@selector(setButtonClick:) forControlEvents:UIControlEventTouchUpInside];
     
     
     UIButton *collectionButton = [UIButton buttonWithType:UIButtonTypeCustom];
     collectionButton.frame = CGRectMake(320-140, 44, 140, 43);
     [userView addSubview:collectionButton];
     [collectionButton addTarget:self action:@selector(collectionButtonClick:) forControlEvents:UIControlEventTouchUpInside];
     
     
     UIButton *logOutButton = [UIButton buttonWithType:UIButtonTypeCustom];
     logOutButton.frame = CGRectMake(320-140, 88, 140, 43);
     [userView addSubview:logOutButton];
     [logOutButton addTarget:self action:@selector(logOutButtonClick:) forControlEvents:UIControlEventTouchUpInside];
     
     
     UIButton *aboutButton =[UIButton buttonWithType:UIButtonTypeCustom];
     aboutButton.frame = CGRectMake(320-140, 131, 163, 43);
     [userView addSubview:aboutButton];
     [aboutButton addTarget:self action:@selector(aboutButtonClick:) forControlEvents:UIControlEventTouchUpInside];
 
     userView.hidden = YES;
 
 }

-(void)adddataforView
{
    
    self.autoLandDictionary = [NSMutableDictionary dictionary];
    
    NSFileManager *flm = [NSFileManager defaultManager];
    
    if ([flm fileExistsAtPath:IOS.autoLandFilePath])
    {
        self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
    }
    
    MM = self.autoLandDictionary[@"P"];
    SS = [self.autoLandDictionary[@"shake"] intValue];
    
    
    if ([MM isEqualToString:@"J"])
    {
        IOS.cursor = 0; IOS.limit = 2;
        
        NSData *JXdata = [NSData dataWithContentsOfURL:WC_The_user_matching_boutique];
        
        NSLog(@"WC_The_user_matching_boutique === %@",WC_The_user_matching_boutique);
        
        NSDictionary *sultDic = [JXdata JSONValue];
        
        NSArray *jxArray = sultDic[@"result"];
        
        if (jxArray.count > 0 )
        {
            IOS.mathDictionary = [jxArray objectAtIndex:SS];
        }
        
    }
    else
    {
        
        
        NSData *PPdata = [NSData dataWithContentsOfURL:WC_The_user_matching_List];
        NSDictionary *ppDic = [PPdata JSONValue];
        NSArray *ppArray  = ppDic[@"result"];
        
        NSLog(@"ppArray === %@",ppArray);
        
        if (ppArray.count > 0 )
        {
            IOS.mathDictionary = [ppArray objectAtIndex:0];
            
        }
        
        IOS.ISY = YES;
        
    }
    
    

    
    
    //--------加载数据--------------------------
    
    NSArray *imageArray = IOS.mathDictionary[@"image"];
    
    //显示头像
    
    NSString *activityStr = IOS.mathDictionary[@"icon"];
    
    if (activityStr  != nil)
    {
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",activityStr];
        
        NSFileManager *fm=[NSFileManager defaultManager];
        
        if([fm fileExistsAtPath:imagefilepath])
        {
            
            [self.userBgImageView  setImage:[UIImage imageWithContentsOfFile:imagefilepath]];
            
        }
        else
        {
            [self performSelectorInBackground:@selector(downloadMatcUserImage) withObject:nil];
        }
        
    }
    //显示介绍图片1
    if (imageArray.count > 0)
    {
        NSString *introduceImagefilepath1=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];
        
        NSFileManager *fm1=[NSFileManager defaultManager];
        
        if([fm1 fileExistsAtPath:introduceImagefilepath1])
        {
            
            [self.image1 setImage:[UIImage imageWithContentsOfFile:  introduceImagefilepath1]];
        }
        else
        {
            [self performSelectorInBackground:@selector(downloadMatcUserDisimage1) withObject:nil];
        }
        
        
    }
    //显示介绍图片2
    if (imageArray.count > 1)
    {
        NSString *introduceImagefilepath2=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
        
        NSFileManager *fm2=[NSFileManager defaultManager];
        
        if([fm2 fileExistsAtPath:introduceImagefilepath2])
        {
            
            [self.image2 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath2]];
            
        }
        else
        {
            [self performSelectorInBackground:@selector(downloadMatcUserDisimage2) withObject:nil];
        }
        
        
    }
    //显示介绍图片3
    
    if (imageArray.count > 2)
    {
        NSString *introduceImagefilepath3=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
        
        NSFileManager *fm3=[NSFileManager defaultManager];
        
        if([fm3 fileExistsAtPath:introduceImagefilepath3])
        {
            
            
            [self.image3 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath3]];
        }
        else
        {
            [self performSelectorInBackground:@selector(downloadMatcUserDisimage3) withObject:nil];
        }
        
        
    }
    
    if ([IOS.mathDictionary[@"gender"]isEqualToString:@"m"])
    {
         self.sexImageView.image = [UIImage imageNamed:@"wc_person_boy"];
    }
    else
    {
        self.sexImageView.image = [UIImage imageNamed:@"wc_person_girls"];
    }
    
    
    if ([IOS.mathDictionary[@"state"]intValue] == 1)
    {
        userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag1"];
    }
    else if ([IOS.mathDictionary[@"state"]intValue] == 2)
    {
        userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag2"];
    }
    
    
    NameLabel.text = IOS.mathDictionary[@"name"];
    introduceLabel.text = IOS.mathDictionary[@"description"];
    NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
    tagLabel1.text = funnydic.allKeys[0];
    tagLabel2.text = funnydic.allKeys[1];
    tagLabel3.text = funnydic.allKeys[2];
    //-------------------------------------------
    

}



//-----实现摇动方法

- (void)motionBegan:(UIEventSubtype)motion withEvent:(UIEvent *)event

{
    //检测到摇动
    
    SS = [[self.autoLandDictionary objectForKey:@"shake"] intValue];
    
    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
    
    
    NSLog(@"MM ==== %@",MM);
    
    
    [Model JudgeNetwork];
    
    if (Model.netWork > 0)
    {
        if ([MM isEqualToString:@"J"])
        {

            IOS.cursor = 1;
            IOS.limit = 2;
            
            NSData *data = [NSData dataWithContentsOfURL:WC_The_user_matching_boutique];
            
            NSDictionary *JXdic =[data JSONValue];
            
            NSArray *jxArray = JXdic[@"result"];
            
            NSLog(@"jxarray ==== %@",jxArray);
            
            IOS.mathDictionary = jxArray[0];
            
            
            [self.autoLandDictionary setObject:@"1" forKey:@"shake"];
            [self.autoLandDictionary writeToFile:IOS.autoLandFilePath atomically:YES];
            
            self.autoLandDictionary = [NSDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];

        }
        else
        {
            
            
            NSData *data = [NSData dataWithContentsOfURL:WC_The_user_matching_List];
            
            NSLog(@"WC_The_user_matching_List ===== %@",WC_The_user_matching_List);
            
            NSDictionary *PPdic =[data JSONValue];
            
            NSArray *PPArray = PPdic[@"result"];
        
            
            if (PPArray.count > 0)
            {
                IOS.mathDictionary = PPArray[0];
                
                NSLog(@"matdictionary ==== %@",IOS.mathDictionary);
                
                [self.autoLandDictionary setObject:[NSString stringWithFormat:@"%d",IOS.cursor] forKey:@"shake"];
                [self.autoLandDictionary writeToFile:IOS.autoLandFilePath atomically:YES];
                
                self.autoLandDictionary = [NSDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
                
                
                IOS.ISY = YES;
            }
            else
            {
                [Model presentSheet:@"暂无更多匹配用户"];
                
            }
            
            IOS.cursor ++;
            IOS.limit = 1;
            
            
        }

    }
    else
    {
        [Model presentSheet:alertdisNet];
    }
    
    

    
}


- (void) motionCancelled:(UIEventSubtype)motion withEvent:(UIEvent *)event

{
    //摇动取消
    
    
    if ([MM isEqualToString:@"J"] && SS == 1)
    {
        [self presentViewController:[[WCUserDataViewController alloc]init] animated:YES completion:^{
            [Model presentSheet:@"请填写资料,进行深度匹配！"];
        }];
        
    }
    else
    {
        
        if (event.subtype == UIEventSubtypeMotionShake)
        {
            
            CATransition *transition=[CATransition animation];
            transition.duration=2.0;
            transition.type=kCATransitionFade;
            transition.subtype=kCATransitionFromLeft;
            
            
            NSArray *imageArray = IOS.mathDictionary[@"image"];
            
            //显示头像
            
            NSString *activityStr = IOS.mathDictionary[@"icon"];
            
            if (activityStr  != nil)
            {
                NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",activityStr];
                
                NSFileManager *fm=[NSFileManager defaultManager];
                
                if([fm fileExistsAtPath:imagefilepath])
                {
                    
                    [self.userBgImageView  setImage:[Model circleImage:[UIImage imageWithContentsOfFile:imagefilepath] withParam:1]];
                    
                }
                else
                {
                    [self performSelectorInBackground:@selector(downloadMatcUserImage) withObject:nil];
                }
                
            }
            
            //显示介绍图片1
            if (imageArray.count > 0)
            {
                NSString *introduceImagefilepath1=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];
                
                NSFileManager *fm1=[NSFileManager defaultManager];
                
                if([fm1 fileExistsAtPath:introduceImagefilepath1])
                {
                    
                    
                    [self.image1 setImage:[UIImage imageWithContentsOfFile:  introduceImagefilepath1]];
                    
                }
                else
                {
                    [self performSelectorInBackground:@selector(downloadMatcUserDisimage1) withObject:nil];
                }
                
                
            }
            //显示介绍图片2
            if (imageArray.count > 1 )
            {
                NSString *introduceImagefilepath2=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
                
                NSFileManager *fm2=[NSFileManager defaultManager];
                
                if([fm2 fileExistsAtPath:introduceImagefilepath2])
                {
                    [self.image2 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath2]];
                    
                    
                }
                else
                {
                    [self performSelectorInBackground:@selector(downloadMatcUserDisimage2) withObject:nil];
                }
                
                
            }
            //显示介绍图片3
            if (imageArray.count > 2)
            {
                NSString *introduceImagefilepath3=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
                
                NSFileManager *fm3=[NSFileManager defaultManager];
                
                if([fm3 fileExistsAtPath:introduceImagefilepath3])
                {
                    [self.image3 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath3]];
                    
                    
                    
                }
                else
                {
                    [self performSelectorInBackground:@selector(downloadMatcUserDisimage3) withObject:nil];
                }
                
                
            }
            
            
            
            
            if ([IOS.mathDictionary[@"gender"]isEqualToString:@"m"])
            {
                self.sexImageView.image = [UIImage imageNamed:@"wc_person_boy"];
            }
            else
            {
                self.sexImageView.image = [UIImage imageNamed:@"wc_person_girls"];
            }
            
            
            if ([IOS.mathDictionary[@"state"]intValue] == 1)
            {
                userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag1"];
            }
            else if ([IOS.mathDictionary[@"state"]intValue] == 2)
            {
                userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag2"];
            }
            
            
            
            
            
            NameLabel.text = IOS.mathDictionary[@"name"];
            introduceLabel.text = IOS.mathDictionary[@"description"];
            NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
            tagLabel1.text = funnydic.allKeys[0];
            tagLabel2.text = funnydic.allKeys[1];
            tagLabel3.text = funnydic.allKeys[2];
            
            
            [self.userBgImageView.layer addAnimation:transition forKey:nil];
            [NameLabel.layer addAnimation:transition forKey:nil];
            [introduceLabel.layer addAnimation:transition forKey:nil];
            [ImageButton1.layer addAnimation:transition forKey:nil];
            [ImageButton2.layer addAnimation:transition forKey:nil];
            [ImageButton3.layer addAnimation:transition forKey:nil];
            [tagLabel1.layer addAnimation:transition forKey:nil];
            [tagLabel2.layer addAnimation:transition forKey:nil];
            [tagLabel3.layer addAnimation:transition forKey:nil];
            
            
            
        }
        
        
        
        
        matchUserScrollView.frame = CGRectMake(0, isiphone(480, 568), 320, isiphone(480-114, 568-114));
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.3];
        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
        matchUserScrollView.frame = CGRectMake(0, 64, 320, isiphone(480-114, 568-114));
        [UIView commitAnimations];
        
        
        
    }
    
    

   
}


- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event

{

    
    //摇动结束
    
    
    
    if ([MM isEqualToString:@"J"]&&SS == 1)
    {
        [self presentViewController:[[WCUserDataViewController alloc]init] animated:YES completion:^{
        [Model presentSheet:@"请填写资料,进行深度匹配！"];
        }];
        
    }
    else
    {
    
    if (event.subtype == UIEventSubtypeMotionShake)
    {

        CATransition *transition=[CATransition animation];
        transition.duration=2.0;
        transition.type=kCATransitionFade;
        transition.subtype=kCATransitionFromLeft;
        
                       
        NSArray *imageArray = IOS.mathDictionary[@"image"];
                        
        //显示头像
                        
        NSString *activityStr = IOS.mathDictionary[@"icon"];
                        
        if (activityStr  != nil)
        {
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",activityStr];
                            
        NSFileManager *fm=[NSFileManager defaultManager];
                            
        if([fm fileExistsAtPath:imagefilepath])
          {
                                
            [self.userBgImageView  setImage:[Model circleImage:[UIImage imageWithContentsOfFile:imagefilepath] withParam:1]];
                                
          }
        else
         {
         [self performSelectorInBackground:@selector(downloadMatcUserImage) withObject:nil];
         }

     }
        
        //显示介绍图片1
        if (imageArray.count > 0)
        {
            NSString *introduceImagefilepath1=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];
            
            NSFileManager *fm1=[NSFileManager defaultManager];
            
            if([fm1 fileExistsAtPath:introduceImagefilepath1])
            {
                

                [self.image1 setImage:[UIImage imageWithContentsOfFile:  introduceImagefilepath1]];
                
            }
            else
            {
                [self performSelectorInBackground:@selector(downloadMatcUserDisimage1) withObject:nil];
            }
            
            
        }
        //显示介绍图片2
        if (imageArray.count > 1 )
        {
            NSString *introduceImagefilepath2=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
            
            NSFileManager *fm2=[NSFileManager defaultManager];
            
            if([fm2 fileExistsAtPath:introduceImagefilepath2])
            {
                [self.image2 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath2]];
                
                
            }
            else
            {
                [self performSelectorInBackground:@selector(downloadMatcUserDisimage2) withObject:nil];
            }
            
            
        }
        //显示介绍图片3
        if (imageArray.count > 2)
        {
            NSString *introduceImagefilepath3=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
            
            NSFileManager *fm3=[NSFileManager defaultManager];
            
            if([fm3 fileExistsAtPath:introduceImagefilepath3])
            {
                [self.image3 setImage:[UIImage imageWithContentsOfFile: introduceImagefilepath3]];
                
            }
            else
            {
                [self performSelectorInBackground:@selector(downloadMatcUserDisimage3) withObject:nil];
            }
            
            
        }
        

        
        if ([IOS.mathDictionary[@"gender"]isEqualToString:@"m"])
        {
            self.sexImageView.image = [UIImage imageNamed:@"wc_person_boy"];
        }
        else
        {
            self.sexImageView.image = [UIImage imageNamed:@"wc_person_girls"];
        }
        
        
        if ([IOS.mathDictionary[@"state"]intValue] == 1)
        {
            userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag1"];
        }
        else if ([IOS.mathDictionary[@"state"]intValue] == 2)
        {
            userHomeStateImageView.image = [UIImage imageNamed:@"wc_macthUser_tag2"];
        }

        
    NameLabel.text = IOS.mathDictionary[@"name"];
    introduceLabel.text = IOS.mathDictionary[@"description"];
    NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
    tagLabel1.text = funnydic.allKeys[0];
    tagLabel2.text = funnydic.allKeys[1];
    tagLabel3.text = funnydic.allKeys[2];
        

        
    [self.userBgImageView.layer addAnimation:transition forKey:nil];
    [NameLabel.layer addAnimation:transition forKey:nil];
    [introduceLabel.layer addAnimation:transition forKey:nil];
    [ImageButton1.layer addAnimation:transition forKey:nil];
    [ImageButton2.layer addAnimation:transition forKey:nil];
    [ImageButton3.layer addAnimation:transition forKey:nil];
    [tagLabel1.layer addAnimation:transition forKey:nil];
    [tagLabel2.layer addAnimation:transition forKey:nil];
    [tagLabel3.layer addAnimation:transition forKey:nil];

    
    
    }
        
        
     matchUserScrollView.frame = CGRectMake(0, isiphone(480, 568), 320, isiphone(480-114, 568-114));
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.3];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    matchUserScrollView.frame = CGRectMake(0, 64, 320, isiphone(480-114, 568-114));
    [UIView commitAnimations];
        

        
    }
    

    
    
}




//点击呜一下
-(void)chatViewButtonClick:(UIButton*)sender
{

    
    
    if ([MM isEqualToString:@"P"])
    {
        act = [[wscustomActivity alloc]init];
        act.center = self.view.center;
        [self.view addSubview:act];
        
         self.disbuddyName = [NSString stringWithFormat:@"%@", IOS.mathDictionary[@"userId"]];
        
         self.disChatName = IOS.mathDictionary[@"name"];
        
        [self clickWuAddFriend:self.disbuddyName];
        
        [act removeFromSuperview];
        
    }
    else
    {
       
        UIAlertView *alert  = [[UIAlertView alloc]initWithTitle:@"温馨提示" message:@"请完善资料,进行深度匹配！" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"好的", nil];
        alert.tag = 100;
        [alert show];
    }
    
    
    
    
}




//判断加没加过好友
-(void)clickWuAddFriend:(NSString*)buddyName;
{
    if ([AddFriend didBuddyExist:buddyName])
    {
        NSString *message = [NSString stringWithFormat:@"'%@'已经是你的好友了!", buddyName];
        [WCAlertView showAlertWithTitle:message
                                message:nil
                     customizationBlock:nil
                        completionBlock:nil
                      cancelButtonTitle:@"确定"
                      otherButtonTitles: nil];
        
    }
    else if([AddFriend hasSendBuddyRequest:buddyName])
    {
        NSString *message = [NSString stringWithFormat:@"您已向'%@'发送好友请求了!", buddyName];
        [WCAlertView showAlertWithTitle:message
                                message:nil
                     customizationBlock:nil
                        completionBlock:nil
                      cancelButtonTitle:@"确定"
                      otherButtonTitles: nil];
        
    }else
    {
        [self showMessageAlertView];
    }
    
}

- (void)showMessageAlertView{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"打个招呼吧" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [alert setAlertViewStyle:UIAlertViewStylePlainTextInput];
    [alert show];
}

#pragma alertView Delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (alertView.tag == 100)
    {
        if (buttonIndex == 1)
        {
            [self presentViewController:[[WCUserDataViewController alloc]init] animated:YES completion:^{
                
            }];
        }
    }
    else if (alertView.tag == 101)
    {
        if (buttonIndex == 1)
        {
            
    
          
             [[NSNotificationCenter defaultCenter] postNotificationName:@"show" object:nil userInfo:nil];
    
            
            NSFileManager *flm = [NSFileManager defaultManager];
            
            if ([flm fileExistsAtPath:IOS.autoLandFilePath])
            {
                self.autoLandDictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
            }
            
            [self.autoLandDictionary removeAllObjects];
            
            [self.autoLandDictionary writeToFile:IOS.autoLandFilePath atomically:YES];
            
            
            [self deleteTemp];
            
        }
        
    }
    else
    {
    if (buttonIndex == 1)
    {
        UITextField *messageTextField = [alertView textFieldAtIndex:0];
        
        NSString *messageStr = @"";
        
        if (messageTextField.text.length > 0)
        {
            messageStr = [NSString stringWithFormat:@"%@：%@",self.disChatName, messageTextField.text];
        }
        else
        {
            messageStr = [NSString stringWithFormat:@"%@ 邀请加你为好友",self.disChatName];
        }
        

            [self showHudInView:self.view hint:@"正在发送申请..."];
            EMError *error;
            [[EaseMob sharedInstance].chatManager addBuddy:self.disbuddyName message:messageStr error:&error];
        
            [self hideHud];
            if (error) {
                [self showHint:@"发送申请失败，请重新操作"];
            }
            else
            {
                [self showHint:@"发送申请成功"];
    
            }
        
    }
    }
}



-(void)clickTomacthButton:(UIButton*)sender
{
    
    [self presentViewController:[[WCUserDataViewController alloc]init] animated:YES completion:^{ }];
}





-(void)TapGestureC:(UITapGestureRecognizer*)tap
{
    
    
    IOS.MacthUserId = [NSString stringWithFormat:@"%@", IOS.mathDictionary[@"userId"]];
   
    [self.navigationController pushViewController:[[WCMatchDetailViewController alloc]init] animated:YES];
    
    
}


-(void)showMenuView:(UIButton*)sender
{
    if (ShowMenu)
    {
        userView.hidden = NO;

        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.3];
        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
        userView.frame = CGRectMake(0, 64, 320, isiphone(480-64, 568-64));
        [UIView commitAnimations];
        ShowMenu = NO;
        
    }
    else
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.3];
        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
        userView.frame = CGRectMake(320, 20, 320, isiphone(480-64, 568-64));
        [UIView commitAnimations];
        userView.hidden = YES;

        ShowMenu = YES;
    }
    
   
}

-(void)menuClickTap:(UITapGestureRecognizer*)sender
{
    userView.frame = CGRectMake(320, 20, 320,isiphone(480-64, 568-64));
    ShowMenu = YES;
}

-(void)setButtonClick:(UIButton*)sender
{

     userView.frame = CGRectMake(320, 20, 320,isiphone(480-64, 568-64));
     userView.hidden = YES;
     ShowMenu = YES;
    [self.navigationController pushViewController:[[WCPersonalViewController alloc]init] animated:YES];
  
}

-(void)collectionButtonClick:(UIButton*)sender
{
    
    [self.navigationController pushViewController:[[WCMyCollectViewController alloc]init] animated:YES];
    
}

-(void)logOutButtonClick:(UIButton*)sender
{
    ShowMenu = YES;
    userView.hidden = YES;

    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"温馨提示" message:@"退出登录状态" delegate:self cancelButtonTitle:@"再看看吧" otherButtonTitles:@"是的", nil];
    
    alert.tag = 101;
    
    [alert show];
    
    
}


-(void)aboutButtonClick:(UIButton*)sender
{
    userView.frame = CGRectMake(320, 20, 140, 130);
    ShowMenu = YES;
    userView.hidden = YES;

    [self.navigationController pushViewController:[[WCAboutViewController alloc]init] animated:YES];
}






-(void)imageButtonClickShowBig:(UIButton*)sender
{
    NSDictionary *ActivityDic = [matchMutableArray objectAtIndex:sender.tag];
    
    IOS.macthBigIamgeArray = ActivityDic[@"image"];
    
    IOS.MacthUserId = ActivityDic[@"userId"];
    
    [self presentViewController:[[WCBigImageShowViewController alloc]init] animated:YES completion:^{
        
    }];
    
}

//下载用户头像
-(void)downloadMatcUserImage
{
  
    
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        NSString *userId =[NSString stringWithFormat:@"%@",IOS.mathDictionary[@"userId"]];
        
        
        IOS.MacthUserId = userId;
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_The_user_matching_headImage];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%@",IOS.mathDictionary[@"icon"]]];

        [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image = [UIImage imageWithContentsOfFile:imagefilepath];
        
        dispatch_async(dispatch_get_main_queue(), ^{
            [self.userBgImageView setImage:image];
        });
    });
}

//下载显示用户简介图像1
-(void)downloadMatcUserDisimage1
{
    
    @autoreleasepool
    {
        
        
        NSArray *imageArray = IOS.mathDictionary[@"image"];
        
        NSString *userId =[NSString stringWithFormat:@"%@",IOS.mathDictionary[@"userId"]];
        
        IOS.MacthUserId = userId;
        IOS.imageInforId = imageArray[0];
        
      
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_The_user_matching_InforImage];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];
        
       [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];

        [self.image1 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}

//下载用户简介图像2
-(void)downloadMatcUserDisimage2
{
   
    
    @autoreleasepool
    {
        
        
        NSArray *imageArray = IOS.mathDictionary[@"image"];
        
        NSString *userId =[NSString stringWithFormat:@"%@",IOS.mathDictionary[@"userId"]];
        
        
        NSURL *imageUrl = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?access_token=%@&image=%@",userId,IOS.access_token,imageArray[1]]];
        
        NSLog(@"imageurl=====%@",imageUrl);
        
        NSData *imagedata = [NSData dataWithContentsOfURL:imageUrl];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
        
       [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        [self.image2 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}

//下载用户简介图像3
-(void)downloadMatcUserDisimage3
{

    
    @autoreleasepool
    {
        
        
        NSArray *imageArray = IOS.mathDictionary[@"image"];
        
        NSString *userId =[NSString stringWithFormat:@"%@",IOS.mathDictionary[@"userId"]];
        
        
        NSURL *imageUrl = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?access_token=%@&image=%@",userId,IOS.access_token,imageArray[2]]];
        
        NSLog(@"imageurl=====%@",imageUrl);
        
        NSData *imagedata = [NSData dataWithContentsOfURL:imageUrl];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
        
       [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        [self.image3 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}


-(void)disBigImageShow:(UIButton*)sender
{
    
    
    self.disIamgeView = [[UIImageView alloc]init];
    
    switch (sender.tag)
    {
        case 1:
             self.disIamgeView.image = self.image1.image;
            break;
        case 2:
             self.disIamgeView.image = self.image2.image;
            break;
        case 3:
             self.disIamgeView.image = self.image3.image;
            break;
            
        default:
            break;
    }
   
    
    self.disIamgeView.contentMode = UIViewContentModeScaleAspectFill;
  
    TGRImageViewController *viewController = [[TGRImageViewController alloc] initWithImage:self.disIamgeView.image];
    
    viewController.transitioningDelegate = self;
    viewController.view.backgroundColor = [UIColor clearColor];
    [self presentViewController:viewController animated:YES completion:nil];

    
}


-(void)tagButtonClcik:(UIButton*)sender
{
    switch (sender.tag)
    {
        case 1:
            [self tagLabel1];
            break;
        case 2:
             [self tagLabel2];
            break;
        case 3:
             [self tagLabel3];
            break;
            
        default:
            break;
    }
    
}

-(void)tagLabel1
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;

    
     NSDictionary *funnyDic = IOS.mathDictionary[@"funnyTags"];
    tagLabel1.text = DisTagArray[[funnyDic[funnyDic.allKeys[0]] intValue]];
    [tagLabel1.layer addAnimation:transition forKey:nil];

    [self performSelector:@selector(reductionTaglabel1) withObject:nil afterDelay:2.0];
    
}

-(void)reductionTaglabel1
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;

    
    NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
    tagLabel1.text = funnydic.allKeys[0];
    [tagLabel1.layer addAnimation:transition forKey:nil];
    
}

-(void)tagLabel2
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;
    
    
    NSDictionary *funnyDic = IOS.mathDictionary[@"funnyTags"];
    tagLabel2.text = DisTagArray[[funnyDic[funnyDic.allKeys[1]] intValue]];
    [tagLabel2.layer addAnimation:transition forKey:nil];
    
    [self performSelector:@selector(reductionTaglabel2) withObject:nil afterDelay:2.0];
}

-(void)reductionTaglabel2
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;
    
    
    NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
    tagLabel2.text = funnydic.allKeys[1];
    
    [tagLabel2.layer addAnimation:transition forKey:nil];
  
}

-(void)tagLabel3
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;
    

    
    NSDictionary *funnyDic = IOS.mathDictionary[@"funnyTags"];
    tagLabel3.text = DisTagArray[[funnyDic[funnyDic.allKeys[2]] intValue]];
    [tagLabel3.layer addAnimation:transition forKey:nil];
    
    [self performSelector:@selector(reductionTaglabel3) withObject:nil afterDelay:2.0];
    
}


-(void)reductionTaglabel3
{
    CATransition *transition=[CATransition animation];
    transition.duration=2.0;
    transition.type=kCATransitionFade;
    transition.subtype=kCATransitionFromLeft;
    

    
    NSDictionary *funnydic = IOS.mathDictionary[@"funnyTags"];
    tagLabel3.text = funnydic.allKeys[2];
    [tagLabel3.layer addAnimation:transition forKey:nil];
}




/*

//---------异步加载数据

-(void)downloadDataWithAsynURLConnecton:(NSURL*)RequestUrl;
{
    NSURLRequest *request=[NSURLRequest requestWithURL:RequestUrl];
    [NSURLConnection connectionWithRequest:request delegate:self];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    
    NSHTTPURLResponse *res = (NSHTTPURLResponse *)response;
    
    NSLog(@"%@",[res allHeaderFields]);
    
    self.receiveData = [NSMutableData data];
    
}
//接收到服务器传输数据的时候调用，此方法根据数据大小执行若干次

-(void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    
    [self.receiveData appendData:data];
    
}
//数据传完之后调用此方法

-(void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    
    IOS.dataDictionary = [self.receiveData JSONValue];
    
    NSDictionary *userDic = IOS.dataDictionary[@"result"];
    
    IOS.ResultUserDictionary = userDic;
    
    
    
}
 */


-(void)deleteTemp
{
    NSError *error = nil;
    
    if([[NSFileManager defaultManager] removeItemAtPath:IOS.tempPath error:&error])
    {
        NSLog(@"清除完成");
    }
    else
    {
        NSLog(@"error=%@", error);
    }
}



#pragma mark - UIViewControllerTransitioningDelegate methods

- (id<UIViewControllerAnimatedTransitioning>)animationControllerForPresentedController:(UIViewController *)presented presentingController:(UIViewController *)presenting sourceController:(UIViewController *)source
{
    if ([presented isKindOfClass:TGRImageViewController.class])
    {
        return [[TGRImageZoomAnimationController alloc] initWithReferenceImageView:self.disIamgeView];
    }
    return nil;
}

- (id<UIViewControllerAnimatedTransitioning>)animationControllerForDismissedController:(UIViewController *)dismissed
{
    if ([dismissed isKindOfClass:TGRImageViewController.class])
    {
        return [[TGRImageZoomAnimationController alloc] initWithReferenceImageView:self.disIamgeView];
    }
    return nil;
}




-(void)backFbuttonClick:(UIButton*)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
