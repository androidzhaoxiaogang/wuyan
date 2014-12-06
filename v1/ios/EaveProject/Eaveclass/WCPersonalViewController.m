//
//  WCPersonalViewController.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCPersonalViewController.h"
#import "WCEditorPersonViewController.h"

#import <ShareSDK/ShareSDK.h>

static BOOL ISClcik = YES;


@interface WCPersonalViewController ()


@end

@implementation WCPersonalViewController

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
    
    [[NSNotificationCenter defaultCenter]postNotificationName:@"hideCustomTabBar" object:nil];
    
}

-(void)viewDidDisappear:(BOOL)animated
{
    editordisImageView.hidden = YES;
    editorIntroImageView1.hidden = YES;
    editorIntroImageView2.hidden = YES;
    editorIntroImageView3.hidden = YES;
    ISClcik = YES;
    
}
- (void)viewDidLoad
{
    [super viewDidLoad];
     self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];
    UIImageView *disImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 20, 320, 44)];
    disImageView.image = [UIImage imageNamed:@"wc_back_nc"];
    
    [self.view addSubview:disImageView];
    
    UILabel *disLabel = [[UILabel alloc]initWithFrame:CGRectMake(130, 20, 80, 44)];
    disLabel.text = @"个人主页";
    disLabel.textColor = [UIColor whiteColor];
    disLabel.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:disLabel];
    
    UIButton *backMatchButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backMatchButton.frame = CGRectMake(0, 20, 50, 44);
    [self.view addSubview:backMatchButton];
    [backMatchButton addTarget:self action:@selector(backMatchButtonCLick:) forControlEvents:UIControlEventTouchUpInside];
    

    
    
    UIButton *shareButton = [UIButton buttonWithType:UIButtonTypeCustom];
    shareButton.frame  = CGRectMake(270, 20, 50, 44);
    [shareButton setBackgroundImage:[UIImage imageNamed:@"wc_person_share"] forState:UIControlStateNormal];
    [self.view addSubview:shareButton];
    [shareButton addTarget:self action:@selector(sharePersonButtonCliclk:) forControlEvents:UIControlEventTouchUpInside];
    
    
    UISwipeGestureRecognizer *swipe = [[UISwipeGestureRecognizer alloc]initWithTarget:self action:@selector(swipeGesture:)];
    swipe.direction = UISwipeGestureRecognizerDirectionRight;
    [self.view addGestureRecognizer:swipe];
    
    
     [self disSubView];
    
    act = [[wscustomActivity alloc]init];
    act.center = self.view.center;
    [self.view addSubview:act];

    
    
}


