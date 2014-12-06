//
//  WCCollectTableViewCell.h
//  EaveProject
//
//  Created by zzc on 14-8-6.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WCCollectTableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UIImageView *UserImageView;

@property (strong, nonatomic) IBOutlet UIImageView *SexImageView;
@property (strong, nonatomic) IBOutlet UILabel *AgeLabel;
@property (strong, nonatomic) IBOutlet UILabel *NameLabel;
@property (strong, nonatomic) IBOutlet UILabel *Industry_ConstellationLabel;
@property (strong, nonatomic) IBOutlet UILabel *Adress_RentLabel;
@property (strong, nonatomic) IBOutlet UIButton *WUButton;


@end
