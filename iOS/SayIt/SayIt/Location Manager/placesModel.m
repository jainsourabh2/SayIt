
/*
 
 File:- placesModel.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "placesModel.h"

@implementation placesModel

-(id)initWithData:(NSString*)placeName vicinity:(NSString*)vicinity icon:(NSString*)icon itemid:(NSString*)itemid place_id:(NSString*)place_id latitude:(double)latitude longitude:(double)longitude
{
    if(self==nil)
    {
        self=[super init];
    }
    self.name = placeName;
    self.vicinity = vicinity;
    self.icon = icon;
    self.itemid = itemid;
    self.place_id = place_id;
    self.latitude = latitude;
    self.longitude = longitude;
    return self;
}


@end
