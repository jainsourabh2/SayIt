
/*
 
 File:- placesModel.h
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import <Foundation/Foundation.h>

@interface placesModel : NSObject

@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) NSString *vicinity;
@property (nonatomic, strong) NSString *icon;
@property (nonatomic, strong) NSString *itemid;
@property (nonatomic, strong) NSString *place_id;
@property (nonatomic) double latitude;
@property (nonatomic) double longitude;


-(id)initWithData:(NSString*)placeName vicinity:(NSString*)vicinity icon:(NSString*)icon itemid:(NSString*)itemid place_id:(NSString*)place_id latitude:(double)latitude longitude:(double)longitude;
@end
