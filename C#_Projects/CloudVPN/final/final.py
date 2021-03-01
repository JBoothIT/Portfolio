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

a = int(input("1-Seoul 2-London 3-Cali 4-Virginia: "))
b = int(input("Number of hops: "))

if a==1:
    REGION_ID ='ap-northeast-2'
    IMAGE_ID = 'ami-0794a2d1e6d99117a'
if a==2:
    REGION_ID ='eu-west-2'
    IMAGE_ID = 'ami-0c30afcb7ab02233d'
if a==3:
    REGION_ID ='us-west-1'
    IMAGE_ID = 'ami-068670db424b01e9a'
if a==4:
    REGION_ID ='us-east-1'
    IMAGE_ID = 'ami-026c8acd92718196b'




ec2 = boto3.client('ec2',
                    region_name = REGION_ID)

response = ec2.import_key_pair(
    
    KeyName='string',
    PublicKeyMaterial="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC0Gd3ivAtPwyeZh/ry1b36ADYbQxkuki4lOb4L5SIOF6wYMt24rDcs2r2VJ50H96SBpzvW5l86BUbBkXbtU3A9mWaoEu9AUVD3fPUs8G0rURQFDtjGEMw/UgAG2uKMKRq8sRWemHj9p6hfH92TEkcQwFeA5TGrW4DL8fqoatKdKNzAQx9jXhemYFan2vl37AK5N8mC406BK/kcDV9QiGsRbGDxgMRkKr6ud/eZIYQ54csPopqatlKLL7MH4zZwgtNXdVeJOStvNWsdZ6bhSa4j5M5PzCt+oGOQ+vzeFpL0ht8rgkwSMZW/hUmODQSyJ78dcyDb+f5ii/w2B5rLWcLX virgkey")
print(response)

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
    KeyName='string'
)

dirName = 'C:/tempOVPN'
if not os.path.exists(dirName):
    os.mkdir(dirName)
    print("Directory " , dirName ,  " Created ")
#serverside 

filepath = "C:/tempOVPN/server.conf"
fout = open(filepath, "w+")
fout.write('port 1194\nproto udp\ndev tun\nca /etc/openvpn/ca.crt\ncert /etc/openvpn/server.crt\nkey /etc/openvpn/server.key  # This file should be kept secret\ndh /etc/openvpn/dh2048.pem\nserver 10.8.0.0 255.255.255.0\nifconfig-pool-persist /var/log/openvpn/ipp.txt\npush "dhcp-option DNS 208.67.222.222"\npush "dhcp-option DNS 208.67.220.220"\nkeepalive 10 120\ncipher AES-256-CBC\npersist-key\npersist-tun\nstatus /var/log/openvpn/openvpn-status.log\nverb 3\nexplicit-exit-notify 1\npush "redirect-gateway def1"\npush "dhcp-option DNS 8.8.8.8"\npush "dhcp-option DNS 8.8.4.4"')
fout.close()

filepath = "C:/tempOVPN/dh2048.pem"
fout = open(filepath, "w+")
fout.write('-----BEGIN DH PARAMETERS-----\nMIIBCAKCAQEAsu4+DWcADrM8zGhOTE3cOgLFTAazeRl7pv7/6UQQ5F7YCbNRxpbJ\ntqq0WmiZIqH5lcdUN099pO8Xudm1uQR8nznsTx6FVGpgemtJJti6gc/OPFjowcan\nAJl/tEZQQwJiEx4VrZtgZVETmg2ciTDHu7m8Eq8jgSrQ76PtrHecdWK+a1HS8QPY\nmIyVyUBmYPsE1NkA5FQPe4+T3cNnXREsZ3hpLQ7IOZN6atOOEY6v4VQUQNBdZakK\nE57PGHZ90g//Pmo3D/bBAIrJliyqyeY+/DKnOLjzrDzLlVMKwMrCj3LLjd0MGTm/\nlJDwh9osKM7L2K/upHbF8hnifs4vVt2GcwIBAg==\n-----END DH PARAMETERS-----\n')
fout.close()

