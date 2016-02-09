
/*
 
 File:- ratingTableViewCell.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "ratingTableViewCell.h"

@implementation ratingTableViewCell

- (void)awakeFromNib {
    // Initialization code
    [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating3.png"] forState:UIControlStateNormal];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}

-(IBAction)changeSlider:(id)sender
{
    NSLog(@"Slider Value %d", (int)self.ratingSlider.value);
    int slidervalue = (int)self.ratingSlider.value;
    switch (slidervalue) {
        case 0:
            
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating1.png"] forState:UIControlStateNormal];
            
            break;
        case 1:
            
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating2.png"] forState:UIControlStateNormal];
            break;
        case 2:
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating3.png"] forState:UIControlStateNormal];
            break;
        case 3:
            
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating4.png"] forState:UIControlStateNormal];
            break;
        case 4:
            
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating5.png"] forState:UIControlStateNormal];
            break;
            
        default:
            
            [self.ratingSlider setThumbImage:[UIImage imageNamed: @"rating5.png"] forState:UIControlStateNormal];
            break;
    }
    
}

@end
