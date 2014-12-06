//
//  WCModel.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCModel.h"
#import "Reachability.h"
#import "ASIHTTPRequest.h"
#import "SBJson.h"

static WCModel *intance;

@implementation WCModel

+(WCModel*)getIntance
{
    if (intance == nil)
    {
        intance = [[WCModel alloc]init];
        
    }
    
    return intance;
}
-(void)JudgeNetwork
{
    int net = 0;
    if([[Reachability reachabilityForLocalWiFi]currentReachabilityStatus]!=NotReachable)
    {
        net=1;
        NSLog(@"WIFI连接");
    }
    else if ([[Reachability reachabilityForInternetConnection]currentReachabilityStatus]!=NotReachable)
    {
        net=2;
        NSLog(@"互联网连接");
    }
    self.netWork = net;
}


//显示提示框
- (void) performDismiss: (NSTimer *)timer
{
    //定时器调用的处理方法，作用是隐藏提示框
    [alert dismissWithClickedButtonIndex:0 animated:NO];
    
    alert = NULL;
}


- (void)presentSheet:(NSString*)message
{
    //定义提示框信息
    alert = [[UIAlertView alloc]
             initWithTitle:@"提示" message:message
             delegate:self cancelButtonTitle:nil
             otherButtonTitles: nil];
    
    //初始化一个定时器，三个主要参数是，时间间隔NSTimeInterval浮点型，事件代理    delegate和事件处理方法@selector（）
    [NSTimer scheduledTimerWithTimeInterval:1.0f target:self selector: @selector(performDismiss:)
                                   userInfo:nil repeats:NO];
    //显示提示框
    [alert show];
}




-(void)Cancel_the_loading
{
    [bgImageView removeFromSuperview];
    [ActView removeFromSuperview];
    [actLabel removeFromSuperview];
    
}


-(void)disviewLoading:(UIScrollView *)scroll andLabelStr:(NSString *)labelStr
{
    bgImageView = [[UIImageView alloc]initWithFrame:CGRectMake(100, 150, 110, 65)];
    bgImageView.image = [UIImage imageNamed:@"activityLoading"];
    bgImageView.alpha = 0.50;
    [scroll addSubview:bgImageView];
    
    
    ActView = [[UIActivityIndicatorView alloc]initWithFrame:CGRectMake(105, 164, 37, 37)];
    ActView.activityIndicatorViewStyle = UIActivityIndicatorViewStyleWhiteLarge;
    
    [ActView startAnimating];
    
    [scroll addSubview:ActView];
    
    actLabel = [[UILabel alloc]initWithFrame:CGRectMake(145, 174, 70, 21)];
    
    actLabel.text = labelStr;
    
    actLabel.textColor = [UIColor groupTableViewBackgroundColor];
    [scroll addSubview:actLabel];
    
    
}



//----------判断版本更新
-(void)Judge_version_update
{
     NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    
    [request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://itunes.apple.com/lookup?id=%@",@"898172938"]]];
    
    [request setHTTPMethod:@"GET"];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    NSDictionary *jsonData = [NSJSONSerialization JSONObjectWithData:returnData options:0 error:nil];
    
    
    NSArray *jsonArray = [jsonData objectForKey:@"results"];
    
    if (jsonArray == nil) {
        
        [Model presentSheet:@"暂无新版本"];
        return;
        
    }
    
    if ([jsonArray count] == 0) {
        [Model presentSheet:@"暂无新版本"];

        return;
    }
    
    NSDictionary *releaseInfo = [jsonArray objectAtIndex:0];
    
    
    NSString *latestVersion = [releaseInfo objectForKey:@"version"];
    
    trackViewUrl1 = [releaseInfo objectForKey:@"trackViewUrl"];//地址trackViewUrl
    
    NSString *trackName = [releaseInfo objectForKey:@"trackName"];//trackName
    
    
    
    //通过latestVersion和currentVersion的比较，来判断是否有新的更新。
    
    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];
    NSString *currentVersion = [infoDict objectForKey:@"CFBundleVersion"];
    
    
    double doubleCurrentVersion = [currentVersion doubleValue];
    
    if (doubleCurrentVersion  < [latestVersion doubleValue]) {
        
        UIAlertView *alerts;
        alerts = [[UIAlertView alloc] initWithTitle:trackName
            message:@"有新版本，是否升级！"
            delegate: self
            cancelButtonTitle:@"取消"
        otherButtonTitles: @"升级", nil];
        alerts.tag = 1001;
        [alerts show];
    }
    else
    {
        [Model presentSheet:@"暂无新版本"];
    }
    
    //  如果有新的版本，那么就跳转至下载页面，
    
    
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
    if (buttonIndex==1)
    {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:trackViewUrl1]];
    }
    
    
    
}