filepath = "C:/tempOVPN/server.crt"
fout = open(filepath, "w+")
fout.write('Certificate:\nData:\nVersion: 3 (0x2)\nSerial Number: 1 (0x1)\nSignature Algorithm: sha256WithRSAEncryption\nIssuer: C=us, ST=al, L=bham, O=uab, OU=cloud, CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nValidity\nNot Before: Jun 29 14:22:30 2019 GMT\nNot After : Jun 26 14:22:30 2029 GMT\nSubject: C=us, ST=al, L=bham, O=cloud, OU=MyOrganizationalUnit, CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nSubject Public Key Info:\nPublic Key Algorithm: rsaEncryption\nPublic-Key: (2048 bit)\nModulus:\n00:bd:a6:93:de:2b:30:f4:95:6b:b6:1a:bf:3b:b9:\n96:5c:25:4d:b9:cc:a3:82:3d:dd:67:40:23:e5:b1:\ne6:f6:f3:ca:e7:b2:44:e4:f2:d4:3a:3d:50:64:2d:\n78:15:49:84:c9:7e:95:7a:7f:11:bb:ba:63:4e:9e:\ncd:12:67:31:e4:59:74:1b:46:69:66:a4:4b:b7:88:\n8a:05:40:34:90:81:8e:d6:f2:ed:69:90:75:02:56:\n67:f8:61:c1:38:73:a5:af:2a:c1:7d:4d:10:ac:4a:\ndf:c1:a9:49:a2:d8:6a:8b:aa:51:13:78:57:56:dd:\n85:ee:6e:58:58:e6:13:9a:a3:ac:a9:ab:00:42:08:\n88:9b:9f:5f:1b:10:09:6e:27:32:34:8f:72:4d:8a:\ne0:94:0a:38:1a:cb:69:14:77:50:4c:c5:f3:be:d4:\n27:52:d8:62:0a:13:5a:73:eb:36:bc:d2:3b:7e:dd:\nf3:8b:d7:df:02:42:dd:bd:09:fe:12:3c:42:86:80:\ne2:9f:76:8a:5f:a3:7c:08:3c:f6:f8:a4:c6:fd:1f:\ne0:43:c6:22:39:55:af:11:f1:2f:9a:9d:92:47:3e:\n9e:0b:0e:63:8f:8c:c5:6b:60:94:04:c6:bd:a1:ad:\n28:70:b6:51:f5:bb:33:1c:f4:65:2d:f3:d6:9f:be:\n7c:29\nExponent: 65537 (0x10001)\nX509v3 extensions:\nX509v3 Basic Constraints:\nCA:FALSE\nNetscape Cert Type:\nSSL Server\nNetscape Comment:\nEasy-RSA Generated Server Certificate\nX509v3 Subject Key Identifier:\nEC:7A:B6:64:62:05:4C:F0:6C:26:AC:76:CE:9E:1C:2D:56:DF:FE:E6\nX509v3 Authority Key Identifier:\nkeyid:77:2C:AE:E1:7E:C3:D1:AB:AF:C8:A8:48:24:F6:4F:F0:53:5A:B9:6B\nDirName:/C=us/ST=al/L=bham/O=uab/OU=cloud/CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nserial:B1:E3:AF:59:69:BB:04:D8\nX509v3 Extended Key Usage:\nTLS Web Server Authentication\nX509v3 Key Usage:\nDigital Signature, Key Encipherment\nX509v3 Subject Alternative Name:\nDNS:server\nSignature Algorithm: sha256WithRSAEncryption\n01:46:ad:79:9c:63:0a:75:fa:0e:96:9d:2a:46:5e:8c:df:7b:\n43:d6:02:ad:94:50:22:79:30:67:7f:c5:da:eb:bc:91:fb:34:\n2f:dd:74:d9:8c:e8:f4:10:7f:e1:d2:78:1e:bf:5a:32:1b:a4:\n83:4e:9a:75:e9:47:c8:0a:23:dd:3b:b7:7d:e1:f1:75:32:a4:\n3d:a9:b3:95:1e:23:55:3d:85:e8:cd:d9:06:17:54:ee:1c:55:\nf2:af:b8:4b:7b:f0:54:be:eb:79:71:56:4d:89:05:2f:de:e5:\n5a:54:0d:12:c6:a6:e1:6b:7f:ca:f1:bb:4a:b1:53:88:ad:d6:\n06:82:79:2b:f7:43:85:e5:c5:8f:52:f4:23:4e:70:fa:6b:1c:\n99:e9:f0:9a:ec:3f:17:55:c6:d1:8f:2a:63:a8:c1:4c:59:35:\n88:d9:eb:13:5f:d6:ec:d2:d9:b6:48:4a:72:f0:37:6a:75:49:\nfc:46:0a:e4:82:82:fa:86:e1:87:94:16:23:81:20:56:d8:de:\n5b:8a:28:b2:e5:6f:aa:1c:04:e1:5b:f3:9d:64:22:d3:3a:15:\n74:63:25:f5:8f:5e:bd:6f:3e:16:98:03:86:8c:21:9f:9a:48:\nef:de:9b:5f:96:28:a4:4f:b3:8f:9e:e6:85:4b:5f:9f:2d:65:\na7:b5:03:2c\n-----BEGIN CERTIFICATE-----\nMIIFGzCCBAOgAwIBAgIBATANBgkqhkiG9w0BAQsFADCBjTELMAkGA1UEBhMCdXMx\nCzAJBgNVBAgTAmFsMQ0wCwYDVQQHEwRiaGFtMQwwCgYDVQQKEwN1YWIxDjAMBgNV\nBAsTBWNsb3VkMQ8wDQYDVQQDEwZzZXJ2ZXIxEDAOBgNVBCkTB0Vhc3lSU0ExITAf\nBgkqhkiG9w0BCQEWEm1lQG15aG9zdC5teWRvbWFpbjAeFw0xOTA2MjkxNDIyMzBa\nFw0yOTA2MjYxNDIyMzBaMIGeMQswCQYDVQQGEwJ1czELMAkGA1UECBMCYWwxDTAL\nBgNVBAcTBGJoYW0xDjAMBgNVBAoTBWNsb3VkMR0wGwYDVQQLExRNeU9yZ2FuaXph\ndGlvbmFsVW5pdDEPMA0GA1UEAxMGc2VydmVyMRAwDgYDVQQpEwdFYXN5UlNBMSEw\nHwYJKoZIhvcNAQkBFhJtZUBteWhvc3QubXlkb21haW4wggEiMA0GCSqGSIb3DQEB\nAQUAA4IBDwAwggEKAoIBAQC9ppPeKzD0lWu2Gr87uZZcJU25zKOCPd1nQCPlseb2\n88rnskTk8tQ6PVBkLXgVSYTJfpV6fxG7umNOns0SZzHkWXQbRmlmpEu3iIoFQDSQ\ngY7W8u1pkHUCVmf4YcE4c6WvKsF9TRCsSt/BqUmi2GqLqlETeFdW3YXublhY5hOa\no6ypqwBCCIibn18bEAluJzI0j3JNiuCUCjgay2kUd1BMxfO+1CdS2GIKE1pz6za8\n0jt+3fOL198CQt29Cf4SPEKGgOKfdopfo3wIPPb4pMb9H+BDxiI5Va8R8S+anZJH\nPp4LDmOPjMVrYJQExr2hrShwtlH1uzMc9GUt89afvnwpAgMBAAGjggFxMIIBbTAJ\nBgNVHRMEAjAAMBEGCWCGSAGG+EIBAQQEAwIGQDA0BglghkgBhvhCAQ0EJxYlRWFz\neS1SU0EgR2VuZXJhdGVkIFNlcnZlciBDZXJ0aWZpY2F0ZTAdBgNVHQ4EFgQU7Hq2\nZGIFTPBsJqx2zp4cLVbf/uYwgcIGA1UdIwSBujCBt4AUdyyu4X7D0auvyKhIJPZP\n8FNauWuhgZOkgZAwgY0xCzAJBgNVBAYTAnVzMQswCQYDVQQIEwJhbDENMAsGA1UE\nBxMEYmhhbTEMMAoGA1UEChMDdWFiMQ4wDAYDVQQLEwVjbG91ZDEPMA0GA1UEAxMG\nc2VydmVyMRAwDgYDVQQpEwdFYXN5UlNBMSEwHwYJKoZIhvcNAQkBFhJtZUBteWhv\nc3QubXlkb21haW6CCQCx469ZabsE2DATBgNVHSUEDDAKBggrBgEFBQcDATALBgNV\nHQ8EBAMCBaAwEQYDVR0RBAowCIIGc2VydmVyMA0GCSqGSIb3DQEBCwUAA4IBAQAB\nRq15nGMKdfoOlp0qRl6M33tD1gKtlFAieTBnf8Xa67yR+zQv3XTZjOj0EH/h0nge\nv1oyG6SDTpp16UfICiPdO7d94fF1MqQ9qbOVHiNVPYXozdkGF1TuHFXyr7hLe/BU\nvut5cVZNiQUv3uVaVA0Sxqbha3/K8btKsVOIrdYGgnkr90OF5cWPUvQjTnD6axyZ\n6fCa7D8XVcbRjypjqMFMWTWI2esTX9bs0tm2SEpy8DdqdUn8RgrkgoL6huGHlBYj\ngSBW2N5biiiy5W+qHAThW/OdZCLTOhV0YyX1j169bz4WmAOGjCGfmkjv3ptfliik\nT7OPnuaFS1+fLWWntQMs\n-----END CERTIFICATE-----\n')
fout.close()

