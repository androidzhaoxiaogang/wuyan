//
//  WCPersonalViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WCAppDelegate.h"

@interface WCPersonalViewController : UIViewController<UINavigationControllerDelegate,UIImagePickerControllerDelegate,UIActionSheetDelegate,UIAlertViewDelegate>
{
    UIButton *Edtiorbutton;
    
    UIScrollView *PersonScrollView;
    
    UIImageView *editordisImageView;
    
    UIImageView *editorIntroImageView1;
    UIImageView *editorIntroImageView2;
    UIImageView *editorIntroImageView3;
    
    
    
    UIButton *personImageButton;
    UIImageView *sexImageView;
    
    UILabel *NameLabel;
    UILabel *introduceLabel;
    UILabel *ageLabel;
    UILabel *addressRentLabel;
    UILabel *featuresLabel;
    
    int Up;
    wscustomActivity *act;
    
    UIView *bigIView;
    
    UIImageView *bigUserImageView ;
    
    NSArray *imageArray;
}
@property(nonatomic,strong)UIImageView *userImageView;

@property(nonatomic,strong)UIImageView *IntroductionImageView1;
@property(nonatomic,strong)UIImageView *IntroductionImageView2;
@property(nonatomic,strong)UIImageView *IntroductionImageView3;

@property(nonatomic,strong)NSMutableDictionary *autoLandDictionary;

@property(nonatomic,strong)UIImage *image;
@property(nonatomic,strong)NSString *fullPath;
@property(nonatomic,retain)NSString *uploadImgName;

@property(nonatomic,strong)UIImage *userImage;



@end
