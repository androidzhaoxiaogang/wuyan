//
//  WCFriendViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCFriendViewController.h"
#import "WCFriendTableViewCell.h"
#import "WCMatchDetailViewController.h"
#import "MJRefresh.h"


#import "ChatListViewController.h"
#import "ContactsViewController.h"

@interface WCFriendViewController ()

@end

@implementation WCFriendViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
    
}
-(void)viewWillAppear:(BOOL)animated
{
    self.navigationController.navigationBarHidden = YES;
    [[NSNotificationCenter defaultCenter]postNotificationName:@"showCustomTabBar" object:nil];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
     self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_back_nc"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(130, 20, 80, 44)];
    disLabel.text = @"我的呜友";
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
 
    chooseImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 64, 320, 35)];
    chooseImageView.image = [UIImage imageNamed:@"wc_friend_L"];
    [self.view addSubview:chooseImageView];
 
    UIButton *Lbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    Lbutton.frame = CGRectMake(0, 64, 160, 35);
    [self.view addSubview:Lbutton];
    
    [Lbutton addTarget:self action:@selector(LbuttonClick:) forControlEvents:UIControlEventTouchUpInside];
    
    UIButton *Rbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    Rbutton.frame = CGRectMake(160, 64, 160, 35);
    [self.view addSubview:Rbutton];
    
    [Rbutton addTarget:self action:@selector(RbuttonClick:) forControlEvents:UIControlEventTouchUpInside];
    
    
    
    /*
    self.FriendTableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 100, 320, isiphone(480-149, 568-149))];
    self.FriendTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.FriendTableView];
    
    friendMutableArray = [NSMutableArray arrayWithObjects:@"0",@"1",@"2",@"3",@"4",@"5",@"6",nil];
    
    // 3.集成刷新控件
    // 3.1.下拉刷新
    [self addHeader];
    
    // 3.2.上拉加载更多
    [self addFooter];
    
    
    self.FriendTableView.dataSource = self;
    self.FriendTableView.delegate = self;
    [self.FriendTableView reloadData];
    
    */
    
    
    
}


/*
- (void)addFooter
{
    
    MJRefreshFooterView *footer = [MJRefreshFooterView footer];
    footer.scrollView = self.FriendTableView;
    footer.beginRefreshingBlock = ^(MJRefreshBaseView *refreshView) {
        // 增加5条假数据
        for (int i = 0; i<5; i++) {
            [friendMutableArray addObject:[NSString stringWithFormat:@"%d",100+i]];
        }
        
        // 模拟延迟加载数据，因此2秒后才调用）
        // 这里的refreshView其实就是footer
        [self performSelector:@selector(doneWithView:) withObject:refreshView afterDelay:2.0];
        
        NSLog(@"%@----开始进入刷新状态", refreshView.class);
    };
   
}

- (void)addHeader
{
    
    MJRefreshHeaderView *header = [MJRefreshHeaderView header];
    header.scrollView = self.FriendTableView;
    header.beginRefreshingBlock = ^(MJRefreshBaseView *refreshView) {
        // 进入刷新状态就会回调这个Block
        
        // 增加5条假数据
        for (int i = 0; i<5; i++) {
            [friendMutableArray addObject:[NSString stringWithFormat:@"%d",8+i]];
        }
        
        // 模拟延迟加载数据，因此2秒后才调用）
        // 这里的refreshView其实就是header
        [self performSelector:@selector(doneWithView:) withObject:refreshView afterDelay:2.0];
        
        NSLog(@"%@----开始进入刷新状态", refreshView.class);
    };
    header.endStateChangeBlock = ^(MJRefreshBaseView *refreshView) {
        // 刷新完毕就会回调这个Block
        NSLog(@"%@----刷新完毕", refreshView.class);
    };
    header.refreshStateChangeBlock = ^(MJRefreshBaseView *refreshView, MJRefreshState state) {
        // 控件的刷新状态切换了就会调用这个block
        switch (state) {
            case MJRefreshStateNormal:
                NSLog(@"%@----切换到：普通状态", refreshView.class);
                break;
                
            case MJRefreshStatePulling:
                NSLog(@"%@----切换到：松开即可刷新的状态", refreshView.class);
                break;
                
            case MJRefreshStateRefreshing:
                NSLog(@"%@----切换到：正在刷新状态", refreshView.class);
                break;
            default:
                break;
        }
    };
    [header beginRefreshing];
   
}

- (void)doneWithView:(MJRefreshBaseView *)refreshView
{
    // 刷新表格
    [self.FriendTableView reloadData];
    // (最好在刷新表格后调用)调用endRefreshing可以结束刷新状态
    [refreshView endRefreshing];
}

#pragma mark - Table view data source
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 90;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return friendMutableArray.count;
    
}
-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
            
           
       static NSString *identifier  = @"identifier";
                
        WCFriendTableViewCell *cell = [self.FriendTableView dequeueReusableCellWithIdentifier:identifier];
                
                if (cell == nil)
                {
                    NSArray *objects = [[NSBundle mainBundle]loadNibNamed:@"FriendCell" owner:self options:nil];
                    
                    for (id object in objects)
                    {
                        if ([object isMemberOfClass:[WCFriendTableViewCell class]])
                        {
                            cell = object;
                        }
                    }
                }
                
                
            return cell;
                
                
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.navigationController pushViewController:[[WCMatchDetailViewController alloc]init] animated:YES];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}
*/







-(void)LbuttonClick:(UIButton*)sender
{
    chooseImageView.image = [UIImage imageNamed:@"wc_friend_L"];
    
    addSubView.frame = CGRectMake(0, 64, 640, isiphone(480-149, 568-149));
    
//    self.FriendTableView.delegate = self;
//    self.FriendTableView.dataSource = self;
//    [self.FriendTableView reloadData];
}

-(void)RbuttonClick:(UIButton*)sender
{
    chooseImageView.image = [UIImage imageNamed:@"wc_friend_R"];
    
    addSubView.frame = CGRectMake(-320, 64, 640, isiphone(480-149, 568-149));
    
//    self.FriendTableView.delegate = self;
//    self.FriendTableView.dataSource = self;
//    [self.FriendTableView reloadData];
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
