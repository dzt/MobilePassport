//
//  MainViewController.swift
//  ios
//
//  Created by Ivan Chau on 1/21/16.
//  Copyright © 2016 Ivan Chau & Peter Soboyejo. All rights reserved.
//

import UIKit

class MainViewController: UIViewController {
    @IBOutlet weak var image : UIImageView!
    @IBOutlet weak var name : UILabel!
    @IBOutlet weak var username : UILabel!
    @IBOutlet weak var email : UILabel!
    @IBOutlet weak var desc : UITextView!
    @IBOutlet weak var profile : UITabBarItem!

    var user = NSDictionary()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarItem = UITabBarItem(title: "Profile", image: UIImage(named: "blah.gif")?.imageWithRenderingMode(.AlwaysOriginal), selectedImage: UIImage(named: "145119083668802.gif"))
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.blackColor()], forState: UIControlState.Normal)
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.whiteColor()], forState: UIControlState.Selected)

        self.navigationItem.hidesBackButton = true;
        image.layer.masksToBounds = false
        image.layer.cornerRadius = image.frame.height/2
        image.clipsToBounds = true
        self.getUserData()
               // Do any additional setup after loading the view.
    }
    func getUserData(){
        let headers = [
            "cache-control": "no-cache",
        ]
        
        
        let request = NSMutableURLRequest(URL: NSURL(string: "http://localhost:3000/user/profile")!,
            cachePolicy: .UseProtocolCachePolicy,
            timeoutInterval: 10.0)
        request.HTTPMethod = "GET"
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
                        do {
                            let json = try NSJSONSerialization.JSONObjectWithData(data!, options: []) as! NSDictionary
                            self.user = json
                            self.email.text = self.user.objectForKey("email") as? String
                            self.username.text = self.user.objectForKey("username") as? String
                            self.name.text = self.user.objectForKey("name") as? String
                            self.desc.text = self.user.objectForKey("desc") as? String
                            let hash = self.user.objectForKey("email") as? String
                            self.getProfileImage(self.md5(string: (hash?.lowercaseString)!))
                            // use anyObj here
                        } catch {
                            print("json error: \(error)")
                        }
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
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func getProfileImage(string:String){
        let gravatarURL = "http://www.gravatar.com/avatar/" + string + "?s=480"
        let gravns = NSURL(string: gravatarURL)
        self.image.load(gravns!)
    }
    override func viewWillAppear(animated: Bool) {
        self.getUserData()
    }
    func md5(string string: String) -> String {
        var digest = [UInt8](count: Int(CC_MD5_DIGEST_LENGTH), repeatedValue: 0)
        if let data = string.dataUsingEncoding(NSUTF8StringEncoding) {
            CC_MD5(data.bytes, CC_LONG(data.length), &digest)
        }
        
        var digestHex = ""
        for index in 0..<Int(CC_MD5_DIGEST_LENGTH) {
            digestHex += String(format: "%02x", digest[index])
        }
        
        return digestHex
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