filepath = "C:/tempOVPN/server.key"
fout = open(filepath, "w+")
fout.write('-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC9ppPeKzD0lWu2\nGr87uZZcJU25zKOCPd1nQCPlseb288rnskTk8tQ6PVBkLXgVSYTJfpV6fxG7umNO\nns0SZzHkWXQbRmlmpEu3iIoFQDSQgY7W8u1pkHUCVmf4YcE4c6WvKsF9TRCsSt/B\nqUmi2GqLqlETeFdW3YXublhY5hOao6ypqwBCCIibn18bEAluJzI0j3JNiuCUCjga\ny2kUd1BMxfO+1CdS2GIKE1pz6za80jt+3fOL198CQt29Cf4SPEKGgOKfdopfo3wI\nPPb4pMb9H+BDxiI5Va8R8S+anZJHPp4LDmOPjMVrYJQExr2hrShwtlH1uzMc9GUt\n89afvnwpAgMBAAECggEAWI5R+andYDftZckzhqXwSfK5Sor9xrEwkyfmGzlpCeNo\n2avqc6XX56H9yelZa0c7FANhtSNkmm48NNf+FhvNtjz29E16pg0NthOxryX95YQ5\nuLESnvpvSLokLl0nJj8KHLMwSlwT7v+/X+S2l63KgpDy7s6AqfUituUbp9c3TDtW\nX+JR0DmnVmLCtRq8NHEZekZdRcQ8O1jemHkI3h68g2u0YAAYhxAtceEsYU9az2dG\n4Plo0IQoVMngKges8JSYyeJkxsATfdiS4vAYO6/7lvINbkjtld9TKuW0RJcSWFfK\nJldSWbcKwHFc0MpnfNu0PBpqRnyPG1U7Ueg6qZhQAQKBgQD0bkbYJxdchuDDoNqH\n8G0WCNbexg+5JAEn4yxoqbksH/irUvSMRKu++xamOz+fRVTZ0BR4xw9eFYar0EvC\nTGdgUp5SYPVL9QcElWNwpyn1my30F0sg7yfFqKKWlBszAnoIM+ljjFIWHneJ2dKz\nEe8wfvJxARy+HRFHyAYhp463KQKBgQDGoIpn9nCMZpMhl/peJUJ3wtpTjI3+O5ux\nl7W+lnsPy6B2YjuFYQ2ye+Z7rvoXmkXMjs/SheZ2LPJcn3bZ6VJe8cm2ZdlQPEyn\nZ1BazY320ZOoRty2+yKCYWLQ0hR+BmTUfyTtZi5ZpHISf55K+1QnKSOcJoDuKnbt\nWqfZupM9AQKBgQDt7R4Wr06FKWFF66BsbHKy3R3SsXaCn/JkEjSbGIJ/2gsjMVeL\n5hhLiXuNieSvMzyZ3Hrg6dsimM+DFPl5gwepciJcS7baHgObyZKHCKDGSywbCkV+\nJG1RcIn7CuXC7Gk+7NEEroUEtNllHNuEpTNvwFJb/1osEH1Oovyc6832qQKBgQCA\nS5P6oNCDr4GbcknyY4iySHay/pd8vk3LgR2QVCx6wGEN9ldRyKWLQsJV42iAtFIf\nVaNg7MI2VMPbFdkz2aQt1Wkf2ltqmvg8LNxXOmxXH3mLePH5cu8QXgzwqrE30bNx\nGgrlGFoZ5sETt8Cr1/i8LrNkme4vZ9hXaXX/iaavAQKBgQCJ4J3CqVxB/OvRiMf6\nK4U5SuLsMe72WIr2aISivFzBJ4Q7YPNJKK74kxqkSMefhLj50P1CrKzrTMhZm4r7\nIESaPmUbPhAYm+SR2VzGqUCuDRfyGb+MAF1HuPWDEnHEcgajrF82G32Weqr84sMv\n3PQtxJPTQVS03MwjFHehIh1Klw==\n-----END PRIVATE KEY-----\n')
fout.close()

