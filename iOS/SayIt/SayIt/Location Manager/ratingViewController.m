
/*
 
 File:- ratingViewController.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */

#import "ratingViewController.h"
#import "categoriesModel.h"
#import "displayCouponsHTMLViewController.h"
#import "ratingTableViewCell.h"
#define kBgQueue dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)
#define getRatingsURL @"http://54.254.253.37/sayit/service/cat/" 
//http://54.254.253.37/sayit/service/cat/Restaurant

#define submitRatingsURL @"http://54.254.253.37/sayit/service/submission/"

@interface ratingViewController ()

@end

@interface SizableImageCellNew : UITableViewCell {}
@end
@implementation SizableImageCellNew
- (void)layoutSubviews {
    [super layoutSubviews];
    
    float desiredWidth = 30;
    float w=self.imageView.frame.size.width;
    if (w>desiredWidth) {
        float widthSub = w - desiredWidth;
        self.imageView.frame = CGRectMake(self.imageView.frame.origin.x,self.imageView.frame.origin.y,desiredWidth,self.imageView.frame.size.height);
        self.textLabel.frame = CGRectMake(self.textLabel.frame.origin.x-widthSub,self.textLabel.frame.origin.y,self.textLabel.frame.size.width+widthSub,self.textLabel.frame.size.height);
        self.detailTextLabel.frame = CGRectMake(self.detailTextLabel.frame.origin.x-widthSub,self.detailTextLabel.frame.origin.y,self.detailTextLabel.frame.size.width+widthSub,self.detailTextLabel.frame.size.height);
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
    }
}
@end

@implementation ratingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIImageView *tempImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"ratingBackground.png"]];
    [tempImageView setFrame:ratingTableView.bounds];
    ratingTableView.backgroundView = tempImageView;
    
    categoryResponseArray=[[NSMutableArray alloc]init];
    
    [self getRatings:@"Restaurant"];
    // Do any additional setup after loading the view.
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
    
    UIImageView *placeIcon = [[UIImageView alloc] initWithImage:[UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:self.aModel.icon]]]];
    
    [placeIcon setFrame:CGRectMake(0, 7.5, 30, 30)];
    
    
    UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(35, 0, 200, 20)];
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.font = [UIFont boldSystemFontOfSize:14];
    titleLabel.text = [NSString stringWithFormat:@"%@",self.aModel.name];
    UILabel *subTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(35, 22, 200, 20)];
    subTitleLabel.backgroundColor = [UIColor clearColor];
    subTitleLabel.font = [UIFont systemFontOfSize:10];
    subTitleLabel.text = [NSString stringWithFormat:@"%@",self.aModel.vicinity];
    
    UIView *twoLineTitleView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 250, 45)];
    
    [twoLineTitleView addSubview:placeIcon];
    [twoLineTitleView addSubview:titleLabel];
    [twoLineTitleView addSubview:subTitleLabel];
    
    self.navigationItem.titleView = twoLineTitleView;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void) getRatings:(NSString *)strCategory {
    
    
    NSString *urlFinal = [NSString stringWithFormat:@"%@%@",getRatingsURL,strCategory];
    
    //Formulate the string as a URL object.
    NSURL *requestURL=[NSURL URLWithString:urlFinal];
    
    // Retrieve the results of the URL.
    dispatch_async(kBgQueue, ^{
        NSData* data = [NSData dataWithContentsOfURL: requestURL];
        [self performSelectorOnMainThread:@selector(fetchedData:) withObject:data waitUntilDone:YES];
    });
}

-(void)fetchedData:(NSData *)responseData {
    //parse out the json data
    NSError* error;
    
    
    NSString* serverResponse = [NSJSONSerialization
                                JSONObjectWithData:responseData
                                options:NSJSONReadingAllowFragments
                                error:&error];
    
    NSData * dat = [serverResponse dataUsingEncoding:NSUTF8StringEncoding];
    
    NSArray *arrayOfStrings = [NSJSONSerialization JSONObjectWithData:dat options:NSJSONReadingMutableContainers error:&error];
    
    [self parseJson:arrayOfStrings];
    
}

-(void)parseJson:(NSArray *)data
{
    // - Loop through the array of places returned from the Google API.
    [categoryResponseArray removeAllObjects];
    
    for (int i=0; i<[data count]; i++) {
        NSDictionary* key = [data objectAtIndex:i];
        
        NSLog(@"Key f %@",[key objectForKey:@"f"]);
        NSLog(@"Key v %@",[key objectForKey:@"v"]);
        
        NSString *f=[key objectForKey:@"f"];
        NSString *v=[key objectForKey:@"v"];
        
        categoriesModel *aModel=[[categoriesModel alloc] initWithData:f vName:v];
        
        [categoryResponseArray addObject:aModel];
        
    }
    [ratingTableView reloadData];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 1;
    
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    
    return [categoryResponseArray count];
}

