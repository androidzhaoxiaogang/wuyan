//
//  WCMatchUserViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MJRefresh.h"


@interface WCMatchUserViewController : UIViewController<UIAlertViewDelegate,MJRefreshBaseViewDelegate>
{
    UIScrollView *matchUserScrollView;
    UIButton *Wubutton;
    
    UIView *userView;
    UIImageView *userClickImageView;
    UIButton *clickButton;
        
    NSMutableArray *matchMutableArray;
    
    UIView *TomatchView;
    
    UIView *TomacthAddView;
    
    
    //------------

    UIImageView *userHomeStateImageView;
    
    UILabel *NameLabel;
    UILabel *introduceLabel;
    
  
    
    UIButton *ImageButton1;
    UIButton *ImageButton2;
    UIButton *ImageButton3;
    
    UILabel *tagLabel1;
    UILabel *tagLabel2;
    UILabel *tagLabel3;
    
    
    NSDictionary *resultDic;
    
    
     wscustomActivity *act;
    
    UIImageView *firstLanchShowImageView;
    
    NSArray *DisTagArray;
    
    
    NSString *MM;
    int SS ;
    
}


@property(nonatomic,strong)UIImageView *userBgImageView;


@property(nonatomic,strong)UIImageView *sexImageView;
@property(nonatomic,strong)UIImageView *image1;
@property(nonatomic,strong)UIImageView *image2;
@property(nonatomic,strong)UIImageView *image3;


@property(nonatomic,strong) NSString *disbuddyName;
@property(nonatomic,strong)NSString *disChatName;
@property(nonatomic,strong)NSIndexPath *selectedIndexPath;


@property(nonatomic,strong)UIImageView *disIamgeView;


@property(nonatomic,strong)NSMutableDictionary *autoLandDictionary;

@property(nonatomic)int Level;


@property(nonatomic,strong)NSMutableData *receiveData;

@end