filepath = "C:/tempOVPN/ca.crt"
fout = open(filepath, "w+")
fout.write("-----BEGIN CERTIFICATE-----\nMIIElTCCA32gAwIBAgIJALHjr1lpuwTYMA0GCSqGSIb3DQEBCwUAMIGNMQswCQYD\nVQQGEwJ1czELMAkGA1UECBMCYWwxDTALBgNVBAcTBGJoYW0xDDAKBgNVBAoTA3Vh\nYjEOMAwGA1UECxMFY2xvdWQxDzANBgNVBAMTBnNlcnZlcjEQMA4GA1UEKRMHRWFz\neVJTQTEhMB8GCSqGSIb3DQEJARYSbWVAbXlob3N0Lm15ZG9tYWluMB4XDTE5MDYy\nOTE0MjA0NVoXDTI5MDYyNjE0MjA0NVowgY0xCzAJBgNVBAYTAnVzMQswCQYDVQQI\nEwJhbDENMAsGA1UEBxMEYmhhbTEMMAoGA1UEChMDdWFiMQ4wDAYDVQQLEwVjbG91\nZDEPMA0GA1UEAxMGc2VydmVyMRAwDgYDVQQpEwdFYXN5UlNBMSEwHwYJKoZIhvcN\nAQkBFhJtZUBteWhvc3QubXlkb21haW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw\nggEKAoIBAQCpsAiwFIzShuqxnMCeKSgESNGyJQLPEmKIifgKk4BqHzvLKtXLcfGc\nhHY6+8xC3hAxWkPaga2VZ9PHMy76KSWwE+k6MjzjMnZy00WvuELxtEamYGp8VTs2\nze537H0mYsnglu2IAQGW7T0fI49/vTKpENgW06jTqhqa8oXjVRq4QOy9eAeUAz/v\ndxcWjX2G+GBUiu2QkZoGN1sGnIKeG+2uI5C59JzswtW4CIwtKfhh0oBxFx9hXdDr\nxSKvk6Gn1qWZeKaw7kkJGhtixzsPiV7+SXa3mCg/MQNPS2tyB21c3ToGwD0NVCN2\njPMPlhDzYMlDXlioIeFNp7+yvcbbM36NAgMBAAGjgfUwgfIwHQYDVR0OBBYEFHcs\nruF+w9Grr8ioSCT2T/BTWrlrMIHCBgNVHSMEgbowgbeAFHcsruF+w9Grr8ioSCT2\nT/BTWrlroYGTpIGQMIGNMQswCQYDVQQGEwJ1czELMAkGA1UECBMCYWwxDTALBgNV\nBAcTBGJoYW0xDDAKBgNVBAoTA3VhYjEOMAwGA1UECxMFY2xvdWQxDzANBgNVBAMT\nBnNlcnZlcjEQMA4GA1UEKRMHRWFzeVJTQTEhMB8GCSqGSIb3DQEJARYSbWVAbXlo\nb3N0Lm15ZG9tYWluggkAseOvWWm7BNgwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0B\nAQsFAAOCAQEABDUw2IMf3sHGpdGK5EaGfZKNm227X+F7JbcksoXibxK1dLaQ3TXP\n0LSPedpNnIEfTVYP9QpOQHJhuvtuh3HVt6n5LRZBASbP0ekIx9Fd6v1I0BO9/O0L\nRmUjf53rJ3c7g92AeL+iQ0YdEJJbBSszFzUR46jeY/5qSxEMAzmGuA4H7jy/b1G1\nSJAW6drNgErrTKV2EghbW4PBuSzQl4qJ6PZhdDANNCnj+WQ/Qs8yUij/jd2Ccw4f\nPYRE2LjMvPTWfDrOwNlHfHPZBvwOHEc5uisdn4m7bdYo0ZqNi93iRlrxPgkf9xWt\nxg9GStZYsVD5adFF3s/S3Pj2d4+Y7UFjqw==\n-----END CERTIFICATE-----")
fout.close()