-(void) tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view
       forSection:(NSInteger)section
{
    if ([view isKindOfClass: [UITableViewHeaderFooterView class]])
    {
        UITableViewHeaderFooterView *headerView = (UITableViewHeaderFooterView *)view;
        headerView.contentView.backgroundColor = [UIColor clearColor];
        headerView.backgroundView.backgroundColor = [UIColor clearColor];
        
        [headerView.textLabel setTextColor:[UIColor whiteColor]];
    }
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    [[UILabel appearanceWhenContainedIn:[UITableViewHeaderFooterView class], nil] setTextAlignment:NSTextAlignmentCenter];
    
    categoriesModel *aModel = [categoryResponseArray objectAtIndex:section];
    
    NSString * strTitle = aModel.vName;
    
    return strTitle;
}

- (UITableViewCell *)tableView:(UITableView *)tableViewPlaces cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"ratingTableViewCell";
    
    UITableViewCell *cell = [tableViewPlaces dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        
        cell = [[SizableImageCellNew alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
        
        [cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
    }
    
    // Configure the cell...
    
    cell.backgroundColor = [UIColor clearColor];
    return cell;
}

-(IBAction)btnSubmitClicked:(id)sender
{
    NSLog(@"btnSubmitClicked");
    
    [self postData];
}


-(void)postData
{
    /*
     category : Restaurant
     feedback :[{"f":"1","v":"4"},{"f":"2","v":"4"},{"f":"3","v":"5"},{"f":"4","v":"2"}]
     devicemetrics : {"pf":"pf-1","osv":"osv-1","did":"did-1","email":"email-1","rtc":"rtc-1","mnu":"mnu-1"}
     
     NSArray *feedback = @[@{@"f":@"1",@"v":@"4"},@{@"f":@"2",@"v":@"4"},@{@"f":@"3",@"v":@"5"},@{@"f":@"4",@"v":@"2"}];
     
     */
    
    NSArray * arrFeedback = [NSArray array];
    
    for(int i=0;i < [categoryResponseArray count];i++)
    {
        NSIndexPath *ip = [NSIndexPath indexPathForRow:0 inSection:i];
        ratingTableViewCell *q1 = (ratingTableViewCell *)[ratingTableView cellForRowAtIndexPath:ip];
        int ratingValue = (int)q1.ratingSlider.value;
        
        NSArray *arrTemp = @[@{@"f":@(i+1).description,@"v":@(ratingValue).description}];
        arrFeedback = [arrFeedback arrayByAddingObjectsFromArray: arrTemp];
    }
    
    NSData *json = [NSJSONSerialization dataWithJSONObject:arrFeedback options:kNilOptions error:nil];
    NSString *feedbackjson = [[NSString alloc] initWithData:json encoding:NSUTF8StringEncoding];
    
    NSString *osVersion = [[UIDevice currentDevice] systemVersion];
    
    NSDictionary *devicemetrics = @{@"pf":@"iOS",@"osv":osVersion,@"did":@"did-1",@"email":@"email-1",@"rtc":@"rtc-1",@"mnu":@"mnu-1"};
    
    NSData *devicejson = [NSJSONSerialization dataWithJSONObject:devicemetrics options:kNilOptions error:nil];
    NSString *devicejsonstring = [[NSString alloc] initWithData:devicejson encoding:NSUTF8StringEncoding];
    
    NSString *requestStr = [NSString stringWithFormat:@"category=Restaurant&feedback=%@&devicemetrics=%@",feedbackjson,devicejsonstring];
    
    NSData *jsonData = [requestStr dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES];
    
    NSString *postLength=[NSString stringWithFormat:@"%lu",(unsigned long)[jsonData length]];
    NSMutableURLRequest *request= [[NSMutableURLRequest alloc]init];
    
    [request setURL:[NSURL URLWithString:submitRatingsURL]];
    
    [request setHTTPMethod:@"POST"];
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    
    [request setHTTPBody:jsonData];
    
    NSLog(@"Request Headers %@", [request allHTTPHeaderFields]);
    
    NSLog(@"Request body %@", [[NSString alloc] initWithData:[request HTTPBody] encoding:NSUTF8StringEncoding]);
    
    NSURLSession *session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    [[session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        NSString *requestReply = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
        NSLog(@"requestReply: %@", requestReply);
        dispatch_async(dispatch_get_main_queue(), ^{
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
            
            displayCouponsHTMLViewController *displayCouponsHTMLVC = [storyboard instantiateViewControllerWithIdentifier:NSStringFromClass([displayCouponsHTMLViewController class])];
            displayCouponsHTMLVC.strDisplayCoupon = requestReply;
            [self.navigationController pushViewController:displayCouponsHTMLVC animated:YES];
            
        });
    }] resume];
    
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
