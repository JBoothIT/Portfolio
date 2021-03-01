# -*- coding: utf-8 -*-
"""
Created on Sun Jul 14 19:29:28 2019

@author: Robert Pressler and John Bedingfield
This code is a controller to manage the VPN instances
It takes user input and stands up AWS EC2 instances appropriately.
It manages keypair import, setting up security groups, configuring the instances for
   multiple hops, in a user selectable location


This code assumes:
    1. Your AWS account public and private keys are in default location at ~/.aws/credentials:
    in a file called 'credentials/txt', and in the following format:
            [default]
            aws_access_key_id = KEYHERE...
            aws_secret_access_key = KEYHERE...
    
    
    2.  You have created AWS keys per our first lab exercise.
    Place both your server keypair files in the same folder with path defined in KEY_PATH_PUBLIC
    There should be two files, the public key to import into AWS (xxx.pub), and public/private keypair
    you will use to SSH (or puTTY) into the instance (xxx.pem)
    Place the name of your keypair in the static variable 'KEY_NAME'.
    If you need to generate a new key pair, the code is: (untested)
            outfile = open('TestKey.pem','w')
            key_pair = ec2.create_key_pair(KeyName='TestKey')
            KeyPairOut = str(key_pair.key_material)
            outfile.write(KeyPairOut)
            
    3.  Assumes you have OpenVPN GUI installed, and you've placed your config files
    in C:\Program Files\OpenVPN\config\ and that you have an environmental variable set
    to the executable openvpn-gui.exe
    you can download OpenVPN GUI here: https://openvpn.net/community-downloads/
    
    4.  (for Robert's code) Assumes you have puTTY and puTTY Secure Cooy (pscp) installed
    They can be found here: https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html
    
    5.  (for John's code) assumes you have paramiko.  If not, just pip install paramiko
    
    
    
    
TO DO:
    a. [done] configure instance security groups
    b. [done] provide any other configuration needed (for example, run through the initial OpenVPN start-up and configuration menus)
    c. collect metrics on the instance (for billing purposes)
    d. [done] provide functions to stop, start, and terminate the instance [need some more automation]
    e. [done] provide capability to start a snapshot versus instance (this would allow us to preconfigure the instance)
    e2. [done] We'll need to decide how we want to snapshot the instances (snapshot, AMI, template), and what configuration is
        required after they are instantiated
    f. [done, just uses existing keys for now] generate new keys, add to the region, provide to the user
    g. Determine if we will centrally manage the instances.  If so, how will that be communicated to the clients
    h. [done] Do we need AMI IDs for each region? are there general ones we can use, or can we push our own
    i. Need to push clone image into other Regions
    j. Need to add Robert's termination software
    
    
This version includes:
    (a) standing up a server based on a snapshot ImageId (AMI ID), add security group, code to start, stop, terminate
    (b) moved code into def functions to ease use, maintenance, and expandability
    (c) capability to stand-up and link multiple servers
    (d) created clone AMIs for each region
    (e) created a class so the client Visual Basic code can easily call it


AWS EC2 availability zones: https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-regions-availability-zones.html
    us-east-1 : US East (N. Virginia)
    us-east-2 : US East (Ohio)
    us-west-1 : US West (N. California)
    us-west-2 : US West (Oregon)
    ca-central-1 : Canada (Central)
    eu-central-1 : EU (Frankfurt)
    eu-west-1 : EU (Ireland)
    eu-west-2 : EU (London)
    eu-west-3 : EU (Paris)
    eu-north-1 : EU (Stockholm)
    ap-east-1 : Asia Pacific (Hong Kong)
    ap-northeast-1 : Asia Pacific (Tokyo)
    ap-northeast-2 : Asia Pacific (Seoul)
    ap-northeast-3 : Asia Pacific (Osaka-Local)
    ap-southeast-1 : Asia Pacific (Singapore)
    ap-southeast-2 : Asia Pacific (Sydney)
    ap-south-1 : Asia Pacific (Mumbai)
    sa-east-1 : South America (São Paulo)
    
    
"""

import boto3
import logging
from botocore.exceptions import ClientError
import time
import os
import time
# for the shell command via Boto approach.  Need pramiko installed: pip install paramiko
import boto.ec2
from boto.manage.cmdshell import sshclient_from_instance
import paramiko
import time
import platform # to determine the client os


