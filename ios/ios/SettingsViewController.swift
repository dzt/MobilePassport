//
//  SettingsViewController.swift
//  ios
//
//  Created by Ivan Chau on 1/23/16.
//  Copyright © 2016 Ivan Chau & Peter Soboyejo. All rights reserved.
//

import UIKit

class SettingsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    @IBOutlet weak var table : UITableView!
    @IBOutlet weak var delete : UIButton!
    @IBAction func updateUsername(){
    
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        self.table.dataSource = self
        self.table.delegate = self;
        self.navigationItem.title = "Settings";
        self.tabBarItem = UITabBarItem(title: "Settings", image: UIImage(named: "rsz_ic_settings_48px-128.png")?.imageWithRenderingMode(.AlwaysOriginal), selectedImage: UIImage(named: "rsz_ic_settings_48px-128.png"))
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.blackColor()], forState: UIControlState.Normal)
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.whiteColor()], forState: UIControlState.Selected)
        // Do any additional setup after loading the view.
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4;
    }
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        if (indexPath.row == 0){
        }
        if (indexPath.row == 1){
        }
        if (indexPath.row == 2){
        }
        if (indexPath.row == 3){
        }

    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if (indexPath.row == 0){
            let cell = tableView.dequeueReusableCellWithIdentifier("UsernameCell")
            return cell!;
        }
        if (indexPath.row == 1){
            let cell = tableView.dequeueReusableCellWithIdentifier("PasswordCell")
            return cell!;
        }
        if (indexPath.row == 2){
            let cell = tableView.dequeueReusableCellWithIdentifier("EmailCell")
            return cell!;
        }
        if (indexPath.row == 3){
            let cell = tableView.dequeueReusableCellWithIdentifier("NameCell")
            return cell!;
        }
        return UITableViewCell()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