filepath = "C:/tempOVPN/virgkey.pem"
fout = open(filepath, "w+")
fout.write("-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEAtBnd4rwLT8MnmYf68tW9+gA2G0MZLpIuJTm+C+UiDhesGDLduKw3LNq9lSed\nB/ekgac71uZfOgVGwZF27VNwPZlmqBLvQFFQ93z1LPBtK1EUBQ7YxhDMP1IABtrijCkavLEVnph4\n/aeoXx/dkxJHEMBXgOUxq1uAy/H6qGrSnSjcwEMfY14XpmBWp9r5d+wCuTfJguNOgSv5HA1fUIhr\nEWxg8YDEZCq+rnf3mSGEOeHLD6KamrZSiy+zB+M2cILTV3VXiTkrbzVrHWem4UmuI+TOT8wrfqBj\nkPr83haS9IbfK4JMEjGVv4VJjg0Esie/HXMg2/n+Yov8Ngeay1nC1wIDAQABAoIBAH3xHLXCM9LK\nMLXvXjBUAa6CWVPFHyXu+S/06g4dZCwgp6qgX2YVn9vQ9wQHmIsKxNIZpQHJRUwohms0EssxLusU\ni7H6063Rb2Ix0q1jT6Qb5XkIAdS3yGx7JiOlCwlicR0EZI7sJqeSh25FtUd0X5e6OTacj5g5u55i\nj5sYYEGfNKLJGgIv+yU8rwfMGCSuslRGnFh9rgAZ5gy+f4G3BsKAScDu8vqZs1vhKl6QIKPjkqRs\nvSH9fqqOh9qSIvmpPsnUi2UMhsakA4mMNOgB4uoCIXRQG2O07j/z5lxTnhnjvhOtMfF9wH5FYqrG\nudkn7Gn5MCB+3RChSZ8WhwbGLHECgYEA7fETf4Z2e4W7eBv5ZztibTv2QhANtIqbkHZPyf4iUexW\n7wHiGz0O9dZfYY8W5kEcOH9oREWsX/4x3yRKO0ScO79hhm5XHSkxrRObocRtt6dpVak7A/w3jDqR\nQPNYiFguZ1rdp0/uPAtz+TmNRU6RqlNfzM1UmtKOrvgB6ywoP/8CgYEAwcUD8uWdA0bs+5z5Omul\nGbWrTUJCL0Fd1olwXHu5/c9IZiMeXMXGSpNI9CdDuh6Tlg9rJ9YClklqsjoz5Oa736ceXKUnPqWs\n/bREDI1UHLehzLp3e5oR6Y9HCaq/3C7LTgpq3UNHVVLn3buUnnL6FubEb9xEINRdkbO09O5YfSkC\ngYB5jsSgTMhw+HQ6u0fh9lOlbJG1VFA8IOeymM7QadWMtsn+1p51ve7LpxYZWCmhvje1oVlaL6IN\ndGZ0Ei0eNEIAwBgJvosgqa/oNPE4ZoqK3asRiSBOO+cA69bTML7VAP/L6377f/k8kblQ5JcIhkgi\nlO+HSIrlgZZFSwxn4ao6bwKBgBjUdDMV9U7MMeX0MYOo85kdzHuz9+MyTyIErp9LQ4Qgobhk26kr\ntsMrqHeQ6H7bGDJse2C5bc9wTPnVt3ynjH+WXK+mgibm+AnypZ2uJo8fgN5JLrJqBc2WDJZSiQ9f\n97FiaFs+Gc/3NbsWJG9BcIaqv5VN9nT2gZ8AFjokqLYxAoGBAL260Thql/uIsdaGlDnVhnaUiigh\nxKlU7d6CiB/IAFQ6evHCyXluQN7zRaXuGjp3CIV3k7ag1IXX3LBq235fK7/7DbYfQLmoEPF7Bk8H\nbdB/6Ivo488I1VCh8DDuSrcfmLUyeRvSU2fH12URpCRb1E9YlkOFN5kY164SvB71hHNN\n-----END RSA PRIVATE KEY-----")
fout.close()

shellSTR='set key="C:\\tempOVPN\\virgkey.pem" && cmd /c icacls %key% /c /t /inheritance:d && cmd /c icacls %key% /c /t /grant %username%:F && cmd /c icacls %key%  /c /t /remove Administrator "Authenticated Users" BUILTIN\Administrators BUILTIN Everyone System Users'
os.system(shellSTR)

filepath = "C:/tempOVPN/virgkey.ppk"
fout = open(filepath, "w+")
fout.write("PuTTY-User-Key-File-2: ssh-rsa\nEncryption: none\nComment: imported-openssh-key\nPublic-Lines: 6\nAAAAB3NzaC1yc2EAAAADAQABAAABAQC0Gd3ivAtPwyeZh/ry1b36ADYbQxkuki4l\nOb4L5SIOF6wYMt24rDcs2r2VJ50H96SBpzvW5l86BUbBkXbtU3A9mWaoEu9AUVD3\nfPUs8G0rURQFDtjGEMw/UgAG2uKMKRq8sRWemHj9p6hfH92TEkcQwFeA5TGrW4DL\n8fqoatKdKNzAQx9jXhemYFan2vl37AK5N8mC406BK/kcDV9QiGsRbGDxgMRkKr6u\nd/eZIYQ54csPopqatlKLL7MH4zZwgtNXdVeJOStvNWsdZ6bhSa4j5M5PzCt+oGOQ\n+vzeFpL0ht8rgkwSMZW/hUmODQSyJ78dcyDb+f5ii/w2B5rLWcLX\nPrivate-Lines: 14\nAAABAH3xHLXCM9LKMLXvXjBUAa6CWVPFHyXu+S/06g4dZCwgp6qgX2YVn9vQ9wQH\nmIsKxNIZpQHJRUwohms0EssxLusUi7H6063Rb2Ix0q1jT6Qb5XkIAdS3yGx7JiOl\nCwlicR0EZI7sJqeSh25FtUd0X5e6OTacj5g5u55ij5sYYEGfNKLJGgIv+yU8rwfM\nGCSuslRGnFh9rgAZ5gy+f4G3BsKAScDu8vqZs1vhKl6QIKPjkqRsvSH9fqqOh9qS\nIvmpPsnUi2UMhsakA4mMNOgB4uoCIXRQG2O07j/z5lxTnhnjvhOtMfF9wH5FYqrG\nudkn7Gn5MCB+3RChSZ8WhwbGLHEAAACBAO3xE3+GdnuFu3gb+Wc7Ym079kIQDbSK\nm5B2T8n+IlHsVu8B4hs9DvXWX2GPFuZBHDh/aERFrF/+Md8kSjtEnDu/YYZuVx0p\nMa0Tm6HEbbenaVWpOwP8N4w6kUDzWIhYLmda3adP7jwLc/k5jUVOkapTX8zNVJrS\njq74AessKD//AAAAgQDBxQPy5Z0DRuz7nPk6a6UZtatNQkIvQV3WiXBce7n9z0hm\nIx5cxcZKk0j0J0O6HpOWD2sn1gKWSWqyOjPk5rvfpx5cpSc+paz9tEQMjVQct6HM\nund7mhHpj0cJqr/cLstOCmrdQ0dVUufdu5SecvoW5sRv3EQg1F2Rs7T07lh9KQAA\nAIEAvbrROGqX+4ix1oaUOdWGdpSKKCHEqVTt3oKIH8gAVDp68cLJeW5A3vNFpe4a\nOncIhXeTtqDUhdfcsGrbfl8rv/sNth9AuagQ8XsGTwdt0H/oi+jjzwjVUKHwMO5K\ntx+YtTJ5G9JTZ8fXZRGkJFvUT1iWQ4U3mRjXrhK8HvWEc00=\nPrivate-MAC: 9dd301c413d4049ed18488e7765ccec7c48f9f4c\n")
fout.close()