//去商店评分
-(void)Judge_version_score
{
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    
    [request setURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://itunes.apple.com/lookup?id=%@",@"898172938"]]];
    
    [request setHTTPMethod:@"GET"];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    NSDictionary *jsonData = [NSJSONSerialization JSONObjectWithData:returnData options:0 error:nil];
    
    
    NSArray *jsonArray = [jsonData objectForKey:@"results"];
    
    if (jsonArray == nil) {
        
        [Model presentSheet:@"暂不能评论"];
        return;
        
    }
    
    if ([jsonArray count] == 0) {
        [Model presentSheet:@"暂不能评论"];
        
        return;
    }
    
    
    NSDictionary *releaseInfo = [jsonArray objectAtIndex:0];
    
    trackViewUrl1 = [releaseInfo objectForKey:@"trackViewUrl"];
    
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:trackViewUrl1]];
    
}


-(NSData*)downloadImageData:(NSString *)stringURl
{
    NSError *error = nil ;
    
    NSString *string = [stringURl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    //NSLog(@"string===%@",string);
    
    NSURL *url = [[NSURL alloc]initWithString:string];
    
    
    NSData *imagedata=[NSData dataWithContentsOfURL:url options:(NSDataReadingMappedIfSafe) error:&error];
    
    NSLog(@"error===%@",error);
    
    return imagedata;
    
}


//1.创建post方式的 参数字符串url
-(NSString *)PostURL:(NSMutableDictionary *)params
{
    NSString *postString=@"";
    
    for(NSString *key in [params allKeys])
    {
        NSString *value=[params objectForKey:key];
        
        postString=[postString stringByAppendingFormat:@"%@=%@&",key,value];
    }
    if([postString length]>1)
    {
        postString=[postString substringToIndex:[postString length]-1];
    }
    
    return postString ;
    
}

//2.zwh -自定义的通用方法－－－－－－post数据回服务器，并返回结果数据集

-(NSData *)getDataByPost:(NSMutableDictionary *)params andURL:(NSURL*)url
{
    
    NSString *postURL=[self PostURL:params];
    
    NSError *error;
    NSURLResponse *theResponse;
    
    NSMutableURLRequest *theRequest=[NSMutableURLRequest requestWithURL:url];
    
    [theRequest setHTTPMethod:@"POST"];
    
    [theRequest setHTTPBody:[postURL dataUsingEncoding:NSUTF8StringEncoding]];
    
    [theRequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    
    [theRequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    
    return [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&theResponse error:&error];
    
 
}





-(NSDictionary*)Login:(NSURL *)RequestURL andRequestMutableDic:(NSMutableDictionary *)MutableDic andRequestMethod:(NSString *)Method
    {
        NSMutableDictionary *resultsDictionary = [NSMutableDictionary dictionary];// 返回的 JSON 数据

        
        if ([NSJSONSerialization isValidJSONObject:MutableDic])
        {
            
            NSError *error;
            
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:MutableDic options:NSJSONWritingPrettyPrinted error: &error];
            NSMutableData *tempJsonData = [NSMutableData dataWithData:jsonData];
            NSURL *url = [NSURL URLWithString:@"http://www.tongjuba.net/v1/login"];
            
            ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
            [request addRequestHeader:@"Content-Type" value:@"application/json; charset=utf-8"];
            [request addRequestHeader:@"Accept" value:@"application/json"];
            
            [request setRequestMethod:Method];
            
            [request setPostBody:tempJsonData];
            [request startSynchronous];
            NSError *error1 = [request error];
            if (!error1)
            {
                NSString *response = [request responseString];
                
                
                resultsDictionary = [response JSONValue];
                
                NSLog(@"resultsDictionary：%@",resultsDictionary);

                
              //  NSData* jsonData = [response dataUsingEncoding:NSUTF8StringEncoding];
                
              //  NSLog(@"jsn===%@",jsonData);
                
            }
        }
        
        return resultsDictionary;
}


-(NSDictionary*)RequestData:(NSURL *)RequestURL andRequestMutableDic:(NSMutableDictionary *)MutableDic  andRequestMethod:(NSString *)Method
{
    
    NSMutableDictionary *resultsDictionary = [NSMutableDictionary dictionary];// 返回的 JSON 数据
    
    
    if ([NSJSONSerialization isValidJSONObject:MutableDic])
    {
        
        NSError *error;
        
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:MutableDic options:NSJSONWritingPrettyPrinted error: &error];
        NSMutableData *tempJsonData = [NSMutableData dataWithData:jsonData];
        
        
        
        ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:RequestURL];
        
        [request addRequestHeader:@"Content-Type" value:@"application/json; charset=utf-8"];
        [request addRequestHeader:@"Accept" value:@"application/json"];
        
        [request setRequestMethod:Method];
        
        [request setPostBody:tempJsonData];
        [request startSynchronous];
        NSError *error1 = [request error];
        if (!error1)
        {
            NSString *response = [request responseString];
            
            
            resultsDictionary = [response JSONValue];
            
            NSLog(@"resultsDictionary：%@",resultsDictionary);
            
            
            //  NSData* jsonData = [response dataUsingEncoding:NSUTF8StringEncoding];
            
            //  NSLog(@"jsn===%@",jsonData);
            
        }
    }
    
    return resultsDictionary;

    
}






