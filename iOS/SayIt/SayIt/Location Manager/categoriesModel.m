/*
 
 File:- categoriesModel.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "categoriesModel.h"

@implementation categoriesModel



-(id)initWithData:(NSString*)fNumber vName:(NSString*)vName
{
    if(self==nil)
    {
        self=[super init];
    }
    self.fNumber = fNumber;
    self.vName = vName;
    
    return self;
}



@end
