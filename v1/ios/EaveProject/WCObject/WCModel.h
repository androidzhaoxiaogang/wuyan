//
//  WCModel.h
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import <Foundation/Foundation.h>

#define  Model [WCModel getIntance]
#define alertdisNet @"无网络接接,请检查网络设置"

@interface WCModel : NSObject<UIAlertViewDelegate,NSURLConnectionDataDelegate>
{
    UIAlertView *alert ;
    
    UIView *disview;
    
    UIImageView *bgImageView;
    
    UIActivityIndicatorView *ActView;
    
    UILabel *actLabel;
    
     NSString *trackViewUrl1;
}
+(WCModel*)getIntance;

//判断网络
-(void)JudgeNetwork;

@property(nonatomic)int netWork;

//===提示框的两个方法
- (void) performDismiss: (NSTimer *)timer;
- (void) presentSheet:(NSString*)message;


//====取消加载提示框
-(void)Cancel_the_loading;
//===加载数据提示框
-(void)disviewLoading:(UIScrollView*)scroll andLabelStr:(NSString*)labelStr;

//----------判断版本更新
-(void)Judge_version_update;
//去商店评分
-(void)Judge_version_score;


//下载图片
-(NSData*)downloadImageData:(NSString*)stringURl;
//1.创建post方式的 参数字符串url
-(NSString *)PostURL:(NSMutableDictionary *)params;


//2.zwh -自定义的通用方法－－－－－－post数据回服务器，并返回结果数据集
-(NSData *)getDataByPost:(NSMutableDictionary *)params andURL:(NSURL*)url;


@property(nonatomic,strong)NSMutableData *receiveData;

//把图片截成圆形
-(UIImage*)circleImage:(UIImage*)image withParam:(CGFloat)inset;

//imageview 加圆角
-(void)setImageView:(UIImageView *)imageView addRoundRectWidth:(float)width color:(UIColor *)color;

//上传图片
-(void)toUploadPictures:(NSString*)picImageString andRequestURL:(NSURL*)RequestURL andRequestMutableDic:(NSMutableDictionary*)MutableDic withTagId:(int)tagId;


-(void)AddImageData:(NSString*)filePath;//创建图片路径


//第三方登录方法
-(NSDictionary*)Login:(NSURL *)RequestURL andRequestMutableDic:(NSMutableDictionary *)MutableDic andRequestMethod:(NSString*)Method;


//请求数据
-(NSDictionary*)RequestData:(NSURL *)RequestURL andRequestMutableDic:(NSMutableDictionary *)MutableDic  andRequestMethod:(NSString*)Method;

@property(nonatomic,strong)NSMutableDictionary *autodictionary;



//----异步加载数据--------
-(void)downloadDataWithAsynURLConnecton:(NSURL*)RequestUrl;


-(void)Judge_Automatic_Login;
@end