-(void)disSubView
{
    PersonScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, 320, isiphone(480-64, 568-64))];
    PersonScrollView.contentSize = CGSizeMake(320, 510);
    PersonScrollView.showsVerticalScrollIndicator = NO;
    PersonScrollView.backgroundColor = [UIColor groupTableViewBackgroundColor];
    [self.view addSubview:PersonScrollView];
    
    UIImageView *LineImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 13, 320, 75)];
    LineImageView.image = [UIImage imageNamed:@"wc_person_lineImagebg"];
    [PersonScrollView addSubview:LineImageView];
    
  
    
    
    self.userImageView =[[UIImageView alloc]initWithFrame:CGRectMake(122, 14, 73, 73)];
    [Model setImageView:self.userImageView addRoundRectWidth:1.f color:[UIColor blackColor]];
    self.userImageView.image = [UIImage imageNamed:@"wc_user_bg"];
    [PersonScrollView addSubview:self.userImageView];
    
   

    
    
    UIImageView *textImageView = [[UIImageView alloc]initWithFrame:CGRectMake(7, 100, 307, 140)];
    textImageView.image= [UIImage imageNamed:@"wc_person_textBgImage"];
    [PersonScrollView addSubview:textImageView];
    
    
    
    NameLabel = [[UILabel alloc]initWithFrame:CGRectMake(100, 100, 120, 25)];
    
    NameLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    NameLabel.textAlignment = NSTextAlignmentCenter;
    NameLabel.font = [UIFont systemFontOfSize:18];
    [PersonScrollView addSubview: NameLabel];
    
    
    
    introduceLabel  = [[UILabel alloc]initWithFrame:CGRectMake(60, 125, 200, 21)];
    introduceLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    [introduceLabel setTextAlignment:NSTextAlignmentCenter];
    introduceLabel.font = [UIFont systemFontOfSize:14];
    [PersonScrollView addSubview: introduceLabel];
    
    
    ageLabel = [[UILabel alloc]initWithFrame:CGRectMake(100, 147, 120, 21)];
    ageLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    [ageLabel setTextAlignment:NSTextAlignmentCenter];
    ageLabel.font = [UIFont systemFontOfSize:14];
    [PersonScrollView addSubview: ageLabel];
    
    
    addressRentLabel = [[UILabel alloc]initWithFrame:CGRectMake(60, 170, 200, 21)];
    addressRentLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    [addressRentLabel setTextAlignment:NSTextAlignmentCenter];
    addressRentLabel.font = [UIFont systemFontOfSize:14];
    [PersonScrollView addSubview: addressRentLabel];
    
    
    featuresLabel = [[UILabel alloc]initWithFrame:CGRectMake(60, 192, 200, 25)];
    featuresLabel.textColor = [UIColor  colorWithRed:110 / 255.0 green:113 / 255.0 blue:120 /255.0 alpha:1.0];
    [featuresLabel setTextAlignment:NSTextAlignmentCenter];
    featuresLabel.font = [UIFont systemFontOfSize:14];
    [PersonScrollView addSubview: featuresLabel];
    
    UILabel *showlabel1 = [[UILabel alloc]initWithFrame:CGRectMake(15, 220, 200, 20)];
    showlabel1.text = @"点击填写资料可以大大提升匹配度";
    showlabel1.font = [UIFont systemFontOfSize:10];
    showlabel1.textColor = [UIColor colorWithRed:0/255.0 green:117/255.0 blue:169/255.0 alpha:1.0];
    [PersonScrollView addSubview:showlabel1];
    
    
    
    
    UIImageView *TPImageView = [[UIImageView alloc]initWithFrame:CGRectMake(7, 250, 307, 128)];
    TPImageView.image = [UIImage imageNamed:@"wc_person_UpImage"];
    [PersonScrollView addSubview:TPImageView];
    
    
    
        editorIntroImageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(14, 259, 88,88)];
        editorIntroImageView1.image =[UIImage imageNamed:@"wc_person_editor_Upbg"];
        editorIntroImageView1.hidden = YES;
        [PersonScrollView addSubview: editorIntroImageView1];
    
    
       editorIntroImageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(14+100, 259, 88,88)];
        editorIntroImageView2.image =[UIImage imageNamed:@"wc_person_editor_Upbg"];
       editorIntroImageView2.hidden = YES;
       [PersonScrollView addSubview:editorIntroImageView2];

        editorIntroImageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(14+200, 259, 88,88)];
        editorIntroImageView3.image =[UIImage imageNamed:@"wc_person_editor_Upbg"];
       editorIntroImageView3.hidden = YES;
       [PersonScrollView addSubview:editorIntroImageView3];
   
    
    
    
    self.IntroductionImageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(16, 260, 85, 85)];
    self.IntroductionImageView1.layer.cornerRadius = 8;
    self.IntroductionImageView1.layer.masksToBounds = YES;
    self.IntroductionImageView1.image = [UIImage imageNamed:@"wc_person_Upbg"];
    [PersonScrollView addSubview:self.IntroductionImageView1];
    
       
   
    self.IntroductionImageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(16+100, 260, 85, 85)];
    self.IntroductionImageView2.layer.cornerRadius = 8;
    self.IntroductionImageView2.layer.masksToBounds = YES;
    self.IntroductionImageView2.image = [UIImage imageNamed:@"wc_person_Upbg"];
     [PersonScrollView addSubview:self.IntroductionImageView2];
    
    
    
    
    self.IntroductionImageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(16+200, 260, 85, 85)];
    self.IntroductionImageView3.layer.cornerRadius = 8;
    self.IntroductionImageView3.layer.masksToBounds = YES;
    self.IntroductionImageView3.image = [UIImage imageNamed:@"wc_person_Upbg"];
    [PersonScrollView addSubview:self.IntroductionImageView3];
    
    
    
    for (int i = 0; i<3; i++)
    {
       
        UIButton *introductionButton = [UIButton  buttonWithType:UIButtonTypeCustom];
        introductionButton.frame = CGRectMake(16+i*100, 260, 85, 85);
        introductionButton.tag = i;
        [PersonScrollView addSubview:introductionButton];
        [introductionButton addTarget:self action:@selector(uploadUserImage:) forControlEvents:UIControlEventTouchUpInside];
        
    }
    
    
    UILabel *showlabel2 = [[UILabel alloc]initWithFrame:CGRectMake(15, 353, 200, 20)];
    showlabel2.text = @"上传图片展示你的房间和生活方式";
    showlabel2.font = [UIFont systemFontOfSize:10];
    showlabel2.textColor = [UIColor colorWithRed:0/255.0 green:117/255.0 blue:169/255.0 alpha:1.0];
    [PersonScrollView addSubview:showlabel2];
    
    
    
    
    Edtiorbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    Edtiorbutton.frame = CGRectMake(270, 340, 50, 44);
    [Edtiorbutton setImage:[UIImage imageNamed:@"wc_person_editorButton_click"] forState:UIControlStateNormal];
    [PersonScrollView addSubview:Edtiorbutton];
    
    [Edtiorbutton addTarget:self action:@selector(NcButtonClick:) forControlEvents:UIControlEventTouchUpInside];
    
    
    
