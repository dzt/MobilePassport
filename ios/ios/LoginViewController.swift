//
//  LoginViewController.swift
//  ios
//
//  Created by Ivan Chau on 1/19/16.
//  Copyright © 2016 Ivan Chau & Peter Soboyejo. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    @IBOutlet weak var username : UITextField!
    @IBOutlet weak var password : UITextField!
    @IBOutlet weak var login : UIButton!
    @IBOutlet weak var register : UIButton!
    
    @IBAction func login(sender:UIButton){
        if (self.username.text == "" || self.password.text == ""){
            let alertView = UIAlertController(title: "UWOTM8", message: "Fam, what you tryna pull?", preferredStyle: .Alert)
            let OK = UIAlertAction(title: "Is it 2 l8 2 say sorry", style: .Default, handler: nil)
            alertView.addAction(OK)
            self.presentViewController(alertView, animated: true, completion: nil);
            return;
        }
        username.resignFirstResponder()
        password.resignFirstResponder()
        
        let headers = [
            "cache-control": "no-cache",
            "content-type": "application/x-www-form-urlencoded"
        ]
        
        let usernameString = "username=" + self.username.text!
        let passwordString = "&password=" + self.password.text!
        let postData = NSMutableData(data: usernameString.dataUsingEncoding(NSUTF8StringEncoding)!)
        postData.appendData(passwordString.dataUsingEncoding(NSUTF8StringEncoding)!)
        
        let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/login")!,
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
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
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
