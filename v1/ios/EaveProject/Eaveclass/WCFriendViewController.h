//
//  WCFriendViewController.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WCFriendViewController : UIViewController//<UITableViewDataSource,UITableViewDelegate>
{
    UIImageView *chooseImageView;
    
    NSMutableArray *friendMutableArray;
    
    UIView *addSubView;
    
    
}

//@property(nonatomic,strong)UITableView *FriendTableView;

@end
