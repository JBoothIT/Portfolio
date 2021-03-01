# -*- coding: utf-8 -*-
"""
Created on Tue Jul  2 15:40:19 2019

@author: John Bedingfield
This code stand-ups an EC2 instance, and returns the instance ID.

This code assumes:
    1. Your public and private keys are in default location at ~/.aws/credentials:
    in a file called 'credentials/txt', and in the following format:
            [default]
            aws_access_key_id = KEYHERE...
            aws_secret_access_key = KEYHERE...
    
    2. You want to stand-up the instance in the location defined in the static
    variable REGION_ID
    
    3.  You want to stand-up a new OpenVPN instance, which has a AMI ID (IMAGE_ID) of ami-04f63e8f7a845159a
    If you want to stand-up a different kind of instance, find the AMI ID for that instance,
    (make sure it matches the region) and change IMAGE_ID static variable to reflect the new AMI Id
    
    4.  Place your keypair (xxx.pem) file in the same folder as this code, and 
    place the name of your keypair in the static variable 'KEY_NAME'.
    If you need to generate a new key pair, the code is: (untested)
            outfile = open('TestKey.pem','w')
            key_pair = ec2.create_key_pair(KeyName='TestKey')
            KeyPairOut = str(key_pair.key_material)
            outfile.write(KeyPairOut)
    
TO DO:
    a. [done] configure instance security groups
    b. provide any other configuration needed (for example, run through the initial OpenVPN start-up and configuration menus)
    c. collect metrics on the instance (for billing purposes)
    d. [done] provide functions to stop, start, and terminate the instance
    e. [done] provide capability to start a snapshot versus instance (this would allow us to preconfigure the instance)
    
This version includes:
    (a) standing up a server based on a snapshot ImageId (AMI ID), add security group, code to start, stop, terminate
    
"""

import boto3
import logging
from botocore.exceptions import ClientError

# initialize variables
ACCESS_KEY = ''
SECRET_KEY = ''
REGION_ID = 'us-east-2'
KEY_NAME = 'linuxAMI2018v1'

# to stand-up a default OpenVPN Server, which then needs to be configured,
# use this AMI ID
#IMAGE_ID = 'ami-04f63e8f7a845159a'

# based on my OpenVPN Launch Instance
IMAGE_ID = 'ami-04f63e8f7a845159a'

SEC_GRP = 'OpenVPN Access Server-2-6-1a-AutogenByAWSMP-'


# *****
# this code is from Assignment #1, and may be used
# to read AWS credentials from a file named awskeys
# if you need to use this code, you need to change the ec2 assignment code to match

with open("awskeys.txt", "r") as f:
     for line_of_text in f:
         #print(line_of_text)
         words = line_of_text.split('= ')
         firstWord = words[0]
         if 'id' in firstWord:
             ACCESS_KEY = words[1]
             # the next few lines remove any extra spaces or newlines
             ACCESS_KEY.strip()
             ACCESS_KEY.rstrip('\n')
             ACCESS_KEY = ACCESS_KEY[0:20]
         if 'secret' in firstWord:
             SECRET_KEY = words[1]
             SECRET_KEY.strip()
     # the next line tests to ensure we just have the keys and no extra characters
     # print('AK :' + ACCESS_KEY + 'SK :' + SECRET_KEY)
     

# *****
# create a new instance

ec2 = boto3.resource(
                    'ec2',
                    region_name = REGION_ID
                    )

instance = ec2.create_instances(
    ImageId=IMAGE_ID,
    MinCount=1,
    MaxCount=1,
    KeyName=KEY_NAME,
    InstanceType='t2.micro',
    SecurityGroups=[SEC_GRP]
)
print(instance[0].id, instance[0].instance_type)

# *****
# to start, stop, terminate the instance

'''
# set-up the client
ec2c = boto3.client(
                    'ec2',
                    region_name = REGION_ID)
# define variables
instance_id = 'i-0c78f565b99534206'
# instance_id = instance[0].id

'''

'''
try:
    response = ec2c.start_instances(InstanceIds=[instance_id], DryRun=False)
    print(response)
except ClientError as e:
    print(e)
'''

'''
try:
    response = ec2c.stop_instances(InstanceIds=[instance_id], DryRun=False)
    print(response)
except ClientError as e:
    print(e)
'''

'''
try:
    response = ec2c.terminate_instances(InstanceIds=[instance_id], DryRun=False)
    print(response)
except ClientError as e:
    print(e)
'''
