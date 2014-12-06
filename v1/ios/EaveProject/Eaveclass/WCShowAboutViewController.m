//
//  WCShowAboutViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-30.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCShowAboutViewController.h"



@interface WCShowAboutViewController ()

@end

@implementation WCShowAboutViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_back_nc"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(130, 20, 80, 44)];
    disLabel.text = @"屋檐简介";
    disLabel.textAlignment = NSTextAlignmentCenter;
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
    UIButton *backMatchButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backMatchButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backMatchButton];
    [backMatchButton addTarget:self action:@selector(backAboutButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    

    
    showScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    showScrollView.showsVerticalScrollIndicator = NO;
    showScrollView.contentSize = CGSizeMake(320, 510);
    showScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    [self.view addSubview:showScrollView];
    
    
    UITextView *disTextView = [[UITextView alloc]initWithFrame:CGRectMake(10, 20, 300,360)];

    disTextView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    disTextView.text = @"    “屋檐”是一款基于大数据的移动社交应用，致力于帮助用户找到更合适一同生活的人，进而建立起高品质的生活环境。通过屋檐的智能算法推荐，你不仅可以找到适合同居屋檐下的室友，也可以进一步与社区中的邻居朋友建立联系，构筑你的社区社交网络，建立更融洽的社区氛围。\n\n理念\n    依托于大数据挖掘和分析能力，屋檐致力于更好的理解用户，通过智能推荐算法，为用户在信息爆炸的时代带来更简洁高效的社交体验。基于数据整合与分析能力，屋檐系统能够迅速而精确地从多个维度对用户特征进行画像，并开创性的借助机器学习与语义分析技术解读用户社交需求，为用户匹配推荐合适结果。屋檐希望能够通过智能的计算与匹配减轻用户的信息负载，从海量信息中挑选出最有价值的部分推荐给用户，使社交更轻松、更高效。";
    
     disTextView.editable = NO;
    
     disTextView.font = [UIFont systemFontOfSize:14];
    [showScrollView addSubview:disTextView];
    
    
    UILabel *calllabel = [[UILabel alloc]initWithFrame:CGRectMake(15, 390, 120, 20)];
    calllabel.text = @"屋檐联系方式";
    calllabel.font = [UIFont systemFontOfSize:14];
    calllabel.textColor = [UIColor colorWithHue:61/255.0 saturation:116/255.0 brightness:196/255.0 alpha:1.0];
    
    [showScrollView addSubview:calllabel];
    
    
    UILabel *sitelabel = [[UILabel alloc]initWithFrame:CGRectMake(15, 420, 280, 20)];
    sitelabel.text = @"官方网站：http://www.tongjuba.net";
    sitelabel.font = [UIFont systemFontOfSize:14];
    sitelabel.textColor = [UIColor colorWithHue:61/255.0 saturation:116/255.0 brightness:196/255.0 alpha:1.0];
    
    [showScrollView addSubview:sitelabel];
    
    
    
    
    UILabel *sinalabel = [[UILabel alloc]initWithFrame:CGRectMake(15, 440, 280, 20)];
    sinalabel.text = @"新浪微博：http://weibo.com/wooventech";
    sinalabel.font = [UIFont systemFontOfSize:14];
    sinalabel.textColor = [UIColor colorWithHue:61/255.0 saturation:116/255.0 brightness:196/255.0 alpha:1.0];
    
    [showScrollView addSubview:sinalabel];
    
    
    
}


-(void)backAboutButtonClick:(UIButton*)sender
{
    
    
    
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];

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
