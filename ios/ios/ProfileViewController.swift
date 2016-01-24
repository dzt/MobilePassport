//
//  ProfileViewController.swift
//  ios
//
//  Created by Ivan Chau on 1/22/16.
//  Copyright © 2016 Ivan Chau & Peter Soboyejo. All rights reserved.
//

import UIKit

class ProfileViewController: UIViewController {
    
    @IBOutlet weak var name : UILabel! = UILabel()
    @IBOutlet weak var username : UILabel! = UILabel()
    @IBOutlet weak var email : UILabel! = UILabel()
    @IBOutlet weak var picture : UIImageView!

    var real = NSMutableArray()
    //name, username, email
    override func viewDidLoad() {
        super.viewDidLoad()
        picture.layer.masksToBounds = false
        picture.layer.cornerRadius = picture.frame.height/2
        picture.clipsToBounds = true
        self.name.text = real[0] as? String
        self.username.text = real[1] as? String
        let hash = real[2] as? String
        self.email.text = hash
        self.getProfileImage(self.md5(string: (hash?.lowercaseString)!))


        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func getProfileImage(string: String){
        let gravatarURL = "http://www.gravatar.com/avatar/" + string + "?s=480"
        let gravns = NSURL(string: gravatarURL)
        self.picture.load(gravns!)
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
