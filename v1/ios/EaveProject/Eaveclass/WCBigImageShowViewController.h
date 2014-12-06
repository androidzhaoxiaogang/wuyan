//
//  WCBigImageShowViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-12.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WCBigImageShowViewController : UIViewController<UICollectionViewDataSource,UICollectionViewDelegate>
{
    UIScrollView *bigScrollView;
    UIScrollView *bigImageSrollView1;
    UIScrollView *bigImageSrollView2;
    UIScrollView *bigImageSrollView3;
    
    UIImageView *bigImage1;
    UIImageView *bigImage2;
    UIImageView *bigImage3;
    
    
    
    UICollectionView *bigImageCollectionView;
}

@property(nonatomic,strong)NSIndexPath *selcetionIndexPath;
@end
