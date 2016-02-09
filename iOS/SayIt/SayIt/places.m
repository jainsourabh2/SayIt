
/*
 
 File:- places.m
 Project:-Say it!
 Abstract:
 Created by Amit Jain
 
 */


#import "places.h"
#import "LocationManager.h"
#import "placesModel.h"
#import "ratingViewController.h"
#import "AppDelegate.h"
@interface places ()

@end

@interface SizableImageCell : UITableViewCell {}
@end
@implementation SizableImageCell
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

@implementation places


/* URL Format

@"https://maps.googleapis.com/maps/api/place/search/xml?location=34.0522222,-118.2427778&radius=500&types=bank&sensor=false&key=TheAPIKey_You_Got"
 
 
Request Example
https://maps.googleapis.com/maps/api/place/search/json?location=19.119676,73.009497&radius=1000&types=food,health&sensor=true&key=AIzaSyAoK0qYAipHiX1vzNmWSLhQuE3wjwCZtjU
*/


// Place URL IS
#define PlaceURL @"https://maps.googleapis.com/maps/api/place/search/json?"

// My API KEY IS
#define apiKey @"AIzaSyAoK0qYAipHiX1vzNmWSLhQuE3wjwCZtjU"

// My TYPES ARE
#define types @"restaurant,food,health"

#define kBgQueue dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)

- (void)viewDidLoad {
    [super viewDidLoad];

    self.title = @"Places";
    
    UIImageView *tempImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"tableBackground.png"]];
    [tempImageView setFrame:tableView.bounds];
    tableView.backgroundView = tempImageView;
    placesResponseArray=[[NSMutableArray alloc]init];

    userCurrentCoordinate = [LocationManager sharedManager].currentCoordinate;
    
    NSLog(@"Places:: Device updated it's location to (%f,%f)",userCurrentCoordinate.latitude,userCurrentCoordinate.longitude);
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(locationUpdated) name:@"kLocationChangeNotification" object:nil];
    
    // Do any additional setup after loading the view, typically from a nib.
    
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
  
}

-(void)locationUpdated {
    AppDelegate * myAppDelegate= (AppDelegate *)[UIApplication sharedApplication].delegate;
    
if(!myAppDelegate.isLocationUpdated)
{
    myAppDelegate.isLocationUpdated = TRUE;
}
        
    NSLog(@"Places:: location Updated to (%f,%f)",userCurrentCoordinate.latitude,userCurrentCoordinate.longitude);

    [self queryGooglePlaces];
}

-(void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) queryGooglePlaces {
    
    userCurrentCoordinate = [LocationManager sharedManager].currentCoordinate;
    
    NSLog(@"Places:: queryGooglePlaces (%f,%f)",userCurrentCoordinate.latitude,userCurrentCoordinate.longitude);
    
    // Build the url string to send to Google.
    NSString *urlFinal = [NSString stringWithFormat:@"%@location=%f,%f&radius=%@&types=%@&sensor=true&key=%@",PlaceURL, userCurrentCoordinate.latitude, userCurrentCoordinate.longitude, [NSString stringWithFormat:@"%i", 1000], types, apiKey];
    
     NSLog(@"Places:: queryGooglePlaces URLFinal = %@",urlFinal);
           
    //Formulate the string as a URL object.
    NSURL *googleRequestURL=[NSURL URLWithString:urlFinal];
    
    // Retrieve the results of the URL.
    dispatch_async(kBgQueue, ^{
        NSData* data = [NSData dataWithContentsOfURL: googleRequestURL];
        [self performSelectorOnMainThread:@selector(fetchedData:) withObject:data waitUntilDone:YES];
    });
}

-(void)fetchedData:(NSData *)responseData {
    //parse out the json data
    NSError* error;
    NSDictionary* json = [NSJSONSerialization
                          JSONObjectWithData:responseData
                          
                          options:kNilOptions
                          error:&error];
    
    //The results from Google will be an array obtained from the NSDictionary object with the key "results".
    NSArray* places = [json objectForKey:@"results"];
    [self parseJson:places];
}

-(void)parseJson:(NSArray *)data
{
    // - Loop through the array of places returned from the Google API.
    
    [placesResponseArray removeAllObjects];
        for (int i=0; i<[data count]; i++) {
            //Retrieve the NSDictionary object in each index of the array.
            NSDictionary* place = [data objectAtIndex:i];
            // 3 - There is a specific NSDictionary object that gives us the location info.
            NSDictionary *geo = [place objectForKey:@"geometry"];
            // Get the lat and long for the location.
            NSDictionary *loc = [geo objectForKey:@"location"];
            // 4 - Get your name and address info for adding to a pin.
            NSString *name=[place objectForKey:@"name"];
            NSString *vicinity=[place objectForKey:@"vicinity"];

            // 5 - Get your icon url,id and place id info
            NSString *icon=[place objectForKey:@"icon"];
            NSString *item_id=[place objectForKey:@"id"];
            NSString *place_id=[place objectForKey:@"place_id"];
    
            // 6 - Create a special variable to hold this coordinate info.
            // Set the lat and long.
            double latitude=[[loc objectForKey:@"lat"] doubleValue];
            double longitude=[[loc objectForKey:@"lng"] doubleValue];
            
            placesModel *aModel=[[placesModel alloc] initWithData:name vicinity:vicinity icon:icon itemid:item_id place_id:place_id latitude:latitude longitude:longitude];
            
            [placesResponseArray addObject:aModel];
            
            }
    [tableView reloadData];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return placesResponseArray.count;

}

- (UITableViewCell *)tableView:(UITableView *)tableViewPlaces cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableViewPlaces dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        
        cell = [[SizableImageCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];

        
        [cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
    }
    
    // Configure the cell...
    placesModel *aModel=[placesResponseArray objectAtIndex:indexPath.row];
    
    cell.textLabel.text = aModel.name;
    cell.detailTextLabel.text = aModel.vicinity;
   
    [cell.imageView setImage:[UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:aModel.icon]]]];
    
     cell.backgroundColor = [UIColor clearColor];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
 
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
        
    ratingViewController *ratingVC = [storyboard instantiateViewControllerWithIdentifier:NSStringFromClass([ratingViewController class])];
    
     placesModel *aModel=[placesResponseArray objectAtIndex:indexPath.row];
    ratingVC.aModel = aModel;
    [self.navigationController pushViewController:ratingVC animated:YES];
}
@end
