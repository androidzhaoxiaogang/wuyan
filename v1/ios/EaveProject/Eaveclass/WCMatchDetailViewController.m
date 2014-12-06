//
//  WCMatchDetailViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCMatchDetailViewController.h"

#import <ShareSDK/ShareSDK.h>

#import "JYRadarChart.h"

static BOOL ISC = YES;

@interface WCMatchDetailViewController ()
{
    JYRadarChart *p;
}

@end

@implementation WCMatchDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
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
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(100, 20, 110, 44)];
    disLabel.text = @"匹配用户详情";
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:18];
    [self.view addSubview:disLabel];
    
    UIButton *backFdButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backFdButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backFdButton];
    [backFdButton addTarget:self action:@selector(backFdButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    
     shareButton =[UIButton buttonWithType:UIButtonTypeCustom];
     shareButton.frame = CGRectMake(270, 20, 50, 44);
    
    [self.view addSubview:shareButton];
    
    [shareButton addTarget:self action:@selector(shareButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self disSubView];
    
    act = [[wscustomActivity alloc]init];
    act.center = self.view.center;
    [self.view addSubview:act];
    
   
    
}

-(void)viewDidAppear:(BOOL)animated
{

    [self AddDetailData];
    
    act.hidden = YES;
}


-(void)disSubView
{
    
    matchDetailScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    matchDetailScrollView.contentSize = CGSizeMake(320, isiphone(500, 530));
    matchDetailScrollView.showsVerticalScrollIndicator = NO;
    matchDetailScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    [self.view addSubview:matchDetailScrollView];
    
    UIImageView *LineImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 14, 320, 83)];
    LineImageView.image = [UIImage imageNamed:@"wc_person_lineImage"];
    
    [matchDetailScrollView addSubview:LineImageView];
    
    matchDetailuserImage = [[UIImageView alloc]initWithFrame:CGRectMake(122, 17, 76, 76)];
    [matchDetailScrollView addSubview:matchDetailuserImage];
    
    
    
    
    NameLabel = [[UILabel alloc]initWithFrame:CGRectMake(111, 108, 98, 20)];
    NameLabel.textAlignment = NSTextAlignmentCenter;
    NameLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    NameLabel.font = [UIFont systemFontOfSize:18];
    
    [matchDetailScrollView addSubview: NameLabel];
    
    sexImageView = [[UIImageView alloc]initWithFrame:CGRectMake(122, 136, 35, 13)];
    [matchDetailScrollView addSubview:sexImageView];
    
    
    
    ageLabel = [[UILabel alloc]initWithFrame:CGRectMake(139, 132,40, 20)];
    
    ageLabel.textColor = [UIColor whiteColor];
    ageLabel.font = [UIFont systemFontOfSize:14];
    [matchDetailScrollView addSubview: ageLabel];
    
    
    constellationLabel = [[UILabel alloc]initWithFrame:CGRectMake(162, 134, 80, 16)];
    constellationLabel.textColor = [UIColor redColor];
    constellationLabel.font = [UIFont systemFontOfSize:14];
    [matchDetailScrollView addSubview: constellationLabel];
    
    
    
    addressRentLabel = [[UILabel alloc]initWithFrame:CGRectMake(60, 159, 200, 21)];
    
    
    addressRentLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    [addressRentLabel setTextAlignment:NSTextAlignmentCenter];
    
    addressRentLabel.font = [UIFont systemFontOfSize:14];
    
    [matchDetailScrollView addSubview: addressRentLabel];
    
    
    featuresLabel = [[UILabel alloc]initWithFrame:CGRectMake(80, 181, 160, 25)];
    featuresLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 / 255.0 alpha:1.0];
    [featuresLabel setTextAlignment:NSTextAlignmentCenter];
    
    featuresLabel.font = [UIFont systemFontOfSize:14];
    
    [matchDetailScrollView addSubview: featuresLabel];
    
    
    
    
    //-------------雷达图------------
    
    UIImageView *radarBgImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 215, 320, 140)];
    radarBgImageView.image = [UIImage imageNamed:@"wc_matchUser_radarbg"];
    [matchDetailScrollView addSubview:radarBgImageView];
    
    
    
    UIImageView *radarImage = [[UIImageView alloc]initWithFrame:CGRectMake(101, 231, 117, 117)];
    radarImage.image = [UIImage imageNamed:@"wc_radar_bg"];
    [matchDetailScrollView addSubview:radarImage];
    
    
    
    
    
    
    //-------------------------------
    
    UIImageView *lineImageView =[[UIImageView alloc]initWithFrame:CGRectMake(0, 433, 320, 1)];
    lineImageView.image = [UIImage imageNamed:@"wc_matchuser_line"];
    [matchDetailScrollView addSubview:lineImageView];
    
    MutualfriendLabel = [[UILabel alloc]initWithFrame:CGRectMake(23, 440, 80, 20)];
    MutualfriendLabel.textColor = [UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 / 255.0 alpha:1.0];
    MutualfriendLabel.font = [UIFont systemFontOfSize:13];
    [matchDetailScrollView addSubview:MutualfriendLabel];
    
    
    
    
    
}