//---------标签----------------
    UIImageView *TagImageView = [[UIImageView alloc]initWithFrame:CGRectMake(7, 388, 307, 77)];
    TagImageView.image = [UIImage imageNamed:@"wc_person_tagBgImage"];
    [PersonScrollView addSubview:TagImageView];
    
    
    
    
    
    
    
 
    
    UITapGestureRecognizer *setTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(setTapGesture:)];
    
    setTap.numberOfTouchesRequired = 1;
    
    [PersonScrollView addGestureRecognizer:setTap];
   

}

-(void)setTapGesture:(UITapGestureRecognizer*)sender
{
    [self.navigationController pushViewController:[[WCEditorPersonViewController alloc]init] animated:YES];
}

-(void)viewDidAppear:(BOOL)animated
{
    
    [self disPersondata];
    [act removeFromSuperview];
    
}


-(void)disPersondata
{

        NSArray *habitArray = [NSArray arrayWithObjects:@"不拘小节",@"整洁",@"轻度洁癖",@"重度洁癖", nil];
    
        NSArray *occupationArray = [NSArray arrayWithObjects:@"其他",@"服务业",@"制造业/物流/贸易",@"房地产/建筑",@"教育/文化/传媒/娱乐",@"学生",@"IT/金融", nil];
        
        
        [Model JudgeNetwork];
        
        if (Model.netWork != 0)
        {
            NSData *data = [NSData dataWithContentsOfURL:WC_Get_the_user_information];
            
            
            NSDictionary *resultDic = [data JSONValue];
            
            NSDictionary *userDic = resultDic[@"result"];
            
            IOS.ResultUserDictionary = userDic;
            
            
            
            NameLabel.text = userDic[@"name"];
            
            introduceLabel.text = userDic[@"description"];
            
            ageLabel.text = [NSString stringWithFormat:@"%@  %@", userDic[@"age"],userDic[@"constellation"]];
            
            addressRentLabel.text = [NSString stringWithFormat:@"%@ | 租金 %@ - %@/月",userDic[@"place"],userDic[@"minPrice"],userDic[@"maxPrice"]];
            
            
            featuresLabel.text =  [NSString stringWithFormat:@"%@ | %@", [occupationArray objectAtIndex:[userDic[@"occupation"] intValue]],[habitArray objectAtIndex:[userDic[@"habit"] intValue]]];
            
            [self showTags];
            
            imageArray = userDic[@"image"];
            
            if (imageArray.count>0)
            {
                
                NSString *introImagefilepath1=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];
                
                NSFileManager *fm1=[NSFileManager defaultManager];
                
                if([fm1 fileExistsAtPath:introImagefilepath1])
                {
                    
                    self.IntroductionImageView1.image = [UIImage imageWithContentsOfFile:introImagefilepath1];
                }
                else
                {
                    
                    IOS.imageID = [imageArray objectAtIndex:0];
                    
                    self.IntroductionImageView1.tag = 1;
                    
                    [self performSelectorInBackground:@selector(downloadpersonDisImage1) withObject:nil];
                }
                
                
                
            }
            if (imageArray.count>1)
            {
                
                NSString *introImagefilepath2=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
                
                NSFileManager *fm2=[NSFileManager defaultManager];
                
                if([fm2 fileExistsAtPath:introImagefilepath2])
                {
                    self.IntroductionImageView2.image = [UIImage imageWithContentsOfFile:introImagefilepath2];
                }
                else
                {
                    
                    IOS.imageID = [imageArray objectAtIndex:1];
                    self.IntroductionImageView2.tag = 2;
                    [self performSelectorInBackground:@selector(downloadpersonDisImage2) withObject:nil];
                }
            }
            if (imageArray.count > 2)
            {
                
                
                NSString *introImagefilepath3=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
                
                NSFileManager *fm3=[NSFileManager defaultManager];
                
                if([fm3 fileExistsAtPath:introImagefilepath3])
                {
                    self.IntroductionImageView3.image = [UIImage imageWithContentsOfFile:introImagefilepath3];
                    
                }
                else
                {
                    
                    IOS.imageID = [imageArray objectAtIndex:2];
                    self.IntroductionImageView3.tag = 3;
                    
                    [self performSelectorInBackground:@selector(downloadpersonDisImage3) withObject:nil];
                }
            }
            
            
            NSString *headImagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",IOS.ResultUserDictionary[@"icon"]];
            
            NSFileManager *fm=[NSFileManager defaultManager];
            
            if([fm fileExistsAtPath:headImagefilepath])
            {
                self.userImage = [UIImage imageWithContentsOfFile:headImagefilepath];
                self.userImageView.image = [Model circleImage:self.userImage withParam:1];
            }
            else
            {
                [self performSelectorInBackground:@selector(downloadpersonDisImage) withObject:nil];
            }

            
            
        }
        else
        {
            [Model presentSheet:alertdisNet];
        }
        

}


