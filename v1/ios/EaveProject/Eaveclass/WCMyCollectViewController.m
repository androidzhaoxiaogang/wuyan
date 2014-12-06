//
//  WCMyCollectViewController.m
//  EaveProject
//
//  Created by zzc on 14-8-6.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCMyCollectViewController.h"
#import "WCCollectTableViewCell.h"
#import "WCMatchDetailViewController.h"
#import "WCAddFriendViewController.h"
#import "WCUserDataViewController.h"

@interface WCMyCollectViewController ()

@end

@implementation WCMyCollectViewController

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
    [[NSNotificationCenter defaultCenter]postNotificationName:@"hideCustomTabBar" object:nil];
    
    
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_back_nc"];
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(120, 20, 80, 44)];
    disLabel.text = @"我的收藏";
    disLabel.textAlignment = NSTextAlignmentCenter;
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    

    
    UIButton *Rbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    Rbutton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:Rbutton];
    [Rbutton addTarget:self action:@selector(backCollectionClick:) forControlEvents:UIControlEventTouchUpInside];
    
    
    self.CollectionTableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    self.CollectionTableView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    self.CollectionTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.CollectionTableView];
    
    
    
      OccupationArray = [NSArray arrayWithObjects:@"其他",@"服务业",@"制造业/物流/贸易",@"房地产/建筑",@"教育/文化/传媒/娱乐",@"学生",@"IT/金融", nil];
 
   
    act = [[wscustomActivity alloc]init];
    act.center = self.view.center;
    [self.view addSubview:act];
    

    
    
}

-(void)viewDidAppear:(BOOL)animated
{
    
    [self disCollect];
    
    [act removeFromSuperview];
    
}