-(void)AddDetailData
{
    
    if (IOS.ISY)
    {
        NSLog(@"WC_The_user_matching_Detail === %@",WC_The_user_matching_Detail);
        
        NSData *data = [NSData dataWithContentsOfURL:WC_The_user_matching_Detail];
        NSDictionary *resultDic = [data JSONValue];
        Detaildictionary = resultDic[@"result"];
        
    }
    else
    {
        NSData *data = [NSData dataWithContentsOfURL:WC_The_user_matching_boutique_Detail];
        
        NSDictionary *resultDic = [data JSONValue];
        
        Detaildictionary = resultDic[@"result"];
    }
    
    
    if ([Detaildictionary[@"isFavorite"] boolValue])
    {
        [shareButton setBackgroundImage:[UIImage imageNamed:@"wc_collect_Y"] forState:UIControlStateNormal];
        
        ISC = NO;
    }
    else
    {
        [shareButton setBackgroundImage:[UIImage imageNamed:@"wc_collect_N"] forState:UIControlStateNormal];
        
        ISC = YES;
    }
    
    
    
    
    NSArray *array = Detaildictionary[@"tags"];
    
    for (int i = 0; i < array.count; i++)
    {
        if (i<4)
        {
            UIImageView *tagImage = [[UIImageView alloc]initWithFrame:CGRectMake(20+i*75, 366, 70, 25)];
            tagImage.image = [UIImage imageNamed:@"wc_matchUser_labelBg"];
            
            [matchDetailScrollView addSubview:tagImage];
            
            
            UILabel *tagLabel = [[UILabel alloc]initWithFrame:CGRectMake(20+i*75, 366, 70, 25)];
            
            tagLabel.text = array[i];
            tagLabel.textColor = [UIColor whiteColor];
            tagLabel.textAlignment = NSTextAlignmentCenter;
            tagLabel.font = [UIFont systemFontOfSize:15];
            [matchDetailScrollView addSubview:tagLabel];
            
            
        }
        else
        {
            UIImageView *tagImage = [[UIImageView alloc]initWithFrame:CGRectMake(20+(i-4)*75, 398, 70, 25)];
            tagImage.image = [UIImage imageNamed:@"wc_matchUser_labelBg"];
            
            [matchDetailScrollView addSubview:tagImage];
            
            
            UILabel *tagLabel = [[UILabel alloc]initWithFrame:CGRectMake(20+(i-4)*75, 398, 70, 25)];
            tagLabel.text = array[i];
            tagLabel.font = [UIFont systemFontOfSize:15];
            tagLabel.textColor = [UIColor whiteColor];
            tagLabel.textAlignment = NSTextAlignmentCenter;
            [matchDetailScrollView addSubview:tagLabel];
            
            
        }
        
        
    }
    
    
    //----------雷达显示数据--------
    p = [[JYRadarChart alloc] initWithFrame:CGRectMake(60, 190, 200, 200)];
    
    NSDictionary *scoreDic = Detaildictionary[@"score"];
    NSString *score1 = [NSString stringWithFormat:@"%@",scoreDic[@"birthday"]];
    NSString *score2 = [NSString stringWithFormat:@"%@",scoreDic[@"interest"]];
    NSString *score3 = [NSString stringWithFormat:@"%@",scoreDic[@"habit"]];
    NSString *score4 = [NSString stringWithFormat:@"%@",scoreDic[@"degree"]];
    NSString *score5 = [NSString stringWithFormat:@"%@",scoreDic[@"occupation"]];
    
    NSArray *a1 = [NSArray arrayWithObjects:score1,score2,score3,score4,score5, nil];
    
	p.dataSeries = @[a1];
	p.steps = 1;
	//p.showStepText = YES;//显示区间的 如：0 - 100
	p.backgroundColor = [UIColor clearColor];
	p.r = 60;
    
	p.minValue = 0;
	p.maxValue = 100;
    
	p.fillArea = YES;
	p.colorOpacity = 0.7;
	p.attributes = @[@"年龄", @"兴趣爱好", @"生活习惯", @"教育背景", @"行业背景"];
    
	p.showLegend = YES;
    
	[p setColors:@[[UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0], [UIColor colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0]]];
    
	[matchDetailScrollView addSubview:p];
    
    UILabel *DisScoreLabel = [[UILabel alloc]initWithFrame:CGRectMake(142, 260, 50, 50)];
    DisScoreLabel.textColor = [UIColor colorWithRed:61/255.5 green:134/255.0 blue:202/255.0 alpha:1.0];
    
    DisScoreLabel.font = [UIFont boldSystemFontOfSize:35];
    DisScoreLabel.text = [NSString stringWithFormat:@"%@",scoreDic[@"total"]];
    [matchDetailScrollView addSubview:DisScoreLabel];
    
    //----------****--------
    
    
    
    
    MutualfriendLabel.text = [NSString stringWithFormat:@"微博好友(%d)",[Detaildictionary[@"friends"] count]];
    
    NameLabel.text = Detaildictionary[@"name"];
    
    
    ageLabel.text = [NSString stringWithFormat:@"%@", Detaildictionary[@"age"]];
    
    constellationLabel.text = Detaildictionary[@"constellation"];
    
    addressRentLabel.text = [NSString stringWithFormat:@"%@ | %@ - %@/月",Detaildictionary[@"place"],Detaildictionary[@"minPrice"],Detaildictionary[@"maxPrice"]];
    
    NSArray *habitArray = [NSArray arrayWithObjects:@"不拘小节",@"整洁",@"轻度洁癖",@"重度洁癖", nil];
    
    NSArray *occupationArray = [NSArray arrayWithObjects:@"其他",@"服务业",@"制造业/物流/贸易",@"房地产/建筑",@"教育/文化/传媒/娱乐",@"学生",@"IT/金融", nil];
    
    featuresLabel.text = [NSString stringWithFormat:@"%@ | %@",occupationArray[[Detaildictionary[@"occupation"] intValue]],habitArray[[Detaildictionary[@"habit"] intValue]]];
    
    
    
    if ([Detaildictionary[@"gender"]isEqualToString:@"m"])
    {
        sexImageView.image = [UIImage imageNamed:@"wc_macthUser_boy"];
    }
    else
    {
        sexImageView.image = [UIImage imageNamed:@"wc_macthUser_girls"];
    }
    
    
    
    NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",Detaildictionary[@"icon"]];
    
    NSFileManager *fm=[NSFileManager defaultManager];
    
    if([fm fileExistsAtPath:imagefilepath])
    {
        
        [matchDetailuserImage  setImage:[Model circleImage:[UIImage imageWithContentsOfFile:imagefilepath] withParam:0]];
        
    }
    else
    {
        
        [self performSelectorInBackground:@selector(downloadMatcDetailUserImage) withObject:nil];
    }
    
    
    for (int y = 0; y < [Detaildictionary[@"friends"] count]; y++)
    {
        NSArray *fArray = Detaildictionary[@"friends"];
        
        NSDictionary *dic = fArray[y];
        
        NSString *HHH = dic[@"profileImageUrl"];
        
        UIImageView *frendImageView = [[UIImageView alloc]initWithFrame:CGRectMake(105+y*50, 445, 40, 40)];
        [matchDetailScrollView addSubview:frendImageView];
        
        
        frendImageView.image = [Model circleImage:[UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:HHH]]] withParam:1];
        
        
    }
    
    
}







