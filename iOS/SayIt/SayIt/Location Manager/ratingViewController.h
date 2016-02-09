
/*
 
 File:- ratingViewController.h
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import <UIKit/UIKit.h>
#import "placesModel.h"

@interface ratingViewController : UIViewController
{
    IBOutlet UITableView *ratingTableView;
    NSMutableArray *categoryResponseArray;

}

@property (nonatomic, strong) placesModel *aModel;

-(IBAction)btnSubmitClicked:(id)sender;
@end
