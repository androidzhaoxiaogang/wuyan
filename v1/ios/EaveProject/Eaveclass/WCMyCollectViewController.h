//
//  WCMyCollectViewController.h
//  EaveProject
//
//  Created by zzc on 14-8-6.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WCMyCollectViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>
{
    NSMutableArray *CollectionMutableArray;
    
    NSArray *OccupationArray;
    wscustomActivity *act;
}

@property(nonatomic,strong)UITableView *CollectionTableView;;

@property(nonatomic,strong) NSString *disbuddyName;
@property(nonatomic,strong)NSString *disChatName;

@end