-(void)shareButtonClick:(UIButton*)sender
{
    
    if (ISC)
    {
       [sender setBackgroundImage:[UIImage imageNamed:@"wc_collect_Y"] forState:UIControlStateNormal];
        
        ISC = NO;
        
        [self addCollectionMacthUser];
   
    }
    else
    {
        [sender setBackgroundImage:[UIImage imageNamed:@"wc_collect_N"] forState:UIControlStateNormal];
        ISC = YES;
        
        
        [self deleteCollectionMacthUser];
      
        
        
    }
    
    
    
    

    
}


-(void)addCollectionMacthUser
{
    

    NSData *data = [NSData dataWithContentsOfURL:WC_collect_add];
    
    NSDictionary *dic = [data JSONValue];
    
    if ([dic[@"result"]isEqualToString:@"ok"])
    {
        [Model presentSheet:@"收藏成功"];
    }
    else
    {
        NSLog(@"收藏失败");
    }
        
    
}

-(void)deleteCollectionMacthUser
{
    
 
    NSData *data = [NSData dataWithContentsOfURL:WC_collect_delet];
    
    NSDictionary *dic = [data JSONValue];
    
    if ([dic[@"result"]isEqualToString:@"ok"])
    {
        [Model presentSheet:@"已取消收藏"];
        
    }
    else
    {
        NSLog(@"取消失败");
    }
        

    
}



-(void)downloadMatcDetailUserImage
{
    @autoreleasepool
    {
        
        
        
        NSString *userId =[NSString stringWithFormat:@"%@",Detaildictionary[@"userId"]];
        
        
        NSURL *imageUrl = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/icon?access_token=%@",userId,IOS.access_token]];
        
        NSLog(@"imageurl=====%@",imageUrl);
        
        NSData *imagedata = [NSData dataWithContentsOfURL:imageUrl];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",Detaildictionary[@"icon"]];
        
        [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image = [Model circleImage:[UIImage imageWithContentsOfFile:imagefilepath] withParam:0];
        
        [matchDetailuserImage performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}



-(void)backFdButtonClick:(UIButton*)sender
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
