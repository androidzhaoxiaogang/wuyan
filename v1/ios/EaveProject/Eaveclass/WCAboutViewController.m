//
//  WCAboutViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-19.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCAboutViewController.h"
#import "WCShowAboutViewController.h"

#import <ShareSDK/ShareSDK.h>

@interface WCAboutViewController ()

@end

@implementation WCAboutViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
-(void)viewWillAppear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter]postNotificationName:@"hideCustomTabBar" object:nil];
    
}
- (void)viewDidLoad
{
    [super viewDidLoad];

    self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_back_nc"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(130, 20, 80, 44)];
    disLabel.text = @"关于";
    disLabel.textAlignment = NSTextAlignmentCenter;
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
    UIButton *backMatchButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backMatchButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backMatchButton];
    [backMatchButton addTarget:self action:@selector(backmacthButtonclick:) forControlEvents:UIControlEventTouchUpInside];
    
    
    [self disSubView];
    
    
}

-(void)disSubView
{
    aboutScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    
    aboutScrollView.contentSize = CGSizeMake(320, isiphone(460, 530));
    aboutScrollView.showsVerticalScrollIndicator = NO;
    aboutScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    [self.view addSubview:aboutScrollView];
    
    
    UIImageView *logoImageView =[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, 190)];
    logoImageView.image = [UIImage imageNamed:@"wc_about_logo"];
    [aboutScrollView addSubview:logoImageView];
    
    
    
    VersionNum = [[UILabel alloc]initWithFrame:CGRectMake(120, 110, 80, 20)];
    VersionNum.textColor = [UIColor whiteColor];
    VersionNum.font = [UIFont systemFontOfSize:15];
    VersionNum.textAlignment = NSTextAlignmentCenter;
    NSDictionary* infoDict =[[NSBundle mainBundle] infoDictionary];
    
 
    VersionNum.text = [NSString stringWithFormat:@"屋檐 %@",[infoDict objectForKey:@"CFBundleVersion"]];
    
    [aboutScrollView addSubview:VersionNum];
    
    
    
    UIImageView *buttonImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 180, 320, 175)];
    buttonImageView.image = [UIImage imageNamed:@"wc_about_buttonBg"];
    [aboutScrollView addSubview:buttonImageView];
    
    
    
    
    
    for (int i = 0; i<4; i++)
    {
        UIButton *gxButton = [UIButton buttonWithType:UIButtonTypeCustom];
        gxButton.frame = CGRectMake(0, 180+i*45,320, 40);
        gxButton.tag = i;
        [aboutScrollView addSubview:gxButton];

        [gxButton addTarget:self action:@selector(Version_update:) forControlEvents:UIControlEventTouchUpInside];
    }
    
    
    
   
    UIImageView *copyrightImageView = [[UIImageView alloc]initWithFrame:CGRectMake(70, isiphone(480-80, 568-100), 181, 42)];
    copyrightImageView.image = [UIImage imageNamed:@"wc_about_copyright"];
    [aboutScrollView addSubview:copyrightImageView];
    
    
    
}




-(void)Version_update:(UIButton*)sender
{
    switch (sender.tag) {
        case 0:
            [Model Judge_version_update];
            break;
            
        case 1:
            [Model Judge_version_score];
            break;
        case 2:
            [self presentViewController:[[WCShowAboutViewController alloc]init] animated:YES completion:^{ }];
            break;
        case 3:
            [self Recommend_to_friends];
            break;
            
        default:
            break;
    }
    

}


-(void)Recommend_to_friends
{
    
    
    NSString *imagePath = [[NSBundle mainBundle] pathForResource:@"Default-568h@2x"  ofType:@"png"];
    
    //构造分享内容
    id<ISSContent> publishContent = [ShareSDK content:@"生活高品质,屋檐找房子;我在屋檐,你在哪里！屋檐客户端可在AppStore及各大安卓平台下载"
                                       defaultContent:@"默认分享内容，没内容时显示"
                                                image:[ShareSDK imageWithPath:imagePath]
                                                title:@"屋檐" url:@"http://www.wooventech.com"
                                          description:@"分享应用"
                                            mediaType:SSPublishContentMediaTypeNews];
    
    
    
    [ShareSDK showShareActionSheet:nil
                         shareList:nil
                           content:publishContent
                     statusBarTips:YES
                       authOptions:nil
                      shareOptions: nil
                            result:^(ShareType type, SSResponseState state, id<ISSPlatformShareInfo> statusInfo, id<ICMErrorInfo> error, BOOL end) {
                                if (state == SSResponseStateSuccess)
                                {
                                    NSLog(@"分享成功");
                                }
                                else if (state == SSResponseStateFail)
                                {
                                    NSLog(@"分享失败,错误码:%d,错误描述:%@", [error errorCode], [error errorDescription]);
                                }
                            }];
     
    
}

-(void)backmacthButtonclick:(UIButton*)sender;
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
