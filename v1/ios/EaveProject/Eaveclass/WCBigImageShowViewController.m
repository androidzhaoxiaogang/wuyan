//
//  WCBigImageShowViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-12.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCBigImageShowViewController.h"
#import "WCBigImageCell.h"

#define BCellIdentifier @"BCellIdentifier"

@interface WCBigImageShowViewController ()

@end

@implementation WCBigImageShowViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor blackColor];
   
    bigScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0,0, 320, isiphone(480, 568))];
    bigScrollView.showsHorizontalScrollIndicator = NO;
    bigScrollView.contentSize = CGSizeMake(960, isiphone(480, 568));
    bigScrollView.pagingEnabled = YES;
    bigScrollView.backgroundColor =[UIColor blackColor];
    [self.view addSubview:bigScrollView];
    
    
    bigImageSrollView1 = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
    
    bigImageSrollView1.contentSize = CGSizeMake(320, isiphone(480, 568));
    bigImageSrollView1.backgroundColor =[UIColor blackColor];
    [bigScrollView addSubview:bigImageSrollView1];
    
    
    bigImageSrollView2 = [[UIScrollView alloc]initWithFrame:CGRectMake(320, 0, 320, isiphone(480, 568))];
    bigImageSrollView2.contentSize = CGSizeMake(320, isiphone(480, 568));
    bigImageSrollView2.backgroundColor =[UIColor blackColor];
    [bigScrollView addSubview:bigImageSrollView2];
    
    bigImageSrollView3 = [[UIScrollView alloc]initWithFrame:CGRectMake(640, 0, 320, isiphone(480, 568))];
    bigImageSrollView3.contentSize = CGSizeMake(320, isiphone(480, 568));
    bigImageSrollView3.backgroundColor =[UIColor blackColor];
    [bigScrollView addSubview:bigImageSrollView3];
    
    
    bigImage1 = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
    bigImage1.contentMode = UIViewContentModeScaleAspectFit;
    
    bigImage1.userInteractionEnabled = YES;
    [bigImageSrollView1 addSubview:bigImage1];
    bigImage1.image = [UIImage imageNamed:@"p1"];
    
    
    //捏合手势
    UIPinchGestureRecognizer *pinch1=[[UIPinchGestureRecognizer alloc]initWithTarget:self action:@selector(PinchGesture1:)];
    [bigImage1 addGestureRecognizer:pinch1];
    
    
    bigImage2 = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
    bigImage2.userInteractionEnabled = YES;
    bigImage2.contentMode = UIViewContentModeScaleAspectFit;
    [bigImageSrollView2 addSubview:bigImage2];
    bigImage2.image = [UIImage imageNamed:@"p2"];
    
    //捏合手势
    UIPinchGestureRecognizer *pinch2=[[UIPinchGestureRecognizer alloc]initWithTarget:self action:@selector(PinchGesture2:)];
    
    [bigImage2 addGestureRecognizer:pinch2];
    
    bigImage3 = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
    bigImage3.userInteractionEnabled = YES;
    bigImage3.contentMode = UIViewContentModeScaleAspectFit;
    [bigImageSrollView3 addSubview:bigImage3];
    bigImage3.image = [UIImage imageNamed:@"p3"];
    
    //捏合手势
    UIPinchGestureRecognizer *pinch3=[[UIPinchGestureRecognizer alloc]initWithTarget:self action:@selector(PinchGesture3:)];
    [bigImage3 addGestureRecognizer:pinch3];
    
    
    
    UITapGestureRecognizer *Tap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(TapGestureback:)];
    Tap.numberOfTapsRequired = 1;
    
    [bigScrollView addGestureRecognizer:Tap];
    

    
}
-(void)TapGestureback:(UITapGestureRecognizer*)sender
{
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}


-(void)disSubView
{
    
    UICollectionViewFlowLayout * layoutlatest = [[UICollectionViewFlowLayout alloc]init];
    
    layoutlatest.itemSize = CGSizeMake(320, isiphone(480, 568));
    
    
    layoutlatest.minimumInteritemSpacing = 0;
    layoutlatest.minimumLineSpacing = 5;
    
    [layoutlatest setScrollDirection:UICollectionViewScrollDirectionHorizontal];
    
    
    bigImageCollectionView = [[UICollectionView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568)) collectionViewLayout:layoutlatest];
    bigImageCollectionView.pagingEnabled = YES;

    [self.view addSubview:bigImageCollectionView];
    
    [bigImageCollectionView registerClass:[WCBigImageCell  class] forCellWithReuseIdentifier:BCellIdentifier];

    
    bigImageCollectionView.dataSource = self;
    bigImageCollectionView.delegate = self;
    
  
   
    
    
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return IOS.macthBigIamgeArray.count;
    
}
-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    WCBigImageCell *cell = (WCBigImageCell*)[collectionView dequeueReusableCellWithReuseIdentifier:BCellIdentifier forIndexPath:indexPath];
    
    
    
    NSString *introduceImagefilepath1=[IOS.tempPath stringByAppendingFormat:@"/%@",IOS.macthBigIamgeArray[indexPath.row]];
    
    NSLog(@"imagepath======%@",introduceImagefilepath1);
    
    NSFileManager *fm1=[NSFileManager defaultManager];
    
    if([fm1 fileExistsAtPath:introduceImagefilepath1])
    {
    
        [cell.bigImageView setImage:[UIImage imageWithContentsOfFile:introduceImagefilepath1]];
    }
    else
    {
        [self performSelectorInBackground:@selector(downloadMatcUserDisBigImage:) withObject:indexPath];
    }
    

    self.selcetionIndexPath = indexPath;
    
    
   
    //捏合手势
    UIPinchGestureRecognizer *pinch=[[UIPinchGestureRecognizer alloc]initWithTarget:self action:@selector(PinchGesture3:)];
    
    [cell.bigImageView addGestureRecognizer:pinch];
    
    
    return cell;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}



