//
//  SearchViewController.swift
//  ios
//
//  Created by Ivan Chau on 1/22/16.
//  Copyright © 2016 Ivan Chau & Peter Soboyejo. All rights reserved.
//

import UIKit

class SearchViewController: UIViewController, UISearchBarDelegate {
    @IBOutlet weak var search : UISearchBar!
    @IBOutlet weak var find : UILabel!
    let indicator = UIActivityIndicatorView()
    var query = NSDictionary()
    var base = UILabel()
    override func viewDidLoad() {
        super.viewDidLoad()
        self.base = self.find
        self.search.delegate = self
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.blackColor()], forState: UIControlState.Normal)
        self.tabBarItem.setTitleTextAttributes([NSForegroundColorAttributeName:UIColor.whiteColor()], forState: UIControlState.Selected)

        // Do any additional setup after loading the view.
    }
    
    func searchBar(searchBar: UISearchBar, textDidChange searchText: String) {
        indicator.center = self.view.center
        indicator.startAnimating()
        indicator.color = UIColor.darkGrayColor()
        self.view.addSubview(indicator)
    }
    func searchBarTextDidBeginEditing(searchBar: UISearchBar) {
        self.find.removeFromSuperview()
    }
    func searchBarTextDidEndEditing(searchBar: UISearchBar) {
        self.indicator.removeFromSuperview()
        self.find = self.base
        self.view.addSubview(self.find)
        let headers = [
            "cache-control": "no-cache",
        ]
        let strins = "http://localhost:3000/user/search/username/" + self.search.text!
        let request = NSMutableURLRequest(URL: NSURL(string: strins)!,
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
                        //segue to main view, etc.
                        do {
                            let json = try NSJSONSerialization.JSONObjectWithData(data!, options: []) as! NSDictionary
                            self.query = json;
                            self.performSegueWithIdentifier("ProfileSearch", sender: self)
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
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        self.search.resignFirstResponder()
        self.indicator.removeFromSuperview()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if(segue.identifier == "ProfileSearch"){
            let profileController = segue.destinationViewController as! ProfileViewController
            if ((self.query.objectForKey("desc")) != nil){
                profileController.real = NSMutableArray(array: [self.query.objectForKey("name")!,self.query.objectForKey("username")!,self.query.objectForKey("email")!,self.query.objectForKey("desc")!])
            }else{
                profileController.real = NSMutableArray(array: [self.query.objectForKey("name")!,self.query.objectForKey("username")!,self.query.objectForKey("email")!,"Descriptionless"])
            }

        }
    }
    

}
