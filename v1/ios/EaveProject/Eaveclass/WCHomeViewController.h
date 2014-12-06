//
//  WCHomeViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MJRefresh.h"
@interface WCHomeViewController : UIViewController<UICollectionViewDataSource,UICollectionViewDelegate,MJRefreshBaseViewDelegate>
{
    NSMutableArray *HomeMutableArray;

    
}
@property(nonatomic,strong)UICollectionView *HomeCollectionView;

@end
