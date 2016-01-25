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
    @IBOutlet weak var logout : UIButton!
    
    @IBAction func logoutAccount(sender:UIButton){
        let alert = UIAlertController(title: "Do you want to logout?", message: "Are you sure you want to logout?", preferredStyle: .Alert)
        alert.addAction(UIAlertAction(title:"No", style: UIAlertActionStyle.Cancel, handler: nil))
        let log = UIAlertAction(title: "Logout", style: UIAlertActionStyle.Destructive) { (_) -> Void in
            let headers = [
                "cache-control": "no-cache",
            ]
            
            
            let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/logout")!,
                cachePolicy: .UseProtocolCachePolicy,
                timeoutInterval: 10.0)
            request.HTTPMethod = "POST"
            request.allHTTPHeaderFields = headers
            
            let session = NSURLSession.sharedSession()
            let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                if (error != nil) {
                    print(error)
                } else {
                    let httpResponse = response as? NSHTTPURLResponse
                    print(httpResponse)
                    
                    if (httpResponse?.statusCode == 200){
                        dispatch_async(dispatch_get_main_queue(), {
                            //segue to main view.
                            self.keychain.setPasscode("MPPassword", passcode: "")
                            self.keychain.setPasscode("MPUsername", passcode: "")
                            self.performSegueWithIdentifier("LogoutSegue", sender: self)
                            
                        })
                    }else{
                        print("error")
                    }
                    // use anyObj here
                    print("json error: \(error)")
                }
            })
            
            dataTask.resume()
        }
        alert.addAction(log)
        self.presentViewController(alert, animated: true, completion: nil)

    }
    @IBAction func deleteAccount(sender:UIButton){
        let alert = UIAlertController(title: "Are you sure?", message: "Do you want to delete your account permanently?", preferredStyle: .Alert)
        alert.addAction(UIAlertAction(title:"Let me Rethink.", style: UIAlertActionStyle.Cancel, handler: nil))
        let over = UIAlertAction(title: "It's Over", style: UIAlertActionStyle.Destructive) { (_) -> Void in
            let headers = [
                "cache-control": "no-cache",
            ]
            
            
            let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/delete")!,
                cachePolicy: .UseProtocolCachePolicy,
                timeoutInterval: 10.0)
            request.HTTPMethod = "DELETE"
            request.allHTTPHeaderFields = headers
            
            let session = NSURLSession.sharedSession()
            let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                if (error != nil) {
                    print(error)
                } else {
                    let httpResponse = response as? NSHTTPURLResponse
                    print(httpResponse)
                    
                    if (httpResponse?.statusCode == 200){
                        dispatch_async(dispatch_get_main_queue(), {
                            //segue to main view.
                            self.keychain.setPasscode("MPPassword", passcode: "")
                            self.keychain.setPasscode("MPUsername", passcode: "")
                            self.performSegueWithIdentifier("LogoutSegue", sender: self)
                            
                        })
                    }else{
                        print("error")
                    }
                    // use anyObj here
                    print("json error: \(error)")
                }
            })
            
            dataTask.resume()
        }
        alert.addAction(over)
        self.presentViewController(alert, animated: true, completion: nil)
    }
    let keychain = Keychain()
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
            let alertView = UIAlertController(title: "Update Username", message: "Enter your new username!", preferredStyle: .Alert)
            alertView.addTextFieldWithConfigurationHandler({ (textField) -> Void in
                textField.placeholder = "Username"
            })
            alertView.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil))
            let updateUsername = UIAlertAction(title: "Update", style: .Default) { (_) in
                let usernameTextField = alertView.textFields![0] as UITextField
                print (usernameTextField.text)
                let headers = [
                    "cache-control": "no-cache",
                    "content-type": "application/x-www-form-urlencoded"
                ]
                
                let usernameStr = "username=" + usernameTextField.text!
                let postData = NSMutableData(data: usernameStr.dataUsingEncoding(NSUTF8StringEncoding)!)
                
                let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/update")!,
                    cachePolicy: .UseProtocolCachePolicy,
                    timeoutInterval: 10.0)
                request.HTTPMethod = "POST"
                request.allHTTPHeaderFields = headers
                request.HTTPBody = postData
                
                let session = NSURLSession.sharedSession()
                let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                    if (error != nil) {
                        print(error)
                    } else {
                        let httpResponse = response as? NSHTTPURLResponse
                        print(httpResponse)
                        
                        if (httpResponse?.statusCode == 200){
                            dispatch_async(dispatch_get_main_queue(), {
                                //segue to main view.
                                alertView.textFields![0].resignFirstResponder()
                                alertView.textFields![1].resignFirstResponder()

                                self.keychain.setPasscode("MPUsername", passcode: usernameTextField.text!)
                            
                            })
                        }else{
                            print("error")
                        }
                        // use anyObj here
                        print("json error: \(error)")
                    }
                })
                
                dataTask.resume()

            }
            alertView.addAction(updateUsername)
            self.presentViewController(alertView, animated: true, completion: nil)

        }
        if (indexPath.row == 1){
            let alertView = UIAlertController(title: "Update Password", message: "Enter your old and new password!", preferredStyle: .Alert)
            alertView.addTextFieldWithConfigurationHandler({(textField) -> Void in
                textField.placeholder = "Old Password"
                textField.secureTextEntry = true;
            })
            alertView.addTextFieldWithConfigurationHandler({ (textField) -> Void in
                textField.placeholder = "New Password"
                textField.secureTextEntry = true;
            })
            let updateUsername = UIAlertAction(title: "Update", style: .Default) { (_) in
                let oldText = alertView.textFields![0] as UITextField
                if(oldText.text == self.keychain.getPasscode("MPPassword")){
                let passwordTextField = alertView.textFields![1] as UITextField
                print (passwordTextField.text)
                let headers = [
                    "cache-control": "no-cache",
                    "content-type": "application/x-www-form-urlencoded"
                ]
                
                let passwordStr = "password=" + passwordTextField.text!
                let postData = NSMutableData(data: passwordStr.dataUsingEncoding(NSUTF8StringEncoding)!)
                
                let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/update")!,
                    cachePolicy: .UseProtocolCachePolicy,
                    timeoutInterval: 10.0)
                request.HTTPMethod = "POST"
                request.allHTTPHeaderFields = headers
                request.HTTPBody = postData
                
                let session = NSURLSession.sharedSession()
                let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                    if (error != nil) {
                        print(error)
                    } else {
                        let httpResponse = response as? NSHTTPURLResponse
                        print(httpResponse)
                        
                        if (httpResponse?.statusCode == 200){
                            dispatch_async(dispatch_get_main_queue(), {
                                //segue to main view.
                                alertView.textFields![0].resignFirstResponder()
                                self.keychain.setPasscode("MPPassword", passcode: passwordTextField.text!)
                            })
                        }else{
                            print("error")
                        }
                        // use anyObj here
                        print("json error: \(error)")
                    }
                })
                
                dataTask.resume()
                }else{
                    self.updateFailure()
                }

            }
            alertView.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil))
            alertView.addAction(updateUsername)
            self.presentViewController(alertView, animated: true, completion: nil)

        }
        if (indexPath.row == 2){
            let alertView = UIAlertController(title: "Update Email", message: "Enter your email!", preferredStyle: .Alert)
            alertView.addTextFieldWithConfigurationHandler({ (textField) -> Void in
                textField.placeholder = "Email"
            })
            let updateUsername = UIAlertAction(title: "Update", style: .Default) { (_) in
                let emailTextField = alertView.textFields![0] as UITextField
                print (emailTextField.text)
                let headers = [
                    "cache-control": "no-cache",
                    "content-type": "application/x-www-form-urlencoded"
                ]
                
                let emailStr = "email=" + emailTextField.text!
                let postData = NSMutableData(data: emailStr.dataUsingEncoding(NSUTF8StringEncoding)!)
                
                let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/update")!,
                    cachePolicy: .UseProtocolCachePolicy,
                    timeoutInterval: 10.0)
                request.HTTPMethod = "POST"
                request.allHTTPHeaderFields = headers
                request.HTTPBody = postData
                
                let session = NSURLSession.sharedSession()
                let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                    if (error != nil) {
                        print(error)
                    } else {
                        let httpResponse = response as? NSHTTPURLResponse
                        print(httpResponse)
                        
                        if (httpResponse?.statusCode == 200){
                            dispatch_async(dispatch_get_main_queue(), {
                                //segue to main view.
                                alertView.textFields![0].resignFirstResponder()
                            })
                        }else{
                            print("error")
                        }
                        // use anyObj here
                        print("json error: \(error)")
                    }
                })
                
                dataTask.resume()
                

            }
            alertView.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil))
            alertView.addAction(updateUsername)
            self.presentViewController(alertView, animated: true, completion: nil)

        }
        if (indexPath.row == 3){
            let alertView = UIAlertController(title: "Update Name", message: "Enter your name!", preferredStyle: .Alert)
            alertView.addTextFieldWithConfigurationHandler({ (textField) -> Void in
                textField.placeholder = "Name"
            })
            let updateUsername = UIAlertAction(title: "Update", style: .Default) { (_) in
                let nameTextField = alertView.textFields![0] as UITextField
                print (nameTextField.text)
                let headers = [
                    "cache-control": "no-cache",
                    "content-type": "application/x-www-form-urlencoded"
                ]
                
                let nameStr = "name=" + nameTextField.text!
                let postData = NSMutableData(data: nameStr.dataUsingEncoding(NSUTF8StringEncoding)!)
                
                let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/update")!,
                    cachePolicy: .UseProtocolCachePolicy,
                    timeoutInterval: 10.0)
                request.HTTPMethod = "POST"
                request.allHTTPHeaderFields = headers
                request.HTTPBody = postData
                
                let session = NSURLSession.sharedSession()
                let dataTask = session.dataTaskWithRequest(request, completionHandler: { (data, response, error) -> Void in
                    if (error != nil) {
                        print(error)
                    } else {
                        let httpResponse = response as? NSHTTPURLResponse
                        print(httpResponse)
                        
                        if (httpResponse?.statusCode == 200){
                            dispatch_async(dispatch_get_main_queue(), {
                                //segue to main view.
                                alertView.textFields![0].resignFirstResponder()
                            })
                        }else{
                            print("error")
                        }
                        // use anyObj here
                        print("json error: \(error)")
                    }
                })
                
                dataTask.resume()
                

            }
            alertView.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil))
            alertView.addAction(updateUsername)
            self.presentViewController(alertView, animated: true, completion: nil)

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
    func updateFailure(){
        let o = UIAlertController(title: "Can't Update Password", message: "The Old Password was entered incorrectly so we can not verify your new Password", preferredStyle: .Alert)
        let damn = UIAlertAction(title: "OK", style: .Default, handler: nil)
        o.addAction(damn)
        self.presentViewController(o, animated: true, completion: nil)
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
