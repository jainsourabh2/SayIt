
/*
 
 File:- SplashViewController.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "SplashViewController.h"
#import "LocationManager.h"
#import "places.h"
#import "Reachability.h"
#import "AppDelegate.h"

@interface SplashViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

@end

@implementation SplashViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

-(void)viewDidAppear:(BOOL)animated {
    
    Reachability* reachability = [Reachability reachabilityWithHostName:@"www.apple.com"];
    NetworkStatus remoteHostStatus = [reachability currentReachabilityStatus];
    
    if(remoteHostStatus == NotReachable) {
        NSLog(@"not reachable");
        
        UIAlertView *networkAlert = [[UIAlertView alloc] initWithTitle:@"Network"
                                                               message:@"Seems device is not connected to internet.Kindly check."
                                                              delegate:nil
                                                     cancelButtonTitle:@"OK"
                                                     otherButtonTitles:nil];
        [networkAlert show];
    }
    
    else
    {
        if([CLLocationManager locationServicesEnabled]) {
            if([CLLocationManager authorizationStatus] == kCLAuthorizationStatusDenied) {
                NSLog(@"permission denied");
                
                UIAlertView *locationAlert = [[UIAlertView alloc] initWithTitle:@"App Permission Denied"
                                                                        message:@"To re-enable, please go to Settings and turn on Location Service for this app."
                                                                       delegate:nil
                                                              cancelButtonTitle:@"OK"
                                                              otherButtonTitles:nil];
                [locationAlert show];
                
            } else{
                [[LocationManager sharedManager] startUpdatingLocation];

                UIViewController *placeVC = [[UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]] instantiateViewControllerWithIdentifier:@"navController"];
                [self presentViewController:placeVC animated:NO completion:^{
                    AppDelegate * myAppDelegate= (AppDelegate *)[UIApplication sharedApplication].delegate;
                    
                    if(!myAppDelegate.isLocationUpdated)
                    {
                        CLLocationCoordinate2D currentCoordinate = [LocationManager sharedManager].currentCoordinate;
                           NSLog(@"SplashViewController:: isLocationUpdated (%f,%f)",currentCoordinate.latitude,currentCoordinate.longitude);
                        [[LocationManager sharedManager] startUpdatingLocation];
                    }
                }];
            }
        }
        else
        {
            UIAlertView *locationAlert = [[UIAlertView alloc] initWithTitle:@"Location Service Disabled"
                                                                    message:@"To re-enable, please go to Settings and turn on Location Service for this app."
                                                                   delegate:nil
                                                          cancelButtonTitle:@"OK"
                                                          otherButtonTitles:nil];
            [locationAlert show];
        }
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end
