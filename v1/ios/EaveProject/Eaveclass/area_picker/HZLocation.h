//
//  HZLocation.h
//  areapicker
//
//  Created by Cloud Dai on 12-9-9.
//  Copyright (c) 2012年 clouddai.com. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HZLocation : NSObject

@property (strong, nonatomic) NSString *province;//省
@property (strong, nonatomic) NSString *city;//市
@property (strong, nonatomic) NSString *area;//区
@property (strong, nonatomic) NSString *circle;//商圈


@property (strong, nonatomic) NSString *district;
@property (strong, nonatomic) NSString *street;



@property (strong, nonatomic) NSString *provinceCode;//省代码
@property (strong, nonatomic) NSString *cityCode;//市代码
@property (strong, nonatomic) NSString *areaCode;//区代码
@property (strong, nonatomic) NSString *circleCode;//商圈代码

@end