print(instanceS[0].id)
ids =[]
ids.append(instanceS[0].id)
instanceS[0].wait_until_running()
instanceS[0].reload()
ipaddress = instanceS[0].public_ip_address
print (ipaddress)
time.sleep(10)

shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo apt-get update"'
os.system(shellSTR)
time.sleep(20)
shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo apt-get update"'
os.system(shellSTR)
time.sleep(10)
shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo apt-get install -y openvpn"'
os.system(shellSTR)
time.sleep(25)
shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo chmod -R 777 /etc/openvpn"'
os.system(shellSTR)
shellSTR='cd c:\\tempOVPN &&echo y | pscp -i virgkey.ppk C:\\tempOVPN\\* ubuntu@'+ipaddress+':/etc/openvpn'
os.system(shellSTR)
time.sleep(10)

shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "sudo sysctl net.ipv4.ip_forward=1;sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
os.system(shellSTR)
shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddress + ' "cd /etc/openvpn;sudo systemctl start openvpn@server.service"'
os.system(shellSTR)

#hop
if b>0:
    instance = ec2.create_instances(
        ImageId=IMAGE_ID,
        MinCount=b,
        MaxCount=b,
        InstanceType='t2.micro',
        SecurityGroupIds=[
                security_group_id,
            ],
        KeyName='string'
    )
    print(instance[0].id)
    ipaddresses=[]
    for g in range (b):
        ids.append(instance[g].id)
        instance[g].wait_until_running()
        instance[g].reload()
        ipaddresses.append(instance[g].public_ip_address)
        print (ipaddresses[g])
    

    
time.sleep(15)
if b>0:
    shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddresses[0] + ' "sudo sysctl net.ipv4.ip_forward=1; sudo iptables -t nat -A PREROUTING -p udp --dport 1194 -j DNAT --to-destination '+ipaddress+':1194; sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
    os.system(shellSTR)
time.sleep(15)
for q in range (b):
    if q>0:
        shellSTR='cd c:\\tempOVPN && ssh -i virgkey.pem -o "StrictHostKeyChecking=no" ubuntu@'+ipaddresses[q] + ' "sudo sysctl net.ipv4.ip_forward=1; sudo iptables -t nat -A PREROUTING -p udp --dport 1194 -j DNAT --to-destination '+ipaddresses[q-1]+':1194; sudo iptables -t nat -A POSTROUTING -j MASQUERADE"'
        os.system(shellSTR)
    
#write config file and run



