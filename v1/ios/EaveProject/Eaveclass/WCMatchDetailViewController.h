//
//  WCMatchDetailViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "wscustomActivity.h"

@interface WCMatchDetailViewController : UIViewController
{
    UIScrollView *matchDetailScrollView;
    
    UIImageView *matchDetailuserImage;
    
    UILabel *NameLabel;
    UILabel *ageLabel;
    UILabel *constellationLabel;
    UILabel *addressRentLabel;
    UILabel *featuresLabel;
    
    UIImageView *sexImageView;
    
    UILabel *MutualfriendLabel;
    
    wscustomActivity *act;
    
    NSDictionary *Detaildictionary;
    
    UIButton *shareButton;
    
}


@end
