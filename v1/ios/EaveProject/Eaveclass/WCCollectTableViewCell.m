//
//  WCCollectTableViewCell.m
//  EaveProject
//
//  Created by zzc on 14-8-6.
//  Copyright (c) 2014年 WeCash. All rights reserved.
//

#import "WCCollectTableViewCell.h"

@implementation WCCollectTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
