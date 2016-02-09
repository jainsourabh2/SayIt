
/*
 
 File:- places.h
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import <UIKit/UIKit.h>
@import CoreLocation;

@interface places : UIViewController
{
    CLLocationCoordinate2D userCurrentCoordinate;
    CLLocation *currentLocation;
    NSMutableArray *placesResponseArray;
    IBOutlet UITableView *tableView;
}

@end