-(void)downloadMatcUserDisBigImage:(NSIndexPath*)indexPath
{
    @autoreleasepool
    {
        
       NSString *imageId = [IOS.macthBigIamgeArray objectAtIndex:indexPath.row];
        
        
        NSURL *imageUrl = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.tongjuba.net/v1/user/%@/image?access_token=%@&image=%@",IOS.MacthUserId,IOS.access_token,imageId]];
        
        
        NSData *imagedata = [NSData dataWithContentsOfURL:imageUrl];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageId];
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        WCBigImageCell *cell=(WCBigImageCell*)[bigImageCollectionView cellForItemAtIndexPath:indexPath];
        
       
        [cell.bigImageView performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES] ;
    
        
        
    }
    
}


//捏合手势的回调方法
-(void)PinchGesture1:(UIPinchGestureRecognizer*)pinch
{
    
    /*
      WCBigImageCell *cell=(WCBigImageCell*)[bigImageCollectionView cellForItemAtIndexPath:self.selcetionIndexPath];
    
    
    float scale=pinch.scale;//提供缩放比例
    
    static float width=0;
    static float height=0;
    
    if (pinch.state==UIGestureRecognizerStateBegan)//把图片设置成当前大小
    {
        width=cell.bigImageView.bounds.size.width;
        height=cell.bigImageView.bounds.size.height;
    }
   // bigScrollView.contentSize = CGSizeMake(320*scale, isiphone(500, 600)*scale);
    
  //  cell.contentView.bounds = CGRectMake(0, 0, 320*scale, isiphone(480, 568)*scale);
    
  //  bigImageCollectionView.contentSize = CGSizeMake(960*scale, isiphone(480, 568)*scale);
    
    cell.bigImageView.bounds = CGRectMake(0, 0, 320*scale, isiphone(480, 568)*scale);*/
    
    float scale=pinch.scale;//提供缩放比例
    
    NSLog(@"scale ===   %f",scale);
    
    static float width=0;
    static float height=0;
    
    if (pinch.state==UIGestureRecognizerStateBegan)//把图片设置成当前大小
    {
        width=bigImage1.bounds.size.width;
        height=bigImage1.bounds.size.height;
        
        NSLog(@"width====%f height ==== %f",width,height);
    }
    
    bigImageSrollView1.contentSize = CGSizeMake(320*scale, isiphone(480, 568)*scale);
    
    bigImage1.frame = CGRectMake(320-320*scale, 320-320*scale, 320*scale, isiphone(480, 568)*scale);
    
    
}


//捏合手势的回调方法
-(void)PinchGesture2:(UIPinchGestureRecognizer*)pinch
{
    
    float scale=pinch.scale;//提供缩放比例
    
    NSLog(@"scale ===   %f",scale);
    
    static float width=0;
    static float height=0;
    
    if (pinch.state==UIGestureRecognizerStateBegan)//把图片设置成当前大小
    {
        width=bigImage2.bounds.size.width;
        height=bigImage2.bounds.size.height;
    }
     bigImageSrollView2.contentSize = CGSizeMake(320*scale, isiphone(500, 600)*scale);

    
    bigImage2.bounds = CGRectMake(0, 0, 320*scale, isiphone(480, 568)*scale);
    
}


//捏合手势的回调方法
-(void)PinchGesture3:(UIPinchGestureRecognizer*)pinch
{
    
    float scale = pinch.scale;//提供缩放比例
    
    NSLog(@"scale ===   %f",scale);
    
    static float width=0;
    static float height=0;
    
    if (pinch.state==UIGestureRecognizerStateBegan)//把图片设置成当前大小
    {
        width=bigImage3.bounds.size.width;
        height=bigImage3.bounds.size.height;
    }
    
    bigImageSrollView3.contentSize = CGSizeMake(320*scale, isiphone(480, 568)*scale);
    
    bigImage3.bounds = CGRectMake(0, 0, 320*scale, isiphone(480, 568)*scale);
    
}


-(void)backMacthDismissButtonClick:(UIButton*)sender
{
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
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
