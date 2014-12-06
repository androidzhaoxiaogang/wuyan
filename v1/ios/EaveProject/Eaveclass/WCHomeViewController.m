//
//  WCHomeViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCHomeViewController.h"
#import "WCHomeCell.h"
#import "WCEventDetailsViewController.h"

#define  HCellIdentifier @"HCellIdentifier"

static int Hcursor = 0;
static int Hlimit = 0;

@interface WCHomeViewController ()



@end

@implementation WCHomeViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
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
    disImageView.image = [UIImage imageNamed:@"wc_nv"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(130, 20, 60, 44)];
    disLabel.text = @"活 动";
    disLabel.textAlignment = NSTextAlignmentCenter;
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
    
  
    
    HomeMutableArray = [NSMutableArray array];
    
    UICollectionViewFlowLayout * layout = [[UICollectionViewFlowLayout alloc]init];
    
    layout.itemSize = CGSizeMake(250, 330);
    layout.headerReferenceSize = CGSizeMake(320, 20);
    layout.minimumInteritemSpacing = 0;
    layout.minimumLineSpacing = 20;
    
    [layout setScrollDirection:UICollectionViewScrollDirectionVertical];
    
    self.HomeCollectionView = [[UICollectionView alloc]initWithFrame:CGRectMake(0, 65, 320, isiphone(480-114, 568-114)) collectionViewLayout:layout];
    
    
    self.HomeCollectionView.showsVerticalScrollIndicator = NO;
    
    self.HomeCollectionView.backgroundColor = [UIColor whiteColor];
    
    
    [self.HomeCollectionView registerClass:[WCHomeCell class] forCellWithReuseIdentifier:HCellIdentifier];
    
    
    
    MJRefreshHeaderView *header = [MJRefreshHeaderView header];
    header.scrollView = self.HomeCollectionView;
    header.delegate = self;
    // 自动刷新
    [header beginRefreshing];
   
    
    /*
    // 3.2.上拉加载更多
    MJRefreshFooterView *footer = [MJRefreshFooterView footer];
    footer.scrollView = self.HomeCollectionView;
    footer.delegate = self;
    */
    
    
    
    self.HomeCollectionView.dataSource = self;
    self.HomeCollectionView.delegate = self;
    
    [self.view addSubview:self.HomeCollectionView];
    
 
    
    
    
    
    
    
}

- (void)doneWithView:(MJRefreshBaseView *)refreshView
{
    // 刷新表格
    [self.HomeCollectionView reloadData];
    
    // (最好在刷新表格后调用)调用endRefreshing可以结束刷新状态
    [refreshView endRefreshing];
}

#pragma mark - 刷新控件的代理方法
#pragma mark 开始进入刷新状态
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    NSLog(@"%@----开始进入刷新状态", refreshView.class);

    
    [Model JudgeNetwork];
    if (Model.netWork != 0)
    {
        
    
        IOS.cursor =  Hcursor + Hlimit;
        
        Hcursor = Hcursor+5;
        IOS.limit = 5;
    
        
        
        NSData *data = [NSData dataWithContentsOfURL:WC_Activity_List];
        

        
        NSDictionary *resultDic =[data JSONValue];
        
        
        NSArray *homeArray = resultDic[@"result"];
        
        if (homeArray.count > 0)
        {
            [HomeMutableArray addObjectsFromArray:homeArray];
        }
        else
        {
            [Model presentSheet:@"暂无更多活动"];
            
        }
    }
    else
    {
        [Model presentSheet:alertdisNet];
    }
    
    // 2.2秒后刷新表格UI
    [self performSelector:@selector(doneWithView:) withObject:refreshView afterDelay:0.5];
  
    
}

#pragma mark 刷新完毕
- (void)refreshViewEndRefreshing:(MJRefreshBaseView *)refreshView
{
    NSLog(@"%@----刷新完毕", refreshView.class);
}

#pragma mark 监听刷新状态的改变
- (void)refreshView:(MJRefreshBaseView *)refreshView stateChange:(MJRefreshState)state
{
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
}

#pragma mark - collection数据源代理
-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    
    return HomeMutableArray.count;
   
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    
    
    
        WCHomeCell *cell = (WCHomeCell*)[collectionView dequeueReusableCellWithReuseIdentifier:HCellIdentifier forIndexPath:indexPath];
    
    
    
        NSDictionary *ActivityDic = [HomeMutableArray objectAtIndex:indexPath.row];
        
        // 设置imageView的图片
    
    
    
      NSString *activityStr = ActivityDic[@"cover"];
    
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",activityStr];
        
        NSFileManager *fm=[NSFileManager defaultManager];
        
        if([fm fileExistsAtPath:imagefilepath])
        {
            cell.HomeDisImageView.image=[UIImage imageWithContentsOfFile:imagefilepath];
        }
        else
        {
            [self performSelectorInBackground:@selector(downloadActivityImage:) withObject:indexPath];
        }
    
        return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    
    
     [collectionView deselectItemAtIndexPath:indexPath animated:YES];
    
    NSDictionary *ActivityDic = [HomeMutableArray objectAtIndex:indexPath.row];
    IOS.activityStr_id = [NSString stringWithFormat:@"%@", ActivityDic[@"id"]];
    IOS.activityStr_namePath = ActivityDic[@"details"];
    
    [self.navigationController pushViewController:[[WCEventDetailsViewController alloc]init] animated:YES];
    
   
    
}
    

-(void)downloadActivityImage:(NSIndexPath*)indexPath
{
    @autoreleasepool
    {
        
        NSDictionary *dic = [HomeMutableArray objectAtIndex:indexPath.row];
        
        IOS.activityStr_token = [NSString stringWithFormat:@"%@", dic[@"id"]];
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Activity_Image];
        
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",dic[@"cover"]];
        
        [Model AddImageData:IOS.tempPath];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        WCHomeCell *cell=(WCHomeCell*)[self.HomeCollectionView cellForItemAtIndexPath:indexPath];
        
        [cell.HomeDisImageView performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
    }
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
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