class MyAlphaVPNClass:
    
    def __init__(self, location, NumHops):

        # initialize variables        
        self.KEY_NAME = 'CS403AlphaVPNKey'
        self.secGrpName = 'CS403AlphaVPN'
        
        # this is a list of supported regions used to convert from the client code,
        # sent from the client as a plain english place (e.g. Virginia), to a Region ID
        # (e.g. us-east-1)
        self.listOSupportedRegions = {
            'Virginia' : 'us-east-1',
            'Ohio' : 'us-east-2',
            'California': 'us-west-1',
            'Oregon' : 'us-west-2',
            #ca-central-1 : Canada (Central),
            #eu-central-1 : EU (Frankfurt),
            #eu-west-1 : EU (Ireland),
            #eu-west-2 : EU (London),
            #eu-west-3 : EU (Paris),
            'Stockholm' : 'eu-north-1',
            #ap-east-1 : Asia Pacific (Hong Kong),
            'Tokyo' : 'ap-northeast-1',
            #ap-northeast-2 : Asia Pacific (Seoul),
            #ap-northeast-3 : Asia Pacific (Osaka-Local),
            #ap-southeast-1 : Asia Pacific (Singapore),
            #ap-southeast-2 : Asia Pacific (Sydney),
            #ap-south-1 : Asia Pacific (Mumbai),
            #sa-east-1 : South America (São Paulo),
                }
        
        # these are a list of the AMIs in each region matching our pre-built server
        self.listOfCloneRegionAMIs = {
            'us-east-1':'ami-09b7d9dc1d9970a17',  #US East (N. Virginia)
            'us-east-2':'ami-03b0133f3a2c80ca0',  #US East (Ohio)
            'us-west-1' : 'ami-0fe0063588f396d8b',  #US West (N. California)
            'us-west-2' : 'ami-0a4efc762fdaf420c',  #US West (Oregon)
            #'ca-central-1' : 'ami',  #Canada (Central)
            #'eu-central-1' : 'ami',  #EU (Frankfurt)
            #'eu-west-1' : 'ami',  #EU (Ireland)
            #'eu-west-2' : 'ami',  #EU (London)
            #'eu-west-3' : 'ami',  #EU (Paris)
            'eu-north-1' : 'ami-0f65f772ed1f4fbd4',  #EU (Stockholm)
            #'ap-east-1' : 'ami',  #Asia Pacific (Hong Kong)
            'ap-northeast-1' : 'ami-0889ee8d3f9fa35bd',  #Asia Pacific (Tokyo)
            #'ap-northeast-2' : 'ami',  #Asia Pacific (Seoul)
            #'ap-northeast-3' : 'ami',  #Asia Pacific (Osaka-Local)
            #'ap-southeast-1' : 'ami',  #Asia Pacific (Singapore)
            #'ap-southeast-2' : 'ami',  #Asia Pacific (Sydney)
            #'ap-south-1' : 'ami',  #Asia Pacific (Mumbai)
            #'sa-east-1' : 'ami',  #South America (São Paulo)
        }
        
        #self.locationLastHop = self.getLocationID(location)
        self.locationLastHop = self.listOSupportedRegions.get(location, "invalid selection")
        self.myNumHops = int(NumHops)
        self.imageIDConditional = self.getCloneAMI(self.locationLastHop)
        
        # start the timer to collect timing data
        start_time = time.time()
        
        # get a resource and client appropriate for the Region
        self.ec2NR = boto3.resource(
                            'ec2',
                            region_name = self.locationLastHop
                            )
        self.ec2cNR = boto3.client(
                        'ec2',
                        region_name = self.locationLastHop
                        )
        
        # complete pre-reqs -- import keys
        self.importKeys(self.ec2cNR)
        self.printStatusMessage('Keys are good', start_time)
        
        # complete pre-reqs -- configure security groups
        self.securityGrpID = self.setSecurityGroup(self.ec2cNR, self.secGrpName)
        self.printStatusMessage('Security Groups are good', start_time)
        
        # put initial files on client
        self.writeFilesToClient()
        
        # first, create an OpenVPN instance, then configure it
        print('===== setting up OpenVPN server ===== \n===== this will take a few seconds =====')
        self.myInstanceID = (self.CreateInstance(self.ec2NR, self.imageIDConditional, self.KEY_NAME, self.secGrpName))
        self.myOVPNInstance = self.ec2NR.Instance(self.myInstanceID)
        self.myOVPNInstance.wait_until_running()
        self.myOVPNIP = self.myOVPNInstance.public_ip_address
        self.printStatusMessage('VPN instance is up', start_time)
        print(self.myOVPNIP)
        self.configureVPNServerViaOS(self.myOVPNIP)
        self.printStatusMessage('VPN server configured', start_time)
        
        if  self.myNumHops == 1:
            self.writeOPVNViaOS(self.myOVPNIP)
            
        # if two hops, also create a port forwarding VM, forwarding to the OpenVPN VM
        if (self.myNumHops == 2) or (self.myNumHops == 3):
            self.myInstanceID2 = (self.CreateInstance(self.ec2NR, self.imageIDConditional, self.KEY_NAME, self.secGrpName))
            self.myHopInstance = self.ec2NR.Instance(self.myInstanceID2)
            self.myHopInstance.wait_until_running()
            self.myHopIP = self.myHopInstance.public_ip_address
            self.printStatusMessage('Hop1 server is up', start_time)
            print('=======\nHop Server :',self.myInstanceID2, self.myHopIP)
            self.configureHopServerViaOS(self.myOVPNIP, self.myHopIP)
            self.printStatusMessage('Hop1 server is configured', start_time)
            
            if self.myNumHops == 2:
                self.writeOPVNViaOS(self.myHopIP)
                return
            
            if (self.myNumHops == 3):
                self.myInstanceID3 = (self.CreateInstance(self.ec2NR, self.imageIDConditional, self.KEY_NAME, self.secGrpName))
                self.myHopInstance3 = self.ec2NR.Instance(self.myInstanceID3)
                self.myHopInstance3.wait_until_running()
                self.myHopIP3 = self.myHopInstance3.public_ip_address
                self.printStatusMessage('Hop2 server is up', start_time)
                print('=======\nHop Server #2:',self.myInstanceID3, self.myHopIP3)
                self.configureHopServerViaOS(self.myHopIP, self.myHopIP3)
                self.printStatusMessage('Hop2 server is configured', start_time)
                
                self.writeOPVNViaOS(self.myHopIP3)
        
        # start it
        os.system('openvpn-gui.exe --connect "client101.ovpn"')
        return
        
    def printStatusMessage(self, message, start_time):
        print(message + " --- %s seconds ---" % (time.time() - start_time))
        
        
    def getLocationID(self, argument):
        # takes a plain name location (string)
        # returns an Region ID (string) appropriate for that Region
        # if the argument does not match one of the Region IDs in dictionary listOfRegionAMIs,
        # returns 'invalid selection' (string)
        self.locArgument = argument
        return self.listOSupportedRegions.get(self.locArgument, "invalid selection")
    
    def getCloneAMI(self, argument):
        # takes a Region ID (string)
        # returns an AMI (string) appropriate for that Region
        # if the argument does not match one of the Region IDs in dictionary listOfRegionAMIs,
        # returns 'invalid selection' (string)
        self.cloneArgument = argument
        return self.listOfCloneRegionAMIs.get(self.cloneArgument, "invalid selection")
    
    def CreateInstance(self, ec2Resource, imageID, key_name, securityGroupID):
        instance = ec2Resource.create_instances(
                ImageId=imageID,
                MinCount=1,
                MaxCount=1,
                KeyName=key_name,
                InstanceType='t2.micro',
                SecurityGroups=[securityGroupID]
                )
        
        #TO-DO: (perhaps?) store this info in a database, along with getInstanceDetails
        # so we can look it up and retrieve it later
        return instance[0].id
    
    def terminateInstance(self, ec2Client, instanceID):
        try:
            response = ec2Client.terminate_instances(InstanceIds=[instanceID], DryRun=False)
            print(response)
        except ClientError as e:
            print(e)
        return
    
    # Robert's code to set up a Security Group, with a little error checking added
    def setSecurityGroup(self, ec2Client, securityGroupName):
        secGrpExists = False # used to determine if the security group exists
        vpcResponse = ec2Client.describe_vpcs()
        vpc_id = vpcResponse.get('Vpcs', [{}])[0].get('VpcId', '')
    
        # check to see if security group already exists
        for security_group_resp in ec2Client.describe_security_groups()['SecurityGroups']:
            if security_group_resp['GroupName'] == securityGroupName:
                 print('already exists, ID is: '+str(security_group_resp['GroupId']))
                 secGrpExists = True
                 return security_group_resp['GroupId']
             
        if secGrpExists == False: # the security group does not already exist
            securityGroupResponse = ec2Client.create_security_group(GroupName=securityGroupName,
                                             Description='for CS403 Group Alpha VPNs',
                                             VpcId=vpc_id)
            security_group_id = securityGroupResponse['GroupId']
        
            data = ec2Client.authorize_security_group_ingress(
                GroupId=security_group_id,
                IpPermissions=[
                    {'IpProtocol': 'tcp',
                     'FromPort': 22,
                     'ToPort': 22,
                     'IpRanges': [{'CidrIp': '0.0.0.0/0'}]},
                    {'IpProtocol': 'udp',
                     'FromPort': 1194,
                     'ToPort': 1194,
                     'IpRanges': [{'CidrIp': '0.0.0.0/0'}]}
                ])
            
            return security_group_id
    
    def importKeys(self, ec2Client):
        # import the key into the region
        
        # use this code to read a key in from a file and import it into the region
        # key file should be at path defined in KEY_PATH_PUBLIC
        # key file name in static variable KEY_NAME_PUBLIC_IMPORT
        '''
        # instantiate variables
        wholeKey = ''    
        keyFileAt = KEY_PATH_PUBLIC + '\\' + KEY_NAME_PUBLIC_IMPORT
    
        # open the file and read in the public key
        with open(keyFileAt, "r") as f:
        
            for line_of_text in f:
                wholeKey = wholeKey + line_of_text
        b = wholeKey.encode('utf-8')
        '''
        
        PublicKeyMaterial="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC0Gd3ivAtPwyeZh/ry1b36ADYbQxkuki4lOb4L5SIOF6wYMt24rDcs2r2VJ50H96SBpzvW5l86BUbBkXbtU3A9mWaoEu9AUVD3fPUs8G0rURQFDtjGEMw/UgAG2uKMKRq8sRWemHj9p6hfH92TEkcQwFeA5TGrW4DL8fqoatKdKNzAQx9jXhemYFan2vl37AK5N8mC406BK/kcDV9QiGsRbGDxgMRkKr6ud/eZIYQ54csPopqatlKLL7MH4zZwgtNXdVeJOStvNWsdZ6bhSa4j5M5PzCt+oGOQ+vzeFpL0ht8rgkwSMZW/hUmODQSyJ78dcyDb+f5ii/w2B5rLWcLX virgkey"
    
        # check to see if the keypair already exists in this region
        if self.KEY_NAME in str(ec2Client.describe_key_pairs()):
            print('===== keypair already exists =====')
            
        else:
            response = ec2Client.import_key_pair(
                    DryRun=False,
                    KeyName=self.KEY_NAME,
                    PublicKeyMaterial=PublicKeyMaterial
            )
         
            print(str(response) +'\n===== keypair imported =====')
        return
            
            
    def configureVPNServerViaOS(self, myOVPNIP):
        # configure the VPN server (from Robert's code)
        
        shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+str(myOVPNIP)+' "sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
        os.system(shellSTR)
        shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+str(myOVPNIP)+' "cd /etc/openvpn;sudo systemctl start openvpn@server.service"'
        os.system(shellSTR)
        
        return
    
    def configureHopServerViaOS(self, myDestinationIP, myHopIP):
        # In a two hop configuration, the destination IP is myOVPNIP (the IP of the VPN Server)
        #   and my HopIP is the IP of the hop server
        # so this forwards traffic from myHopIP to myDestinationIP
        # configure port fowarding (Robert's code)
        shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+str(myHopIP) + ' "sudo iptables -t nat -A PREROUTING -p udp --dport 1194 -j DNAT --to-destination '+str(myDestinationIP)+':1194; sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
        os.system(shellSTR)
        return
    
    def writeFilesToClient(self):
        dirName = 'C:/tempOVPN'
        if not os.path.exists(dirName):
            os.mkdir(dirName)
            print("Directory " , dirName ,  " Created ")
            
        filepath = "C:/tempOVPN/virgkey.pem"
        fout = open(filepath, "w+")
        fout.write("-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEAtBnd4rwLT8MnmYf68tW9+gA2G0MZLpIuJTm+C+UiDhesGDLduKw3LNq9lSed\nB/ekgac71uZfOgVGwZF27VNwPZlmqBLvQFFQ93z1LPBtK1EUBQ7YxhDMP1IABtrijCkavLEVnph4\n/aeoXx/dkxJHEMBXgOUxq1uAy/H6qGrSnSjcwEMfY14XpmBWp9r5d+wCuTfJguNOgSv5HA1fUIhr\nEWxg8YDEZCq+rnf3mSGEOeHLD6KamrZSiy+zB+M2cILTV3VXiTkrbzVrHWem4UmuI+TOT8wrfqBj\nkPr83haS9IbfK4JMEjGVv4VJjg0Esie/HXMg2/n+Yov8Ngeay1nC1wIDAQABAoIBAH3xHLXCM9LK\nMLXvXjBUAa6CWVPFHyXu+S/06g4dZCwgp6qgX2YVn9vQ9wQHmIsKxNIZpQHJRUwohms0EssxLusU\ni7H6063Rb2Ix0q1jT6Qb5XkIAdS3yGx7JiOlCwlicR0EZI7sJqeSh25FtUd0X5e6OTacj5g5u55i\nj5sYYEGfNKLJGgIv+yU8rwfMGCSuslRGnFh9rgAZ5gy+f4G3BsKAScDu8vqZs1vhKl6QIKPjkqRs\nvSH9fqqOh9qSIvmpPsnUi2UMhsakA4mMNOgB4uoCIXRQG2O07j/z5lxTnhnjvhOtMfF9wH5FYqrG\nudkn7Gn5MCB+3RChSZ8WhwbGLHECgYEA7fETf4Z2e4W7eBv5ZztibTv2QhANtIqbkHZPyf4iUexW\n7wHiGz0O9dZfYY8W5kEcOH9oREWsX/4x3yRKO0ScO79hhm5XHSkxrRObocRtt6dpVak7A/w3jDqR\nQPNYiFguZ1rdp0/uPAtz+TmNRU6RqlNfzM1UmtKOrvgB6ywoP/8CgYEAwcUD8uWdA0bs+5z5Omul\nGbWrTUJCL0Fd1olwXHu5/c9IZiMeXMXGSpNI9CdDuh6Tlg9rJ9YClklqsjoz5Oa736ceXKUnPqWs\n/bREDI1UHLehzLp3e5oR6Y9HCaq/3C7LTgpq3UNHVVLn3buUnnL6FubEb9xEINRdkbO09O5YfSkC\ngYB5jsSgTMhw+HQ6u0fh9lOlbJG1VFA8IOeymM7QadWMtsn+1p51ve7LpxYZWCmhvje1oVlaL6IN\ndGZ0Ei0eNEIAwBgJvosgqa/oNPE4ZoqK3asRiSBOO+cA69bTML7VAP/L6377f/k8kblQ5JcIhkgi\nlO+HSIrlgZZFSwxn4ao6bwKBgBjUdDMV9U7MMeX0MYOo85kdzHuz9+MyTyIErp9LQ4Qgobhk26kr\ntsMrqHeQ6H7bGDJse2C5bc9wTPnVt3ynjH+WXK+mgibm+AnypZ2uJo8fgN5JLrJqBc2WDJZSiQ9f\n97FiaFs+Gc/3NbsWJG9BcIaqv5VN9nT2gZ8AFjokqLYxAoGBAL260Thql/uIsdaGlDnVhnaUiigh\nxKlU7d6CiB/IAFQ6evHCyXluQN7zRaXuGjp3CIV3k7ag1IXX3LBq235fK7/7DbYfQLmoEPF7Bk8H\nbdB/6Ivo488I1VCh8DDuSrcfmLUyeRvSU2fH12URpCRb1E9YlkOFN5kY164SvB71hHNN\n-----END RSA PRIVATE KEY-----")
        fout.close()
        
        #do we need this?
        shellSTR='set key="C:\\tempOVPN\\virgkey.pem" && cmd /c icacls %key% /c /t /inheritance:d && cmd /c icacls %key% /c /t /grant %username%:F && cmd /c icacls %key%  /c /t /remove Administrator "Authenticated Users" BUILTIN\Administrators BUILTIN Everyone System Users'
        os.system(shellSTR)

        filepath = "C:/tempOVPN/virgkey.ppk"
        fout = open(filepath, "w+")
        fout.write("PuTTY-User-Key-File-2: ssh-rsa\nEncryption: none\nComment: imported-openssh-key\nPublic-Lines: 6\nAAAAB3NzaC1yc2EAAAADAQABAAABAQC0Gd3ivAtPwyeZh/ry1b36ADYbQxkuki4l\nOb4L5SIOF6wYMt24rDcs2r2VJ50H96SBpzvW5l86BUbBkXbtU3A9mWaoEu9AUVD3\nfPUs8G0rURQFDtjGEMw/UgAG2uKMKRq8sRWemHj9p6hfH92TEkcQwFeA5TGrW4DL\n8fqoatKdKNzAQx9jXhemYFan2vl37AK5N8mC406BK/kcDV9QiGsRbGDxgMRkKr6u\nd/eZIYQ54csPopqatlKLL7MH4zZwgtNXdVeJOStvNWsdZ6bhSa4j5M5PzCt+oGOQ\n+vzeFpL0ht8rgkwSMZW/hUmODQSyJ78dcyDb+f5ii/w2B5rLWcLX\nPrivate-Lines: 14\nAAABAH3xHLXCM9LKMLXvXjBUAa6CWVPFHyXu+S/06g4dZCwgp6qgX2YVn9vQ9wQH\nmIsKxNIZpQHJRUwohms0EssxLusUi7H6063Rb2Ix0q1jT6Qb5XkIAdS3yGx7JiOl\nCwlicR0EZI7sJqeSh25FtUd0X5e6OTacj5g5u55ij5sYYEGfNKLJGgIv+yU8rwfM\nGCSuslRGnFh9rgAZ5gy+f4G3BsKAScDu8vqZs1vhKl6QIKPjkqRsvSH9fqqOh9qS\nIvmpPsnUi2UMhsakA4mMNOgB4uoCIXRQG2O07j/z5lxTnhnjvhOtMfF9wH5FYqrG\nudkn7Gn5MCB+3RChSZ8WhwbGLHEAAACBAO3xE3+GdnuFu3gb+Wc7Ym079kIQDbSK\nm5B2T8n+IlHsVu8B4hs9DvXWX2GPFuZBHDh/aERFrF/+Md8kSjtEnDu/YYZuVx0p\nMa0Tm6HEbbenaVWpOwP8N4w6kUDzWIhYLmda3adP7jwLc/k5jUVOkapTX8zNVJrS\njq74AessKD//AAAAgQDBxQPy5Z0DRuz7nPk6a6UZtatNQkIvQV3WiXBce7n9z0hm\nIx5cxcZKk0j0J0O6HpOWD2sn1gKWSWqyOjPk5rvfpx5cpSc+paz9tEQMjVQct6HM\nund7mhHpj0cJqr/cLstOCmrdQ0dVUufdu5SecvoW5sRv3EQg1F2Rs7T07lh9KQAA\nAIEAvbrROGqX+4ix1oaUOdWGdpSKKCHEqVTt3oKIH8gAVDp68cLJeW5A3vNFpe4a\nOncIhXeTtqDUhdfcsGrbfl8rv/sNth9AuagQ8XsGTwdt0H/oi+jjzwjVUKHwMO5K\ntx+YtTJ5G9JTZ8fXZRGkJFvUT1iWQ4U3mRjXrhK8HvWEc00=\nPrivate-MAC: 9dd301c413d4049ed18488e7765ccec7c48f9f4c\n")
        fout.close()
        
        filepath = "C:/tempOVPN/client.crt"
        fout = open(filepath, "w+")
        fout.write("Certificate:\nData:\nVersion: 3 (0x2)\nSerial Number: 2 (0x2)\nSignature Algorithm: sha256WithRSAEncryption\nIssuer: C=us, ST=al, L=bham, O=uab, OU=cloud, CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nValidity\nNot Before: Jun 29 14:23:14 2019 GMT\nNot After : Jun 26 14:23:14 2029 GMT\nSubject: C=US, ST=CA, L=SanFrancisco, O=Fort-Funston, OU=MyOrganizationalUnit, CN=client/name=EasyRSA/emailAddress=me@myhost.mydomain\nSubject Public Key Info:\nPublic Key Algorithm: rsaEncryption\nPublic-Key: (2048 bit)\nModulus:\n00:c9:09:ef:bd:5a:3a:b9:32:4e:ea:c2:db:2e:a4:\n55:87:38:8f:5b:1e:e6:a3:40:e0:a8:0f:7c:5f:76:\nfd:e7:c8:db:c7:02:25:74:14:4c:6c:66:67:36:5c:\nff:42:ec:79:2e:7a:9c:6f:91:2f:9a:ee:ee:f2:75:\n93:ae:58:6d:df:54:75:f3:e3:cb:a6:71:8b:10:e1:\nb0:dc:45:08:27:8d:f3:42:72:dd:2c:57:d6:44:9d:\n54:20:32:b0:d0:ab:cd:6f:d2:7f:0f:41:3a:39:99:\n5b:85:65:f8:04:d5:e9:e5:65:c9:5e:e9:d7:c8:81:\n40:f1:22:2d:10:1b:05:39:bc:a9:e8:86:da:b5:6b:\n97:33:1c:7f:59:19:57:0c:8d:df:bc:5e:22:b6:15:\n4b:57:dc:a3:0c:a2:1b:92:c7:d9:30:24:d1:75:02:\n28:eb:35:e6:46:ce:4a:e9:f4:ac:72:23:36:fd:2e:\n5a:ef:ff:6f:fc:04:51:66:a1:9a:2a:17:0c:89:08:\n89:cd:01:1b:b8:60:92:03:1e:24:59:11:a2:40:db:\n61:8d:ab:ed:ea:df:c1:2e:9c:9c:26:87:c8:66:df:\ndb:f7:4d:57:9f:2e:4e:c1:92:cc:0e:08:4e:9c:00:\n12:78:33:e1:34:1a:33:40:1d:8e:41:94:82:d1:c0:\nef:19\nExponent: 65537 (0x10001)\nX509v3 extensions:\nX509v3 Basic Constraints:\nCA:FALSE\nNetscape Comment:\nEasy-RSA Generated Certificate\nX509v3 Subject Key Identifier:\n7E:1E:F5:70:F5:3F:C4:B2:31:AE:2B:9D:0A:F4:51:02:C2:58:CE:76\nX509v3 Authority Key Identifier:\nkeyid:77:2C:AE:E1:7E:C3:D1:AB:AF:C8:A8:48:24:F6:4F:F0:53:5A:B9:6B\nDirName:/C=us/ST=al/L=bham/O=uab/OU=cloud/CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nserial:B1:E3:AF:59:69:BB:04:D8\n\n509v3 Extended Key Usage:\nTLS Web Client Authentication\nX509v3 Key Usage:\nDigital Signature\nX509v3 Subject Alternative Name:\nDNS:client\nSignature Algorithm: sha256WithRSAEncryption\n2a:fa:39:06:1f:f1:f8:c1:f1:27:6a:d1:69:8b:b9:b9:9c:83:\n87:0f:c3:57:bb:71:9e:80:c3:f8:4b:dd:15:fb:9a:8a:d1:eb:\n02:5f:b8:aa:20:1a:04:d2:09:2c:97:7c:15:43:5c:1f:29:47:\n38:c4:00:cf:58:c0:87:1d:55:77:9e:3f:93:b9:da:4e:92:77:\nf0:0d:56:d9:75:1a:9b:19:dd:37:55:10:24:af:23:a2:cc:20:\ndf:d9:62:af:b8:11:c9:49:8d:33:bf:4c:e8:d3:80:e7:3f:8f:\nc7:fc:e3:30:ce:02:38:48:dd:4c:a4:27:81:13:76:23:67:c0:\na5:80:cb:dd:d3:a7:02:8f:bb:0c:8c:b6:71:92:6e:e0:07:0f:\ncb:cc:8e:1b:9e:70:c1:0e:b0:08:7d:ea:47:94:44:d8:86:8d:\n17:89:ce:2f:93:b0:40:0c:5f:04:8e:6e:c3:b8:0e:d4:08:52:\n0e:32:a1:ac:1d:2f:25:72:f8:8f:56:ca:75:9b:c5:1a:da:96:\nbe:9c:a9:53:4c:25:0b:d8:bc:11:1d:3f:31:94:33:da:60:c5:\n7c:38:a1:0a:43:b6:10:2c:91:cc:a9:7a:e7:72:d2:31:c7:0f:\n39:a6:25:cf:95:6f:28:5f:fe:7f:8f:d9:df:a7:85:c8:38:28:\n62:7b:75:5f\n-----BEGIN CERTIFICATE-----\nMIIFEDCCA/igAwIBAgIBAjANBgkqhkiG9w0BAQsFADCBjTELMAkGA1UEBhMCdXMx\nCzAJBgNVBAgTAmFsMQ0wCwYDVQQHEwRiaGFtMQwwCgYDVQQKEwN1YWIxDjAMBgNV\nBAsTBWNsb3VkMQ8wDQYDVQQDEwZzZXJ2ZXIxEDAOBgNVBCkTB0Vhc3lSU0ExITAf\nBgkqhkiG9w0BCQEWEm1lQG15aG9zdC5teWRvbWFpbjAeFw0xOTA2MjkxNDIzMTRa\nFw0yOTA2MjYxNDIzMTRaMIGtMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFTAT\nBgNVBAcTDFNhbkZyYW5jaXNjbzEVMBMGA1UEChMMRm9ydC1GdW5zdG9uMR0wGwYD\nVQQLExRNeU9yZ2FuaXphdGlvbmFsVW5pdDEPMA0GA1UEAxMGY2xpZW50MRAwDgYD\nVQQpEwdFYXN5UlNBMSEwHwYJKoZIhvcNAQkBFhJtZUBteWhvc3QubXlkb21haW4w\nggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDJCe+9Wjq5Mk7qwtsupFWH\nOI9bHuajQOCoD3xfdv3nyNvHAiV0FExsZmc2XP9C7HkuepxvkS+a7u7ydZOuWG3f\nVHXz48umcYsQ4bDcRQgnjfNCct0sV9ZEnVQgMrDQq81v0n8PQTo5mVuFZfgE1enl\nZcle6dfIgUDxIi0QGwU5vKnohtq1a5czHH9ZGVcMjd+8XiK2FUtX3KMMohuSx9kw\nJNF1AijrNeZGzkrp9KxyIzb9Llrv/2/8BFFmoZoqFwyJCInNARu4YJIDHiRZEaJA\n22GNq+3q38EunJwmh8hm39v3TVefLk7BkswOCE6cABJ4M+E0GjNAHY5BlILRwO8Z\nAgMBAAGjggFXMIIBUzAJBgNVHRMEAjAAMC0GCWCGSAGG+EIBDQQgFh5FYXN5LVJT\nQSBHZW5lcmF0ZWQgQ2VydGlmaWNhdGUwHQYDVR0OBBYEFH4e9XD1P8SyMa4rnQr0\nUQLCWM52MIHCBgNVHSMEgbowgbeAFHcsruF+w9Grr8ioSCT2T/BTWrlroYGTpIGQ\nMIGNMQswCQYDVQQGEwJ1czELMAkGA1UECBMCYWwxDTALBgNVBAcTBGJoYW0xDDAK\nBgNVBAoTA3VhYjEOMAwGA1UECxMFY2xvdWQxDzANBgNVBAMTBnNlcnZlcjEQMA4G\nA1UEKRMHRWFzeVJTQTEhMB8GCSqGSIb3DQEJARYSbWVAbXlob3N0Lm15ZG9tYWlu\nggkAseOvWWm7BNgwEwYDVR0lBAwwCgYIKwYBBQUHAwIwCwYDVR0PBAQDAgeAMBEG\nA1UdEQQKMAiCBmNsaWVudDANBgkqhkiG9w0BAQsFAAOCAQEAKvo5Bh/x+MHxJ2rR\naYu5uZyDhw/DV7txnoDD+EvdFfuaitHrAl+4qiAaBNIJLJd8FUNcHylHOMQAz1jA\nhx1Vd54/k7naTpJ38A1W2XUamxndN1UQJK8joswg39lir7gRyUmNM79M6NOA5z+P\nx/zjMM4COEjdTKQngRN2I2fApYDL3dOnAo+7DIy2cZJu4AcPy8yOG55wwQ6wCH3q\nR5RE2IaNF4nOL5OwQAxfBI5uw7gO1AhSDjKhrB0vJXL4j1bKdZvFGtqWvpypU0wl\nC9i8ER0/MZQz2mDFfDihCkO2ECyRzKl653LSMccPOaYlz5VvKF/+f4/Z36eFyDgo\nYnt1Xw==\n-----END CERTIFICATE-----\n\n")
        fout.close()

        filepath = "C:/tempOVPN/client.key"
        fout = open(filepath, "w+")
        fout.write("-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJCe+9Wjq5Mk7q\nwtsupFWHOI9bHuajQOCoD3xfdv3nyNvHAiV0FExsZmc2XP9C7HkuepxvkS+a7u7y\ndZOuWG3fVHXz48umcYsQ4bDcRQgnjfNCct0sV9ZEnVQgMrDQq81v0n8PQTo5mVuF\nZfgE1enlZcle6dfIgUDxIi0QGwU5vKnohtq1a5czHH9ZGVcMjd+8XiK2FUtX3KMM\nohuSx9kwJNF1AijrNeZGzkrp9KxyIzb9Llrv/2/8BFFmoZoqFwyJCInNARu4YJID\nHiRZEaJA22GNq+3q38EunJwmh8hm39v3TVefLk7BkswOCE6cABJ4M+E0GjNAHY5B\nlILRwO8ZAgMBAAECggEAUfo/TFNSxtoA3DIA9pAEYFNsAZgkLEX0VhOaf8Mh3jZF\niG0ToLX2Q+2uALkDTeLnt1BAIi5t3xu3TB8tzVY512u1fXJSRNjgAE5HtEph/N8h\nZuM31TEsKmaNO2PwPecQqpyHO0051Or4KvldnXstGWHcL1vjpdsvBCUPgxXBl7zh\njkJAK85Tqs6uN39W62X6diNH9l95MnNCl6Z2HrT351mu5olP2wxAV7YipjguVWsF\nZjQnxqnzF1k5SnVP256AIaoPNsZNcul2sNv/uPKQG2FxPUwAk4kCN63SlowuU8uo\no58ygC0z61m1ApDglEo/NEaJwC+ge0wUBSJ4hVGEAQKBgQDygX7pHe1QZJYo+H8W\nvDZKdf/5Jkvy9Zor7dq2/996uhxybR36WBAit9Ry52jHXCrMdOFTEcGURs+fnuF9\nfcOEx05qylw6YdL+RjkZ1akTLFbcCaJ0+dCjV3aXqgFjwmBtZ9m7oxkL5wZBj1q+\nLSj4sxkDdmMFx+QtaTqdwez14QKBgQDUOb3QbWWHSM/lvbMO0c8KJHp1fchaZW+E\nT9qI40S6TirMFKYpPsVmo32OoNkF3tF/LIPrwRqNB4JR39PI+oKMZD/XWTBkfNe+\noaAGhOtTq1Iqj9Ox4tP5xubFkLrrD51kX/B3UhvjoQ6WaLQzijXTn+HA5m8YhSuN\nW8uHamQwOQKBgQDUeHkFLl9abIbT5sUS2lyG6NQZK2CnwmAN1Rwn8g+Pq7h42PnG\n+ZlJkd7lvbKj2TD2agFxksEUdOY8aCwQkzvW/fjKv/oGfOcsOcKVzAmS7uo7Z4Wb\nD7WpJ1yUjTMigI+Ty8WNDN1I/GUS09MhhNe87s4fpn4j9nsI/oWI6vv+gQKBgBsZ\nBFOIpF4RvfnnruVFhNoWrkwyM1LXgNW0HIM+AZiIQo6sVEsP6MSiO3Xqs8s8GCPU\n20NO9MTtIIazvab71Y93fW9RxAwQpLuCh5xNfH7CcUtd8fnUrJxH+U9gm31IESCv\n31Rc95KpDePGmru5+gV7dKfcxHKtDlKOVf6EZyaBAoGBAOwmyHeJpsxUvgzm/Qua\nNiJCy7lM7xI9vyvleCvkd0dn5lqgukvFgYJjs46kAcSPhdnLqpjH44kLSrLIZtDu\n5kFk6QsZDAhNLiuvxKxh9T0oMV65EDO412eaRfuZY8BnZfoj9QXccUBTcbGRi5Ig\nDMatiJ9uu+9x58ykQSshpPxK\n-----END PRIVATE KEY-----\n")
        fout.close()
        
        return
    
    def writeOPVNViaOS(self, myOVPNIP):
        #write config file and run (from Robert's file)
        SERVER_KEY_PATH = 'C:\\\\tempOVPN\\'
        ovpnString = "client\ndev tun\nproto udp\nremote "+str(myOVPNIP)+" 1194\nresolv-retry infinite\nnobind\npersist-key\npersist-tun\nca " + str(SERVER_KEY_PATH) + "\\ca.crt\ncert " + str(SERVER_KEY_PATH) + "\\client.crt\nkey " + str(str(SERVER_KEY_PATH)) + "\\client.key\nremote-cert-tls server\ncipher AES-256-CBC\nverb 3"
        print('=====')
        print('If for some reason your client101.ovpn file is not created')
        print('copy the following into notepad, save as client101.ovpn')
        print('======= for your .ovpn file =======')
        print(ovpnString)
        print('===================================')
        try:
            filepath = "C:/Program Files/OpenVPN/config/client101.ovpn"
            fout = open(filepath, "w+")
            fout.write(ovpnString)
            fout.close()
        except IOError:
            msg = ("Unable to create file on disk.")
        
        print('VPN started, enjoy!')
        # connect to VPN
        os.system('openvpn-gui.exe --connect "client101.ovpn"')
        
        return
    

    
    def startANewVPN(self):
        # start the timer to collect timing data
        self.start_time = time.time()
        
        # get a resource and client appropriate for the Region
        self.ec2NR = boto3.resource(
                            'ec2',
                            region_name = self.locationLastHop
                            )
        self.ec2cNR = boto3.client(
                        'ec2',
                        region_name = self.locationLastHop
                        )
        
        # complete pre-reqs -- import keys
        self.importKeys(ec2cNR)
        
        # complete pre-reqs -- configure security groups
        self.secGrpName = 'CS403AlphaVPN'
        print('===== Security Group ID for '+ locationLastHop + ' =====')
        print('   '+str(setSecurityGroup(ec2cNR, secGrpName)))
        
        # first, create an OpenVPN instance, and log it
        print('===== setting up OpenVPN server ===== \n===== this will take a few seconds =====')
        myInstanceID = (CreateInstance(ec2NR, imageIDConditional, SEC_GRP))
        myOVPNInstance = ec2NR.Instance(myInstanceID)
        myOVPNInstance.wait_until_running()
        myOVPNIP = myOVPNInstance.public_ip_address
        # to trouble shoot ssh connection
        print(myOVPNIP)
        
        if approach == 'o':
            configureVPNServerViaOS(myOVPNIP)
            print("VPN server configured --- %s seconds ---" % (time.time() - start_time))
            if numberOfHops == '1':
                writeOPVNViaOS(myOVPNIP)
        
        if approach == 'b':
            # TO DO
            configureVPNServerViaBoto(myOVPNIP, locationLastHop, myInstanceID)
            print("VPN server configured --- %s seconds ---" % (time.time() - start_time))
            if numberOfHops == '1':
                writeOPVNViaBoto(myOVPNIP)
        
        print("VPN server complete --- %s seconds ---" % (time.time() - start_time))
        print('=======\nVPN Server :', myInstanceID, myOVPNIP)
        
        # if two hops, also create a port forwarding VM, forwarding to the OpenVPN VM
        if (numberOfHops == '2') or (numberOfHops == '3'):
            print('setting up Hop server as: ')
            myInstanceID2 = (CreateInstance(ec2NR, imageIDConditional, SEC_GRP))
            myHopInstance = ec2NR.Instance(myInstanceID2)
            myHopInstance.wait_until_running()
            myHopIP = myHopInstance.public_ip_address
            print("Hop1 server initialized --- %s seconds ---" % (time.time() - start_time))
            print('=======\nHop Server :',myInstanceID2, myHopIP)
            
            if approach == 'o':
                configureHopServerViaOS(myOVPNIP, myHopIP)
                print("Hop1 server configured --- %s seconds ---" % (time.time() - start_time))
                if numberOfHops == '2':
                    writeOPVNViaOS(myHopIP)
                    return
                    
            if approach == 'b':
                # TO DO
                configureHopServerViaBoto(myOVPNIP, myHopIP, locationLastHop, myInstanceID2)
                print("Hop1 server configured --- %s seconds ---" % (time.time() - start_time))
                if numberOfHops == '2':
                    writeOPVNViaBoto(myHopIP)
                    return
            
            # if three hops, rinse and repeat step 2
            if (numberOfHops == '3'):
                print('setting up Hop server #2 as: ')
                myInstanceID3 = (CreateInstance(ec2NR, imageIDConditional, SEC_GRP))
                myHopInstance3 = ec2NR.Instance(myInstanceID3)
                myHopInstance3.wait_until_running()
                myHopIP3 = myHopInstance3.public_ip_address
                print("Hop2 server initialized --- %s seconds ---" % (time.time() - start_time))
                print('=======\nHop Server #2:',myInstanceID3, myHopIP3)
            
                if approach == 'o':
                    configureHopServerViaOS(myHopIP, myHopIP3)
                    print("Hop2 server configured --- %s seconds ---" % (time.time() - start_time))
                    writeOPVNViaOS(myHopIP3)
                    return
                        
                if approach == 'b':
                    # TO DO
                    configureHopServerViaBoto(myHopIP, myHopIP3, locationLastHop, myInstanceID3)
                    print("Hop2 server configured --- %s seconds ---" % (time.time() - start_time))
                    writeOPVNViaBoto(myHopIP3)
                    return
            
            return
            
        
        return 'VPN started, enjoy!'