//-------显示标签-----
-(void)showTags
{
    NSArray *tagArray = IOS.ResultUserDictionary[@"tags"];
    
    for (int i = 0; i < tagArray.count; i++)
    {
        
        if (i<4)
        {
            UIImageView *tagImage = [[UIImageView alloc]initWithFrame:CGRectMake(13+i*75, 398, 70, 25)];
            tagImage.image = [UIImage imageNamed:@"wc_matchUser_labelBg"];
            
            [PersonScrollView addSubview:tagImage];
            
            
            UILabel *tagLabel = [[UILabel alloc]initWithFrame:CGRectMake(13+i*75, 398, 70, 25)];
            tagLabel.text = tagArray[i];
            tagLabel.textColor = [UIColor whiteColor];
            tagLabel.textAlignment = NSTextAlignmentCenter;
            tagLabel.font = [UIFont systemFontOfSize:15];
            [PersonScrollView addSubview:tagLabel];
            
            
        }
        else
        {
            UIImageView *tagImage = [[UIImageView alloc]initWithFrame:CGRectMake(13+(i-4)*75, 432, 70, 25)];
            tagImage.image = [UIImage imageNamed:@"wc_matchUser_labelBg"];
            
            [PersonScrollView addSubview:tagImage];
            
            
            UILabel *tagLabel = [[UILabel alloc]initWithFrame:CGRectMake(13+(i-4)*75, 432, 70, 25)];
            tagLabel.text = tagArray[i];
            tagLabel.font = [UIFont systemFontOfSize:15];
            tagLabel.textColor = [UIColor whiteColor];
            tagLabel.textAlignment = NSTextAlignmentCenter;
            [PersonScrollView addSubview:tagLabel];
            
            
        }
        
        
    }
    

}
//-------显示标签---

