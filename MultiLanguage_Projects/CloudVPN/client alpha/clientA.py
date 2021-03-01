import os
import time

#virginia
#os.system('openvpn-gui.exe --connect "openvpn_3_219_244_241_autologin_p6854.ovpn"')

#soeul
#os.system('openvpn-gui.exe --connect "openvpn_13_124_241_113_autologin_p4848.ovpn"')

#london
#os.system('openvpn-gui.exe --connect "openvpn_3_9_57_230_autologin_p8880.ovpn"')





import boto3
import logging
from botocore.exceptions import ClientError


ec2 = boto3.client('ec2')

response = ec2.describe_vpcs()
vpc_id = response.get('Vpcs', [{}])[0].get('VpcId', '')


response = ec2.create_security_group(GroupName='new',
                                     Description='new',
                                     VpcId=vpc_id)
security_group_id = response['GroupId']
#print('Security Group Created %s in vpc %s.' % (security_group_id, vpc_id))

data = ec2.authorize_security_group_ingress(
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
#print('Ingress Successfully Set %s' % data)



# initialize variables

REGION_ID = 'us-east-1'
IMAGE_ID = 'ami-026c8acd92718196b'

ec2 = boto3.resource(
                    'ec2',
                    region_name = REGION_ID
                    )

instanceS = ec2.create_instances(
    ImageId=IMAGE_ID,
    MinCount=1,
    MaxCount=1,
    InstanceType='t2.micro',
    SecurityGroupIds=[
            security_group_id,
        ],
    KeyName='virgkey'
)
print(instanceS[0].id)
instanceS[0].wait_until_running()
instanceS[0].reload()
ipaddress = instanceS[0].public_ip_address
print (ipaddress)
time.sleep(10)

shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo apt-get update"'
os.system(shellSTR)
time.sleep(15)
shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo apt-get install -y openvpn"'
os.system(shellSTR)
time.sleep(25)
shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo chmod -R 777 /etc/openvpn"'
os.system(shellSTR)
shellSTR='cd c:\\users\\robpr &&echo y | pscp -i virgkey.ppk C:\\Users\\robpr\\ohiokeys\\* ubuntu@'+ipaddress+':/etc/openvpn'
os.system(shellSTR)
time.sleep(10)

shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo sysctl net.ipv4.ip_forward=1;sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
os.system(shellSTR)
shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "cd /etc/openvpn;sudo systemctl start openvpn@server.service"'
os.system(shellSTR)

#hop

instance = ec2.create_instances(
    ImageId=IMAGE_ID,
    MinCount=1,
    MaxCount=1,
    InstanceType='t2.micro',
    SecurityGroupIds=[
            security_group_id,
        ],
    KeyName='virgkey'
)
print(instance[0].id)
instance[0].wait_until_running()
instance[0].reload()
ipaddressH = instance[0].public_ip_address
print (ipaddressH)
time.sleep(15)
shellSTR='cd c:\\users\\robpr && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddressH + ' "sudo sysctl net.ipv4.ip_forward=1; sudo iptables -t nat -A PREROUTING -p udp --dport 1194 -j DNAT --to-destination '+ipaddress+':1194; sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
os.system(shellSTR)
time.sleep(15)

#write config file and run

filepath = "C:/Program Files/OpenVPN/config/client101.ovpn"
fout = open(filepath, "w+")

fout.write("client\ndev tun\nproto udp\nremote "+ipaddressH+" 1194\nresolv-retry infinite\nnobind\npersist-key\npersist-tun\nca C:\\\\Users\\\\robpr\\\\ohiokeys\\\\ca.crt\ncert C:\\\\Users\\\\robpr\\\\ohiokeys\\\\client.crt\nkey C:\\\\Users\\\\robpr\\\\ohiokeys\\\\client.key\nremote-cert-tls server\ncipher AES-256-CBC\nverb 3")
fout.close()

os.system('openvpn-gui.exe --connect "client101.ovpn"')