-(void)disCollect
{

    
        [Model JudgeNetwork];
    
    if (Model.netWork > 0)
    {
        
        NSData *data  =[NSData dataWithContentsOfURL:WC_collect_List];
        
        NSLog(@"WC_collect_List === %@",WC_collect_List);
        
        NSDictionary *resultDic  =[data JSONValue];
        CollectionMutableArray = resultDic[@"result"];
        
        if (CollectionMutableArray.count == 0)
        {
            [Model presentSheet:@"暂无收藏用户"];
        }
        
        self.CollectionTableView.dataSource = self;
        self.CollectionTableView.delegate = self;
        [self.CollectionTableView reloadData];
    }
    else
    {
        [Model presentSheet:alertdisNet];
    }
        

    
    
}




 
 #pragma mark - Table view data source
 -(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
 {
     return 90;
 }
 -(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
 {
    return CollectionMutableArray.count;
    
 }
 -(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
 {
 
 
      static NSString *identifier  = @"identifier";
 
      WCCollectTableViewCell *cell = [self.CollectionTableView dequeueReusableCellWithIdentifier:identifier];
 
      if (cell == nil)
       {
         NSArray *objects = [[NSBundle mainBundle]loadNibNamed:@"CollectCell" owner:self options:nil];
 
         for (id object in objects)
           {
           if ([object isMemberOfClass:[WCCollectTableViewCell class]])
            {
           cell = object;
            }
         }
     }
     
     
     
     NSDictionary *dic  = CollectionMutableArray[indexPath.row];
     
   
     
     if ([dic[@"gender"]isEqualToString:@"m"])
     {
         cell.SexImageView.image = [UIImage imageNamed:@"wc_friend_boy"];
     }
     else
     {
     cell.SexImageView.image = [UIImage imageNamed:@"wc_friend_girls"];
     }
     
     cell.NameLabel.text = dic[@"name"];
     cell.AgeLabel.text = [NSString stringWithFormat:@"%@",dic[@"age"]];
     
     NSString *Hystr = [OccupationArray objectAtIndex:[dic[@"occupation"] intValue]];
     
     cell.Industry_ConstellationLabel.text = [NSString stringWithFormat:@"%@|%@",Hystr,dic[@"constellation"]];
     
     cell.WUButton.tag = indexPath.row;
     [cell.WUButton addTarget:self action:@selector(WuButtonCLick:) forControlEvents:UIControlEventTouchUpInside];
     

     
     
     NSString *imagefilpath =[IOS.tempPath stringByAppendingFormat:@"/%@",dic[@"icon"]];
     
     NSFileManager *fm=[NSFileManager defaultManager];
     
     if([fm fileExistsAtPath:imagefilpath])
     {
          [Model setImageView:cell.UserImageView addRoundRectWidth:1.f color:[UIColor blackColor]];
         cell.UserImageView.image= [UIImage imageWithContentsOfFile:imagefilpath];
     }
     else
     {
         [self performSelectorInBackground:@selector(downloadlistImage:) withObject:indexPath];
     }
     
     
     
     return cell;
 
 
 }
 

//设置删除键
- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return @"删除";
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    NSDictionary *dic = [CollectionMutableArray objectAtIndex:indexPath.row];
    
    IOS.MacthUserId = dic[@"userId"];
    
    NSData *data = [NSData dataWithContentsOfURL:WC_collect_delet];
    NSDictionary *deleteDic = [data JSONValue];
    
    if ([deleteDic[@"result"]isEqualToString:@"ok"])
    {
        NSLog(@"删除成功");
    }
    else
    {
        NSLog(@"删除失败");
    }
    
    
    [CollectionMutableArray removeObjectAtIndex:indexPath.row];
    
    
    [tableView deleteRowsAtIndexPaths:@[indexPath]withRowAnimation:UITableViewRowAnimationAutomatic];
    
    [self.CollectionTableView reloadData];
    
}


 - (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
 {
     
     NSDictionary *dic = [CollectionMutableArray objectAtIndex:indexPath.row];
     IOS.MacthUserId = dic[@"userId"];
     
    [self.navigationController pushViewController:[[WCMatchDetailViewController alloc]init] animated:YES];
     
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
 }



-(void)WuButtonCLick:(UIButton*)sender
{
    
    NSMutableDictionary *autoDic = [NSMutableDictionary dictionary];
    
    NSFileManager *flm = [NSFileManager defaultManager];
    
    if ([flm fileExistsAtPath:IOS.autoLandFilePath])
    {
       autoDic = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
    }
    
     NSString *MM = autoDic[@"P"];
 
    
    
    if ([MM isEqualToString:@"P"])
    {
        
         NSDictionary *dic = [CollectionMutableArray objectAtIndex:sender.tag];
        
        self.disbuddyName = [NSString stringWithFormat:@"%@", dic[@"userId"]];
        self.disChatName = dic[@"name"];
        
        [self clickWuAddFriend:self.disbuddyName];
    }
    else
    {
        
        UIAlertView *alert  = [[UIAlertView alloc]initWithTitle:@"温馨提示" message:@"请完善资料,进行深度匹配！" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"好的", nil];
        alert.tag = 100;
        [alert show];
    }

    
    
    
}


//判断加没加过好友
-(void)clickWuAddFriend:(NSString*)buddyName;
{
    if ([AddFriend didBuddyExist:buddyName])
    {
        NSString *message = [NSString stringWithFormat:@"'%@'已经是你的好友了!", buddyName];
        [WCAlertView showAlertWithTitle:message
                                message:nil
                     customizationBlock:nil
                        completionBlock:nil
                      cancelButtonTitle:@"确定"
                      otherButtonTitles: nil];
        
    }
    else if([AddFriend hasSendBuddyRequest:buddyName])
    {
        NSString *message = [NSString stringWithFormat:@"您已向'%@'发送好友请求了!", buddyName];
        [WCAlertView showAlertWithTitle:message
                                message:nil
                     customizationBlock:nil
                        completionBlock:nil
                      cancelButtonTitle:@"确定"
                      otherButtonTitles: nil];
        
    }else
    {
        [self showMessageAlertView];
    }
    
}

- (void)showMessageAlertView
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"打个招呼吧" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [alert setAlertViewStyle:UIAlertViewStylePlainTextInput];
    alert.tag = 101;
    [alert show];
}

#pragma alertView Delegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (alertView.tag == 101)
    {
        if (buttonIndex == 1)
        {
            UITextField *messageTextField = [alertView textFieldAtIndex:0];
            
            NSString *messageStr = @"";
            
            if (messageTextField.text.length > 0)
            {
                messageStr = [NSString stringWithFormat:@"%@：%@",self.disChatName, messageTextField.text];
            }
            else
            {
                messageStr = [NSString stringWithFormat:@"%@ 邀请加你为好友",self.disChatName];
            }
            
            
            [self showHudInView:self.view hint:@"正在发送申请..."];
            EMError *error;
            [[EaseMob sharedInstance].chatManager addBuddy:self.disbuddyName message:messageStr error:&error];
            
            [self hideHud];
            if (error) {
                [self showHint:@"发送申请失败，请重新操作"];
            }
            else
            {
                [self showHint:@"发送申请成功"];
                
            }
            
        }
    }
    else if (alertView.tag == 100)
    {
        if (buttonIndex == 1)
        {
            [self presentViewController:[[WCUserDataViewController alloc]init] animated:YES completion:^{
                
            }];
        }
    }
    
}






-(void)downloadlistImage:(NSIndexPath*)indexPath
{
    @autoreleasepool
    {
        
        
        NSDictionary *dic = [CollectionMutableArray objectAtIndex:indexPath.row];
    
        
        NSString *userId =[NSString stringWithFormat:@"%@",dic[@"userId"]];
        
        IOS.MacthUserId = userId;
        
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_The_user_matching_headImage];
        
        [Model AddImageData:IOS.tempPath];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",dic[@"icon"]];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image = [UIImage imageWithContentsOfFile:imagefilepath];
        
        WCCollectTableViewCell *cell = (WCCollectTableViewCell*)[self.CollectionTableView cellForRowAtIndexPath:indexPath];
         [Model setImageView:cell.UserImageView addRoundRectWidth:0.f color:[UIColor blackColor]];
        [cell.UserImageView performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
        
    }
}



-(void)backCollectionClick:(UIButton*)sender
{
 
    [self.navigationController popViewControllerAnimated:YES];
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
