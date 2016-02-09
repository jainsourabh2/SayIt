
/*
 
 File:- LocationManager.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import "LocationManager.h"
#import "AppDelegate.h"

@implementation LocationManager{
    
    // Declaration of private properties
    CLLocationManager *manager;
}


+(instancetype)sharedManager
{
    static LocationManager* sharedManager;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedManager = [[self alloc]init];
    });
    return sharedManager;
}


// Custom implementation  of the init method to set delegate for the CLMolationManager

-(instancetype)init{
    if (self = [super init]) {
        // Custom initialization code
        manager = [[CLLocationManager alloc]init];
        manager.delegate = self;
        manager.desiredAccuracy = kCLLocationAccuracyBest;
        // Setting distance fiter to 100 to get notified only after location change about 100 meter
        manager.distanceFilter = 100;
    
        // Data will not be avaiable until delegate didUpdateLocations() called
        self.isDataAvailable =  FALSE;
        
        // Requesting for authorization
        
        if ([manager respondsToSelector:@selector(requestWhenInUseAuthorization)]){
            [manager requestWhenInUseAuthorization];
        }
        
      
        
#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 80000
        
        // user activated automatic attraction info mode
        CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
        if (status == kCLAuthorizationStatusDenied ||
            status == kCLAuthorizationStatusAuthorizedWhenInUse) {
            // present an alert indicating location authorization required
            // and offer to take the user to Settings for the app via
            // UIApplication -openUrl: and UIApplicationOpenSettingsURLString
            [manager requestAlwaysAuthorization];
        }
        
        [manager requestWhenInUseAuthorization];
#else
        [manager requestAlwaysAuthorization];
#endif

    }
    
    return self;
}

-(void)startUpdatingLocation{
    [manager startUpdatingLocation];
}
-(void)stopUpdatingLocation{
    [manager stopUpdatingLocation];
}

#pragma mark - CLLocationManager Delegates
-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations{
    // Assigning the first object as the current location of the device
    self.currentLocation = [locations lastObject];
    // Updating coordinate
    
    self.currentCoordinate = self.currentLocation.coordinate;

    NSLog(@"LocationManager: Device updated it's location to (%f,%f)",self.currentCoordinate.latitude,self.currentCoordinate.longitude);
    
    self.isDataAvailable =  TRUE;
    
    CLGeocoder * geoCoder = [[CLGeocoder alloc] init];
    [geoCoder reverseGeocodeLocation:self.currentLocation completionHandler:^(NSArray *placemarks, NSError *error) {
        for (CLPlacemark * placemark in placemarks) {
            _userLocality = [placemark locality];
            //NSLog(@"User's current location = %@",_userLocality);
        }
    }];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"kLocationChangeNotification" object:nil userInfo:@{@"location" : self.currentLocation}];
}
@end
