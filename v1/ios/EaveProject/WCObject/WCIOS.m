//
//  WCIOS.m
//  EaveProject
//
//  Created by zzc on 14-7-10.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCIOS.h"

static WCIOS *instance;

@implementation WCIOS
+(WCIOS*)getIntance
{
    if (instance == nil)
    {
        instance = [[WCIOS alloc]init];
    }
    
    return instance;
}
@end
