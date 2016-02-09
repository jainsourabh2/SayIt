/*
 
 File:- categoriesModel.h
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import <Foundation/Foundation.h>

@interface categoriesModel : NSObject
@property (nonatomic, strong) NSString *fNumber;
@property (nonatomic, strong) NSString *vName;


-(id)initWithData:(NSString*)fNumber vName:(NSString*)vName;


@end
