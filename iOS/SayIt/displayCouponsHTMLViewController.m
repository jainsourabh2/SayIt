
/*

 File:- displayCouponsHTMLViewController.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "displayCouponsHTMLViewController.h"
#import "AppDelegate.h"
@interface displayCouponsHTMLViewController ()

@end

@implementation displayCouponsHTMLViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title = @"Wow!! You won a coupon";
    [self.webviewDisplayCoupon loadHTMLString:self.strDisplayCoupon baseURL:nil];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)btnSaveCoupon:(id)sender
{
    NSLog(@"btnSaveCouponClicked");
    
    [self saveCoupon];
}

-(UIImage *)saveCoupon{
    UIGraphicsBeginImageContext(self.view.bounds.size);
    [self.view.layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *imageView = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    UIImageWriteToSavedPhotosAlbum(imageView, nil, nil, nil); //if you need to save
    
    UIAlertView *couponSavedAlert = [[UIAlertView alloc] initWithTitle:@"Congratulations"
                                                               message:@"Your coupon has been saved Successfully in your device Photo Gallery."
                                                              delegate:nil
                                                     cancelButtonTitle:@"OK"
                                                     otherButtonTitles:nil];
    [couponSavedAlert show];
    
    return imageView;
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
