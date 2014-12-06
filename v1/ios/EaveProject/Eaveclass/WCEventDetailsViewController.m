//
//  WCEventDetailsViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCEventDetailsViewController.h"

@interface WCEventDetailsViewController ()

@end

@implementation WCEventDetailsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self){
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
    disLabel.text = @"活动详情";
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:20];
    [self.view addSubview:disLabel];
    
    
    UIButton *backHomeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backHomeButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backHomeButton];
    [backHomeButton addTarget:self action:@selector(backHomeButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    
    
    
    
    EventDetailScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    EventDetailScrollView.contentSize = CGSizeMake(320, 500);
    EventDetailScrollView.showsVerticalScrollIndicator = NO;
    EventDetailScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    [self.view addSubview:EventDetailScrollView];
    
    
     DetailImageView = [[UIImageView alloc]initWithFrame:CGRectMake(20, 20, 280, 420)];
    
     [EventDetailScrollView addSubview:DetailImageView];
    
    
    NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",IOS.activityStr_namePath];
    
    NSFileManager *fm=[NSFileManager defaultManager];
    
    if([fm fileExistsAtPath:imagefilepath])
    {
        DetailImageView.image=[UIImage imageWithContentsOfFile:imagefilepath];
    }
    else
    {
        [self performSelectorInBackground:@selector(downloadActivityDetailImage:) withObject:IOS.activityStr_namePath];
    }
    
   
    
    
    
    
    
    
    
    
}



//下载图像
-(void)downloadActivityDetailImage:(NSString*)filePath
{
    @autoreleasepool
    {

        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Activity_image_Detail];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",filePath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
    
        [DetailImageView performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES] ;
        
        
        
    }
    
}




-(void)backHomeButtonClick:(UIButton*)sender
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
