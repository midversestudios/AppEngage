//
//  ViewController.m
//  AppEngageSampleApp
//
//  Created by Ryan Bertrand on 1/6/14.
//  Copyright (c) 2014 Midverse. All rights reserved.
//

#import "ViewController.h"
#import "MVAppEngage.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

-(IBAction)playAction:(id)sender{
    [MVAppEngage userPerformedEngagementAction:@"play"];
}

-(IBAction)winAction:(id)sender{
    [MVAppEngage userPerformedEngagementAction:@"win"];
}

-(IBAction)showEngagementDialog:(id)sender{
    [MVAppEngage showEngagementDialog];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