filepath = "C:/tempOVPN/client.crt"
fout = open(filepath, "w+")
fout.write("Certificate:\nData:\nVersion: 3 (0x2)\nSerial Number: 2 (0x2)\nSignature Algorithm: sha256WithRSAEncryption\nIssuer: C=us, ST=al, L=bham, O=uab, OU=cloud, CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nValidity\nNot Before: Jun 29 14:23:14 2019 GMT\nNot After : Jun 26 14:23:14 2029 GMT\nSubject: C=US, ST=CA, L=SanFrancisco, O=Fort-Funston, OU=MyOrganizationalUnit, CN=client/name=EasyRSA/emailAddress=me@myhost.mydomain\nSubject Public Key Info:\nPublic Key Algorithm: rsaEncryption\nPublic-Key: (2048 bit)\nModulus:\n00:c9:09:ef:bd:5a:3a:b9:32:4e:ea:c2:db:2e:a4:\n55:87:38:8f:5b:1e:e6:a3:40:e0:a8:0f:7c:5f:76:\nfd:e7:c8:db:c7:02:25:74:14:4c:6c:66:67:36:5c:\nff:42:ec:79:2e:7a:9c:6f:91:2f:9a:ee:ee:f2:75:\n93:ae:58:6d:df:54:75:f3:e3:cb:a6:71:8b:10:e1:\nb0:dc:45:08:27:8d:f3:42:72:dd:2c:57:d6:44:9d:\n54:20:32:b0:d0:ab:cd:6f:d2:7f:0f:41:3a:39:99:\n5b:85:65:f8:04:d5:e9:e5:65:c9:5e:e9:d7:c8:81:\n40:f1:22:2d:10:1b:05:39:bc:a9:e8:86:da:b5:6b:\n97:33:1c:7f:59:19:57:0c:8d:df:bc:5e:22:b6:15:\n4b:57:dc:a3:0c:a2:1b:92:c7:d9:30:24:d1:75:02:\n28:eb:35:e6:46:ce:4a:e9:f4:ac:72:23:36:fd:2e:\n5a:ef:ff:6f:fc:04:51:66:a1:9a:2a:17:0c:89:08:\n89:cd:01:1b:b8:60:92:03:1e:24:59:11:a2:40:db:\n61:8d:ab:ed:ea:df:c1:2e:9c:9c:26:87:c8:66:df:\ndb:f7:4d:57:9f:2e:4e:c1:92:cc:0e:08:4e:9c:00:\n12:78:33:e1:34:1a:33:40:1d:8e:41:94:82:d1:c0:\nef:19\nExponent: 65537 (0x10001)\nX509v3 extensions:\nX509v3 Basic Constraints:\nCA:FALSE\nNetscape Comment:\nEasy-RSA Generated Certificate\nX509v3 Subject Key Identifier:\n7E:1E:F5:70:F5:3F:C4:B2:31:AE:2B:9D:0A:F4:51:02:C2:58:CE:76\nX509v3 Authority Key Identifier:\nkeyid:77:2C:AE:E1:7E:C3:D1:AB:AF:C8:A8:48:24:F6:4F:F0:53:5A:B9:6B\nDirName:/C=us/ST=al/L=bham/O=uab/OU=cloud/CN=server/name=EasyRSA/emailAddress=me@myhost.mydomain\nserial:B1:E3:AF:59:69:BB:04:D8\n\n509v3 Extended Key Usage:\nTLS Web Client Authentication\nX509v3 Key Usage:\nDigital Signature\nX509v3 Subject Alternative Name:\nDNS:client\nSignature Algorithm: sha256WithRSAEncryption\n2a:fa:39:06:1f:f1:f8:c1:f1:27:6a:d1:69:8b:b9:b9:9c:83:\n87:0f:c3:57:bb:71:9e:80:c3:f8:4b:dd:15:fb:9a:8a:d1:eb:\n02:5f:b8:aa:20:1a:04:d2:09:2c:97:7c:15:43:5c:1f:29:47:\n38:c4:00:cf:58:c0:87:1d:55:77:9e:3f:93:b9:da:4e:92:77:\nf0:0d:56:d9:75:1a:9b:19:dd:37:55:10:24:af:23:a2:cc:20:\ndf:d9:62:af:b8:11:c9:49:8d:33:bf:4c:e8:d3:80:e7:3f:8f:\nc7:fc:e3:30:ce:02:38:48:dd:4c:a4:27:81:13:76:23:67:c0:\na5:80:cb:dd:d3:a7:02:8f:bb:0c:8c:b6:71:92:6e:e0:07:0f:\ncb:cc:8e:1b:9e:70:c1:0e:b0:08:7d:ea:47:94:44:d8:86:8d:\n17:89:ce:2f:93:b0:40:0c:5f:04:8e:6e:c3:b8:0e:d4:08:52:\n0e:32:a1:ac:1d:2f:25:72:f8:8f:56:ca:75:9b:c5:1a:da:96:\nbe:9c:a9:53:4c:25:0b:d8:bc:11:1d:3f:31:94:33:da:60:c5:\n7c:38:a1:0a:43:b6:10:2c:91:cc:a9:7a:e7:72:d2:31:c7:0f:\n39:a6:25:cf:95:6f:28:5f:fe:7f:8f:d9:df:a7:85:c8:38:28:\n62:7b:75:5f\n-----BEGIN CERTIFICATE-----\nMIIFEDCCA/igAwIBAgIBAjANBgkqhkiG9w0BAQsFADCBjTELMAkGA1UEBhMCdXMx\nCzAJBgNVBAgTAmFsMQ0wCwYDVQQHEwRiaGFtMQwwCgYDVQQKEwN1YWIxDjAMBgNV\nBAsTBWNsb3VkMQ8wDQYDVQQDEwZzZXJ2ZXIxEDAOBgNVBCkTB0Vhc3lSU0ExITAf\nBgkqhkiG9w0BCQEWEm1lQG15aG9zdC5teWRvbWFpbjAeFw0xOTA2MjkxNDIzMTRa\nFw0yOTA2MjYxNDIzMTRaMIGtMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFTAT\nBgNVBAcTDFNhbkZyYW5jaXNjbzEVMBMGA1UEChMMRm9ydC1GdW5zdG9uMR0wGwYD\nVQQLExRNeU9yZ2FuaXphdGlvbmFsVW5pdDEPMA0GA1UEAxMGY2xpZW50MRAwDgYD\nVQQpEwdFYXN5UlNBMSEwHwYJKoZIhvcNAQkBFhJtZUBteWhvc3QubXlkb21haW4w\nggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDJCe+9Wjq5Mk7qwtsupFWH\nOI9bHuajQOCoD3xfdv3nyNvHAiV0FExsZmc2XP9C7HkuepxvkS+a7u7ydZOuWG3f\nVHXz48umcYsQ4bDcRQgnjfNCct0sV9ZEnVQgMrDQq81v0n8PQTo5mVuFZfgE1enl\nZcle6dfIgUDxIi0QGwU5vKnohtq1a5czHH9ZGVcMjd+8XiK2FUtX3KMMohuSx9kw\nJNF1AijrNeZGzkrp9KxyIzb9Llrv/2/8BFFmoZoqFwyJCInNARu4YJIDHiRZEaJA\n22GNq+3q38EunJwmh8hm39v3TVefLk7BkswOCE6cABJ4M+E0GjNAHY5BlILRwO8Z\nAgMBAAGjggFXMIIBUzAJBgNVHRMEAjAAMC0GCWCGSAGG+EIBDQQgFh5FYXN5LVJT\nQSBHZW5lcmF0ZWQgQ2VydGlmaWNhdGUwHQYDVR0OBBYEFH4e9XD1P8SyMa4rnQr0\nUQLCWM52MIHCBgNVHSMEgbowgbeAFHcsruF+w9Grr8ioSCT2T/BTWrlroYGTpIGQ\nMIGNMQswCQYDVQQGEwJ1czELMAkGA1UECBMCYWwxDTALBgNVBAcTBGJoYW0xDDAK\nBgNVBAoTA3VhYjEOMAwGA1UECxMFY2xvdWQxDzANBgNVBAMTBnNlcnZlcjEQMA4G\nA1UEKRMHRWFzeVJTQTEhMB8GCSqGSIb3DQEJARYSbWVAbXlob3N0Lm15ZG9tYWlu\nggkAseOvWWm7BNgwEwYDVR0lBAwwCgYIKwYBBQUHAwIwCwYDVR0PBAQDAgeAMBEG\nA1UdEQQKMAiCBmNsaWVudDANBgkqhkiG9w0BAQsFAAOCAQEAKvo5Bh/x+MHxJ2rR\naYu5uZyDhw/DV7txnoDD+EvdFfuaitHrAl+4qiAaBNIJLJd8FUNcHylHOMQAz1jA\nhx1Vd54/k7naTpJ38A1W2XUamxndN1UQJK8joswg39lir7gRyUmNM79M6NOA5z+P\nx/zjMM4COEjdTKQngRN2I2fApYDL3dOnAo+7DIy2cZJu4AcPy8yOG55wwQ6wCH3q\nR5RE2IaNF4nOL5OwQAxfBI5uw7gO1AhSDjKhrB0vJXL4j1bKdZvFGtqWvpypU0wl\nC9i8ER0/MZQz2mDFfDihCkO2ECyRzKl653LSMccPOaYlz5VvKF/+f4/Z36eFyDgo\nYnt1Xw==\n-----END CERTIFICATE-----\n\n")
fout.close()