-(UIImage*)circleImage:(UIImage*)image withParam:(CGFloat)inset
{
    UIGraphicsBeginImageContext(image.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetLineWidth(context, 2);
    CGContextSetStrokeColorWithColor(context, [UIColor blackColor].CGColor);
    CGRect rect = CGRectMake(inset, inset, image.size.width - inset * 2.0f, image.size.height - inset * 2.0f);
    CGContextAddEllipseInRect(context, rect);
    CGContextClip(context);
    
    [image drawInRect:rect];
    CGContextAddEllipseInRect(context, rect);
    CGContextStrokePath(context);
    UIImage *newimg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return newimg;
}


-(void)toUploadPictures:(NSString*)picImageString andRequestURL:(NSURL *)RequestURL andRequestMutableDic:(NSMutableDictionary *)MutableDic withTagId:(int)tagId
{
    @autoreleasepool
    {
        NSString *fullPath = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"] stringByAppendingPathComponent:picImageString];
        
        //分界线的标识符
        NSString *TWITTERFON_FORM_BOUNDARY = @"AaB03x";
        
        //根据url初始化request
        NSMutableURLRequest* request = [NSMutableURLRequest requestWithURL:RequestURL
           cachePolicy:NSURLRequestReloadIgnoringLocalCacheData
            timeoutInterval:10];
        //分界线 --AaB03x
        NSString *MPboundary=[[NSString alloc]initWithFormat:@"--%@",TWITTERFON_FORM_BOUNDARY];
        //结束符 AaB03x--
        NSString *endMPboundary=[[NSString alloc]initWithFormat:@"%@--",MPboundary];

        
        UIImage *image = [UIImage imageWithContentsOfFile:fullPath];
        
        //得到图片的data
        
      //  NSData* data = UIImagePNGRepresentation(image);
        
         NSData* data = UIImageJPEGRepresentation(image, 0.5);
        
        //http body的字符串
      //  NSMutableString *body=[[NSMutableString alloc]init];
        
        //参数的集合的所有key的集合
        
        
        
        
        if (tagId == 0)
        {
            
            [MutableDic setObject:@"IntroductionImageView1.png" forKey:@"image"];
        }
        else if (tagId == 1)
        {
 
            [MutableDic setObject:@"IntroductionImageView2.png" forKey:@"image"];
        }
        else if (tagId == 2)
        {
            
            [MutableDic setObject:@"IntroductionImageView3.png" forKey:@"image"];
        }
        else if (tagId == 5)
        {
             [MutableDic setObject:@"UserImageView.png" forKey:@"icon"];
        }
        
      
        
        /*
        //----------------------------------------------//
        NSArray *keys= [MutableDic allKeys];
        
        //遍历keys
        for(int i=0;i<[keys count];i++)
        {
            //得到当前key
            NSString *key=[keys objectAtIndex:i];
            //如果key不是pic，说明value是字符类型，比如name：Boris
            if(![key isEqualToString:@"pic"])
            {
                //添加分界线，换行
                [body appendFormat:@"%@\r\n",MPboundary];
                //添加字段名称，换2行
                [body appendFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n",key];
                //添加字段的值
                [body appendFormat:@"%@\r\n",[MutableDic objectForKey:key]];
            }
        }
        
        //---------------------------------------------------//
        
        ////添加分界线，换行
        [body appendFormat:@"%@\r\n",MPboundary];
        //声明pic字段，文件名为boris.png
        [body appendFormat:@"Content-Disposition: form-data; name=\"img\"; filename=\"boris.png\"\r\n"];//将pic 改成 img
        
        //声明上传文件的格式
        //[body appendFormat:@"Content-Type: image/png\r\n\r\n"];
        
        [body appendFormat:@"Content-Type:application/octet-stream"];
        
        //声明结束符：--AaB03x--
        NSString *end=[[NSString alloc]initWithFormat:@"\r\n%@",endMPboundary];
        //声明myRequestData，用来放入http body
        NSMutableData *myRequestData=[NSMutableData data];
        //将body字符串转化为UTF8格式的二进制
        [myRequestData appendData:[body dataUsingEncoding:NSUTF8StringEncoding]];
        //将image的data加入
        
        
        [myRequestData appendData:data];
        //加入结束符--AaB03x--
        [myRequestData appendData:[end dataUsingEncoding:NSUTF8StringEncoding]];
        
        //设置HTTPHeader中Content-Type的值
        NSString *content=[[NSString alloc]initWithFormat:@"multipart/form-data; boundary=%@",TWITTERFON_FORM_BOUNDARY];
        //设置HTTPHeader
        [request setValue:content forHTTPHeaderField:@"Content-Type"];
        //设置Content-Length
        [request setValue:[NSString stringWithFormat:@"%lu", (unsigned long)[myRequestData length]] forHTTPHeaderField:@"Content-Length"];
        //设置http body
        [request setHTTPBody:myRequestData];
        //http method
        [request setHTTPMethod:@"POST"];
        
        //建立连接，设置代理
        NSURLConnection *conn = [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
        //设置接受response的data
        if (conn)
        {
            
            NSLog(@"上传图片");
            
        }*/
        
        
        
        NSMutableData *myRequestData=[NSMutableData data];
        //将body字符串转化为UTF8格式的二进制

        //设置HTTPHeader中Content-Type的值
        NSString *content=[[NSString alloc]initWithFormat:@"application/octet-stream; boundary=%@",TWITTERFON_FORM_BOUNDARY];
        //设置HTTPHeader
        [request setValue:content forHTTPHeaderField:@"Content-Type"];
        //设置Content-Length
        [request setValue:[NSString stringWithFormat:@"%lu", (unsigned long)[myRequestData length]] forHTTPHeaderField:@"Content-Length"];
        
        [myRequestData appendData:data];
     
        [request setHTTPBody:myRequestData];
        //http method
        [request setHTTPMethod:@"POST"];
        
        //建立连接，设置代理
        NSURLConnection *conn = [[NSURLConnection alloc] initWithRequest:request delegate:self];
        
        //设置接受response的data
        if (conn)
        {
            
            NSLog(@"上传图片");
            
        }

        
        
    }
    
    
}


-(void)Judge_Automatic_Login
{
    self.autodictionary = [NSMutableDictionary dictionary];
    self.autodictionary = [NSMutableDictionary dictionaryWithContentsOfFile:IOS.autoLandFilePath];
    
    if (self.autodictionary.allKeys.count != 0)
    {
        
        NSDictionary *autodic = [self.autodictionary objectForKey:[self.autodictionary.allKeys objectAtIndex:0]];
        
        if ([[autodic objectForKey:@"auto"] isEqualToString:@"yesAuto"])
        {
            //IOS.username = [autodic objectForKey:@"username"];
            
            
        }
        
    }
}


-(void)AddImageData:(NSString*)filePath
{
    
    //判断本地文件夹是否存在，如果不存在，需要手动创建
    //writeToFile 方法会自动快捷创建路径文件夹，但有时会丢失路径，导致创建失败
    BOOL isDir = NO;
    NSFileManager *fileManager = [NSFileManager defaultManager];
    BOOL existed = [fileManager fileExistsAtPath:filePath isDirectory:&isDir];
    if (!(isDir && existed) )
    {
        [fileManager createDirectoryAtPath:filePath withIntermediateDirectories:YES attributes:nil error:nil];
    }
    
}


//---------异步加载数据

-(void)downloadDataWithAsynURLConnecton:(NSURL*)RequestUrl;
{
    NSURLRequest *request=[NSURLRequest requestWithURL:RequestUrl];
    [NSURLConnection connectionWithRequest:request delegate:self];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    
    NSHTTPURLResponse *res = (NSHTTPURLResponse *)response;
    
    NSLog(@"%@",[res allHeaderFields]);
    
    self.receiveData = [NSMutableData data];
    
}

//图片加圆角
-(void)setImageView:(UIImageView *)imageView addRoundRectWidth:(float)width color:(UIColor *)color
{
    imageView.layer.cornerRadius = imageView.frame.size.width / 2;
    imageView.layer.borderWidth = width;
    imageView.layer.borderColor = color.CGColor;
    imageView.layer.masksToBounds = YES;
}

//接收到服务器传输数据的时候调用，此方法根据数据大小执行若干次

-(void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    
    [self.receiveData appendData:data];
    
}
//数据传完之后调用此方法

-(void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    
   // NSString *receiveStr = [[NSString alloc]initWithData:self.receiveData encoding:NSUTF8StringEncoding];

   // NSDictionary *dataDic = [receiveStr JSONValue];
    
  
    
    IOS.dataDictionary = [self.receiveData JSONValue];
    
 
    
    
}




@end