-(void)NcButtonClick:(UIButton*)sender
{
    
    if (ISClcik)
    {
         [sender setImage:[UIImage imageNamed:@"wc_person_editorButton_click"] forState:UIControlStateNormal];
        
        editorIntroImageView1.hidden = NO;
        editorIntroImageView2.hidden = NO;
        editorIntroImageView3.hidden = NO;
        
        ISClcik = NO;
        
    }
    else
    {

        editorIntroImageView1.hidden = YES;
        editorIntroImageView2.hidden = YES;
        editorIntroImageView3.hidden = YES;
        ISClcik = YES;
    }
    
    
}


-(void)personSetupButtonClick:(UIButton*)sender
{
    
    if (ISClcik)
    {
        bigIView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480, 568))];
        bigIView.backgroundColor = [UIColor blackColor];
        [self.view addSubview:bigIView];
        
        bigUserImageView =[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 320, isiphone(480-64, 568-64))];
        
        bigUserImageView.contentMode = UIViewContentModeScaleAspectFit;
        
        bigUserImageView.image = self.userImage;
        [bigIView addSubview:bigUserImageView];
        
        UITapGestureRecognizer *ImageTap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(ImageTap:)];
        ImageTap.numberOfTapsRequired = 1;
        [bigIView addGestureRecognizer:ImageTap];
        

    }
    else
    {
        
    [self.navigationController pushViewController:[[WCEditorPersonViewController alloc]init] animated:YES];
    }
    
}




-(void)ImageTap:(UITapGestureRecognizer*)sender
{
    [bigIView removeFromSuperview];
}


-(void)uploadUserImage:(UIButton*)sender
{
    
    if (!ISClcik)
    {
     
    UIActionSheet *sheet;
    
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
    {
        
        
        sheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照",@"从相册选择",nil];
        
        
    }
    else
    {
        
        sheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"从相册选择", nil];
        
    }
    
    sheet.tag = 255;
    
    [sheet showInView:self.view];
    
    Up = (int)sender.tag;
        
    }
    
}


-(void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (actionSheet.tag == 255) {
        
        NSUInteger sourceType = 0;
        
        // 判断是否支持相机
        if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
        {
            
            switch (buttonIndex)
            {
                case 0:
                    // 相机
                    sourceType = UIImagePickerControllerSourceTypeCamera;
                    break;
                case 1:
                    // 相册
                    sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
                    break;
                case 2:
                    return;
                    break;
            }
        }
        else {
            if (buttonIndex == 0) {
                sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
            } else {
                return;
            }
        }
        // 跳转到相机或相册页面
        UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
        
        imagePickerController.delegate = self;
        imagePickerController.allowsEditing = YES;
        
        imagePickerController.sourceType = sourceType;
        
        [self presentViewController:imagePickerController animated:YES completion:^{}];
    }

}

#pragma mark - image picker delegte
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    [picker dismissViewControllerAnimated:YES completion:^{}];
    
    self.image = [info objectForKey:UIImagePickerControllerEditedImage];
    
    
    if (Up == 0)
    {
        IOS.imageInforId = [NSString stringWithFormat:@"%d", Up+1];
        [self SetToUploadPictures:@"IntroductionImageView1.png" andDisImageView:self.IntroductionImageView1];
    }
    else if (Up == 1)
    {
        IOS.imageInforId = [NSString stringWithFormat:@"%d", Up+1];
        [self SetToUploadPictures:@"IntroductionImageView2.png" andDisImageView:self.IntroductionImageView2];

    }
    else if (Up == 2)
    {
        IOS.imageInforId = [NSString stringWithFormat:@"%d", Up+1];
        [self SetToUploadPictures:@"IntroductionImageView3.png" andDisImageView:self.IntroductionImageView3];

    }
    
    
    
}


- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    [self dismissViewControllerAnimated:YES completion:^{}];
}

