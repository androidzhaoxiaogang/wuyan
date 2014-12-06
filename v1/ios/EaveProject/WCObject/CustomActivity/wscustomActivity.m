//
//  wscustomActivity.m
//  Lvzhidu
//
//  Created by kiok on 14-4-29.
//  Copyright (c) 2014年 ws. All rights reserved.
//

#import "wscustomActivity.h"
#import <QuartzCore/QuartzCore.h>
@implementation wscustomActivity



- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        CGRect rect = self.frame;
        rect.size.height = 60;
        rect.size.width = 60;
        self.frame = rect;
        
        self.backgroundColor = [UIColor blackColor];
        self.alpha = 0.7;
        self.layer.cornerRadius = 8.0f;
        
        UIActivityIndicatorView * activity = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
        activity.frame = CGRectMake(20, 10, 20, 20);
        activity.hidesWhenStopped = YES;
        [activity startAnimating];
        [self addSubview:activity];
        
        UILabel * lab = [[UILabel alloc]initWithFrame:CGRectMake(0, 35, 60, 20)];
        lab.text = @"Loading...";
        lab.font = [UIFont fontWithName:@"Helvetica" size:12];
        lab.textColor = [UIColor whiteColor];
        lab.textAlignment = NSTextAlignmentCenter;
        lab.backgroundColor = [UIColor clearColor];
        [self addSubview:lab];
        
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