filepath = "C:/tempOVPN/client.key"
fout = open(filepath, "w+")
fout.write("-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJCe+9Wjq5Mk7q\nwtsupFWHOI9bHuajQOCoD3xfdv3nyNvHAiV0FExsZmc2XP9C7HkuepxvkS+a7u7y\ndZOuWG3fVHXz48umcYsQ4bDcRQgnjfNCct0sV9ZEnVQgMrDQq81v0n8PQTo5mVuF\nZfgE1enlZcle6dfIgUDxIi0QGwU5vKnohtq1a5czHH9ZGVcMjd+8XiK2FUtX3KMM\nohuSx9kwJNF1AijrNeZGzkrp9KxyIzb9Llrv/2/8BFFmoZoqFwyJCInNARu4YJID\nHiRZEaJA22GNq+3q38EunJwmh8hm39v3TVefLk7BkswOCE6cABJ4M+E0GjNAHY5B\nlILRwO8ZAgMBAAECggEAUfo/TFNSxtoA3DIA9pAEYFNsAZgkLEX0VhOaf8Mh3jZF\niG0ToLX2Q+2uALkDTeLnt1BAIi5t3xu3TB8tzVY512u1fXJSRNjgAE5HtEph/N8h\nZuM31TEsKmaNO2PwPecQqpyHO0051Or4KvldnXstGWHcL1vjpdsvBCUPgxXBl7zh\njkJAK85Tqs6uN39W62X6diNH9l95MnNCl6Z2HrT351mu5olP2wxAV7YipjguVWsF\nZjQnxqnzF1k5SnVP256AIaoPNsZNcul2sNv/uPKQG2FxPUwAk4kCN63SlowuU8uo\no58ygC0z61m1ApDglEo/NEaJwC+ge0wUBSJ4hVGEAQKBgQDygX7pHe1QZJYo+H8W\nvDZKdf/5Jkvy9Zor7dq2/996uhxybR36WBAit9Ry52jHXCrMdOFTEcGURs+fnuF9\nfcOEx05qylw6YdL+RjkZ1akTLFbcCaJ0+dCjV3aXqgFjwmBtZ9m7oxkL5wZBj1q+\nLSj4sxkDdmMFx+QtaTqdwez14QKBgQDUOb3QbWWHSM/lvbMO0c8KJHp1fchaZW+E\nT9qI40S6TirMFKYpPsVmo32OoNkF3tF/LIPrwRqNB4JR39PI+oKMZD/XWTBkfNe+\noaAGhOtTq1Iqj9Ox4tP5xubFkLrrD51kX/B3UhvjoQ6WaLQzijXTn+HA5m8YhSuN\nW8uHamQwOQKBgQDUeHkFLl9abIbT5sUS2lyG6NQZK2CnwmAN1Rwn8g+Pq7h42PnG\n+ZlJkd7lvbKj2TD2agFxksEUdOY8aCwQkzvW/fjKv/oGfOcsOcKVzAmS7uo7Z4Wb\nD7WpJ1yUjTMigI+Ty8WNDN1I/GUS09MhhNe87s4fpn4j9nsI/oWI6vv+gQKBgBsZ\nBFOIpF4RvfnnruVFhNoWrkwyM1LXgNW0HIM+AZiIQo6sVEsP6MSiO3Xqs8s8GCPU\n20NO9MTtIIazvab71Y93fW9RxAwQpLuCh5xNfH7CcUtd8fnUrJxH+U9gm31IESCv\n31Rc95KpDePGmru5+gV7dKfcxHKtDlKOVf6EZyaBAoGBAOwmyHeJpsxUvgzm/Qua\nNiJCy7lM7xI9vyvleCvkd0dn5lqgukvFgYJjs46kAcSPhdnLqpjH44kLSrLIZtDu\n5kFk6QsZDAhNLiuvxKxh9T0oMV65EDO412eaRfuZY8BnZfoj9QXccUBTcbGRi5Ig\nDMatiJ9uu+9x58ykQSshpPxK\n-----END PRIVATE KEY-----\n")
fout.close()

filepath = "C:/Program Files/OpenVPN/config/client101.ovpn"
fout = open(filepath, "w+")

if b==0:
    fout.write("client\ndev tun\nproto udp\nremote "+ipaddress+" 1194\nresolv-retry infinite\nnobind\npersist-key\npersist-tun\nca C:\\\\temp\\\\ca.crt\ncert C:\\\\temp\\\\client.crt\nkey C:\\\\temp\\\\client.key\nremote-cert-tls server\ncipher AES-256-CBC\nverb 3")
    fout.close()
if b>0:
    fout.write("client\ndev tun\nproto udp\nremote "+ipaddresses[b-1]+" 1194\nresolv-retry infinite\nnobind\npersist-key\npersist-tun\nca C:\\\\temp\\\\ca.crt\ncert C:\\\\temp\\\\client.crt\nkey C:\\\\temp\\\\client.key\nremote-cert-tls server\ncipher AES-256-CBC\nverb 3")
    fout.close()

os.system('openvpn-gui.exe --connect "client101.ovpn"')


#disconnect and clean up
exitx = input("enter to disconnect and clean up: ")

os.system('taskkill.exe /F /IM openvpn.exe')

ec2.instances.filter(InstanceIds = ids).terminate()
print("waiting for termination...")  
instanceS[0].wait_until_terminated()
print("cleaning up...")    
for q in range (b):
    instance[q].wait_until_terminated()
  
ec2 = boto3.client('ec2',
                    region_name = REGION_ID)    
response = ec2.delete_key_pair(
    KeyName='string',
)
response = ec2.delete_security_group(GroupName='new')
print("done")
