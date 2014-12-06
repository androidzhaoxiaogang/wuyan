//
//  HZAreaPickerView.m
//  areapicker
//
//  Created by Cloud Dai on 12-9-9.
//  Copyright (c) 2012年 clouddai.com. All rights reserved.
//

#import "HZAreaPickerView.h"
#import <QuartzCore/QuartzCore.h>


#define kDuration 0.3

@interface HZAreaPickerView ()
{
    NSArray *provinces, *cities, *areas,*circle;
    
    UILabel *mycom1;
}

@end

@implementation HZAreaPickerView

@synthesize delegate=_delegate;
@synthesize pickerStyle=_pickerStyle;
@synthesize locate=_locate;
@synthesize locatePicker = _locatePicker;



-(HZLocation *)locate
{
    if (_locate == nil) {
        _locate = [[HZLocation alloc] init];
    }
    
    return _locate;
}

- (id)initWithStyle:(HZAreaPickerStyle)pickerStyle delegate:(id<HZAreaPickerDelegate>)delegate
{
    
    self = [[[NSBundle mainBundle] loadNibNamed:@"HZAreaPickerView" owner:self options:nil] objectAtIndex:0];
    if (self)
    {
        self.delegate = delegate;
        self.pickerStyle = pickerStyle;
        self.locatePicker.dataSource = self;
        self.locatePicker.delegate = self;
        
        
        
        
           //加载数据
    
           NSString *filePath=[[NSBundle mainBundle]pathForResource:@"business" ofType:@"json"];
           NSData *data=[NSData dataWithContentsOfFile:filePath];
        
            provinces = [data JSONValue];
        
        
        
            cities = [[provinces objectAtIndex:0] objectForKey:@"citys"];
       
        
            areas = [[cities objectAtIndex:0]objectForKey:@"areas"];
        
        
            circle = [[areas objectAtIndex:0]objectForKey:@"landmarks"];
        
        

        self.locate.province = [[provinces objectAtIndex:0]objectForKey:@"name"];
        self.locate.city = [[cities objectAtIndex:0]objectForKey:@"name"];
        self.locate.area = [[areas objectAtIndex:0]objectForKey:@"name"];
        self.locate.circle = [[circle objectAtIndex:0]objectForKey:@"name"];
        
        self.locate.provinceCode = [[provinces objectAtIndex:0] objectForKey:@"code"];
        self.locate.cityCode = [[cities objectAtIndex:0] objectForKey:@"code"];
        self.locate.areaCode = [[areas objectAtIndex:0] objectForKey:@"code"];
        self.locate.circleCode = [[circle objectAtIndex:0]objectForKey:@"code"];
       
        
        if ([circle count]>0 )
        {
            
            self.locate.circleCode = [[areas objectAtIndex:0] objectForKey:@"code"];
        }
        else
        {
           
            self.locate.circleCode = @"";
        }

        }
    
    
    return self;
    

}

#pragma mark - PickerView lifecycle

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
        return 4;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    switch (component) {
        case 0:
            return [provinces count];
            break;
        case 1:
            return [cities count];
            break;
        case 2:
            return [areas count];
            break;
        case 3:
            return [circle count];
            break;
            
        default:
            return 0;
            break;
    }
}


- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
   
        switch (component) {
            case 0:
                return [[provinces objectAtIndex:row] objectForKey:@"name"];
                break;
                
            case 1:
                if (cities.count > 0)
                {
                return [[cities objectAtIndex:row] objectForKey:@"name"];
                }
                break;
                
            case 2:
                if (areas.count > 0)
                {
                    return [[areas objectAtIndex:row]objectForKey:@"name"];
                }
                
                    break;
                
            case 3:
                if ([circle count] > 0)
                {
                    return  [[circle objectAtIndex:row]objectForKey:@"name"];
                    break;
                }
                else
                {
                    return nil;
                    break;
                }
            default:
                return  nil;
                break;
        }
    
 
    return nil;
    
}



- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
   
    switch (component)
    {
            
            case 0:
                
                cities = [[provinces objectAtIndex:row] objectForKey:@"citys"];
                [self.locatePicker selectRow:0 inComponent:1 animated:YES];
                [self.locatePicker reloadComponent:1];
            
            
                areas = [[cities objectAtIndex:0] objectForKey:@"areas"];
                [self.locatePicker selectRow:0 inComponent:2 animated:YES];
                [self.locatePicker reloadComponent:2];
                
            
                circle = [[areas objectAtIndex:0]objectForKey:@"landmarks"];
                [self.locatePicker selectRow:0 inComponent:3 animated:YES];
                [self.locatePicker reloadComponent:3];
                    
            
            
            self.locate.province = [[provinces objectAtIndex:row]objectForKey:@"name"];
            self.locate.provinceCode = [[provinces objectAtIndex:row] objectForKey:@"code"];
            
            self.locate.city = [[cities objectAtIndex:0]objectForKey:@"name"];
            self.locate.cityCode = [[cities objectAtIndex:0] objectForKey:@"code"];
            
            self.locate.area = [[areas objectAtIndex:0]objectForKey:@"name"];
            self.locate.areaCode = [[areas objectAtIndex:0] objectForKey:@"code"];
            
            if (circle.count > 0)
            {
                self.locate.circle = [[circle objectAtIndex:0]objectForKey:@"name"];
                self.locate.circleCode = [[circle objectAtIndex:0]objectForKey:@"code"];
            }
            else
            {
                self.locate.circle = @"";
                self.locate.circleCode = @"";
            }
    
           
            
            
                break;
                
            case 1:
            
          
                areas = [[cities objectAtIndex:row] objectForKey:@"areas"];
                [self.locatePicker selectRow:0 inComponent:2 animated:YES];
                [self.locatePicker reloadComponent:2];
            
            
            
            self.locate.city = [[cities objectAtIndex:row]objectForKey:@"name"];
            self.locate.cityCode = [[cities objectAtIndex:row] objectForKey:@"code"];

            self.locate.area = [[areas objectAtIndex:0]objectForKey:@"name"];
            self.locate.areaCode = [[areas objectAtIndex:0] objectForKey:@"code"];
            
            
            circle = [[areas objectAtIndex:0]objectForKey:@"landmarks"];
            [self.locatePicker selectRow:0 inComponent:3 animated:YES];
            [self.locatePicker reloadComponent:3];
                    
            
            
            if (circle.count > 0)
            {
                self.locate.circle = [[circle objectAtIndex:0]objectForKey:@"name"];
                self.locate.circleCode = [[circle objectAtIndex:0]objectForKey:@"code"];
            }
            else
            {
                self.locate.circle = @"";
                self.locate.circleCode = @"";
            }
        
                break;
                
            case 2:
            
          
                circle = [[areas objectAtIndex:row]objectForKey:@"landmarks"];
                
                [self.locatePicker selectRow:0 inComponent:3 animated:YES];
                [self.locatePicker reloadComponent:3];
                
                
                 self.locate.area = [[areas objectAtIndex:row]objectForKey:@"name"];
                 self.locate.areaCode = [[areas objectAtIndex:row] objectForKey:@"code"];
            
                
                if (circle.count > 0)
                {
                    self.locate.circle = [[circle objectAtIndex:0]objectForKey:@"name"];
                    self.locate.circleCode = [[circle objectAtIndex:0]objectForKey:@"code"];
                }
                else
                {
                    self.locate.circle = @"";
                    self.locate.circleCode = @"";
                }
                
        
        
            
                break;
            case 3:
            
            if (circle.count >0)
            {
                self.locate.circle = [[circle objectAtIndex:row]objectForKey:@"name"];
                self.locate.circleCode = [[circle objectAtIndex:row]objectForKey:@"code"];
            }
            else
            {
                self.locate.circle = @"";
                self.locate.circleCode = @"";
            }
            
               break;
                
            default:
                break;
        }
   
    if([self.delegate respondsToSelector:@selector(pickerDidChaneStatus:)])
      {
        [self.delegate pickerDidChaneStatus:self];
    }

}


- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row
          forComponent:(NSInteger)component reusingView:(UIView *)view
{
    mycom1 = view ? (UILabel *) view : [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 80.0f, 30.0f)];
    
    switch (component)
    {
        case 0:
            mycom1.text = [[provinces objectAtIndex:row]objectForKey:@"name"];
            break;
        case 1:
            mycom1.text = [[cities objectAtIndex:row]objectForKey:@"name"];
            break;
        case 2:
            mycom1.text = [[areas objectAtIndex:row]objectForKey:@"name"];
            break;
        case 3:
            mycom1.text = [[circle objectAtIndex:row]objectForKey:@"name"];
            break;
            
        default:
            break;
    }
    
    
    [mycom1 setFont:[UIFont boldSystemFontOfSize:15]];
    // mycom1.backgroundColor = [UIColor clearColor];
    
   // CFShow(CFBridgingRetain(mycom1));
    
    
    return mycom1;
}



#pragma mark - animation

- (void)showInView:(UIView *) view
{
    self.frame = CGRectMake(0, view.frame.size.height, self.frame.size.width, self.frame.size.height);
    [view addSubview:self];
    
    [UIView animateWithDuration:0.3 animations:^{
        self.frame = CGRectMake(0, view.frame.size.height - self.frame.size.height, self.frame.size.width, self.frame.size.height);
    }];
    
}

- (void)cancelPicker
{
    
    [UIView animateWithDuration:0.3
                     animations:^{
                         self.frame = CGRectMake(0, self.frame.origin.y+self.frame.size.height, self.frame.size.width, self.frame.size.height);
                     }
                     completion:^(BOOL finished){
                         [self removeFromSuperview];
                         
                     }];
    
}

@end