#pragma mark - 保存图片至沙盒
- (void) saveImage:(UIImage *)currentImage withName:(NSString *)imageName
{
    
    NSData *imageData = UIImageJPEGRepresentation(currentImage, 0.5);
    
    // 获取沙盒目录
    self.fullPath = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"] stringByAppendingPathComponent:imageName];
    
    // 将图片写入文件
    [imageData writeToFile:self.fullPath atomically:NO];
    
}


//===保存本地及获取图片定义的方法 可调用
-(void)SetToUploadPictures:(NSString*)DocumentsPicturesName andDisImageView:(UIImageView*)disImageView
{
    [self saveImage:self.image withName:DocumentsPicturesName];
    
    NSString *fullPath = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"] stringByAppendingPathComponent:DocumentsPicturesName];
    
    UIImage *savedImage = [[UIImage alloc] initWithContentsOfFile:fullPath];
    
    [disImageView setImage:savedImage];
    
    self.uploadImgName = DocumentsPicturesName;
    
    
    NSMutableDictionary *MutableDIC =[NSMutableDictionary dictionary];
    [MutableDIC setObject:IOS.access_token forKey:@"access_token"];
    [MutableDIC setObject:IOS.userId forKey:@"id"];
    
    
    [Model toUploadPictures:self.uploadImgName andRequestURL:UPLOAD_SERVER_URL andRequestMutableDic:MutableDIC withTagId:Up];
    
}



-(void)swipeGesture:(UISwipeGestureRecognizer*)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}




-(void)sharePersonButtonCliclk:(UIButton*)sender
{
    
    
    NSString *imagePath = [[NSBundle mainBundle] pathForResource:@"Default@2x"  ofType:@"png"];
    
    //构造分享内容
    id<ISSContent> publishContent = [ShareSDK content:@"快来“屋檐”看看吧!想要和我做室友吗?"
                                       defaultContent:@"默认分享内容，没内容时显示"
                                                image:[ShareSDK imageWithPath:imagePath]
                                                title:@"屋檐" url:@"http://www.wooventech.com"
                                          description:@"分享信息"
                                            mediaType:SSPublishContentMediaTypeNews];
    
    
    
    [ShareSDK showShareActionSheet:nil
                         shareList:nil
                           content:publishContent
                     statusBarTips:YES
                       authOptions:nil
                      shareOptions: nil
                            result:^(ShareType type, SSResponseState state, id<ISSPlatformShareInfo> statusInfo, id<ICMErrorInfo> error, BOOL end) {
                                if (state == SSResponseStateSuccess)
                                {
                                    NSLog(@"分享成功");
                                }
                                else if (state == SSResponseStateFail)
                                {
                                    NSLog(@"分享失败,错误码:%d,错误描述:%@", [error errorCode], [error errorDescription]);
                                }
                            }];
    
    
}






//下载用户头像
-(void)downloadpersonDisImage
{
    @autoreleasepool
    {
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Get_the_user_HeadImage];
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",IOS.ResultUserDictionary[@"icon"]];
        
        [Model AddImageData:IOS.tempPath];

        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
    
        
        
        [self.userImageView performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
}



//下载用户显示图片
-(void)downloadpersonDisImage1
{
    @autoreleasepool
    {
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Get_the_user_image];
        
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[0]];

        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        [self.IntroductionImageView1 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}

-(void)downloadpersonDisImage2
{
    @autoreleasepool
    {
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Get_the_user_image];
        
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[1]];
        
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        [self.IntroductionImageView2 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}


-(void)downloadpersonDisImage3
{
    @autoreleasepool
    {
        
        NSData *imagedata = [NSData dataWithContentsOfURL:WC_Get_the_user_image];
        
        
        NSString *imagefilepath=[IOS.tempPath stringByAppendingFormat:@"/%@",imageArray[2]];
        
        
        [imagedata writeToFile:imagefilepath atomically:YES];
        
        UIImage *image=[UIImage imageWithContentsOfFile:imagefilepath];
        
        [self.IntroductionImageView3 performSelectorOnMainThread:@selector(setImage:) withObject:image waitUntilDone:YES];
        
    }
    
    
}






-(void)backMatchButtonCLick:(UIButton*)sender
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
