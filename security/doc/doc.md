## 创建keystore

keytool -genkey -alias domain_name_login -keyalg RSA -keystore keystore.jks -keysize 1024

## 从jks导出私钥 pem

### 1. 将jks转换成pkcs12格式

keytool -importkeystore -srckeystore keystore.jks -destkeystore mykeystore.p12 -deststoretype PKCS12

### 2. 导出私钥 pem

openssl pkcs12 -in mykeystore.p12  -nodes -nocerts -out key.pem

注：此私钥是PKCS8格式

### REF

http://asaf.github.io/blog/post/export_certificate_and_private_key_from_jks/

## 从jks导出公钥 pem

### 1. 从jks导出证书

keytool -export -alias domain_name_login -file mydomain.crt -keystore keystore.jks

### 2. 导出公钥 pem

openssl x509 -inform der -in mydomain.crt -pubkey -noout

### ref

https://stackoverflow.com/questions/10103657/how-to-print-the-public-key-of-a-certificate-using-keytool

## openssl创建公钥私钥

### 创建私钥

openssl genrsa -out private.pem 1024

注：此私钥是PKCS1格式，JDK中能处理的格式是PKCS8，如果需要Java处理，要将PKCS1转换为PKCS8：

openssl pkcs8 -topk8 -inform PEM -in private.pem -outform pem -nocrypt -out private_pkcs8.pem

### 创建公钥

openssl rsa -pubout -in private.pem -out public.pem

### ref

http://www.jianshu.com/p/08e41304edab

## 私钥格式区别

可以注意到，private.pem与private_pkcs8.pem头尾行是不一样的
private.pem的头尾行是 -----BEGIN PRIVATE KEY-----         -----END PRIVATE KEY-----
private_pkcs8.pem的头尾行是 -----BEGIN RSA PRIVATE KEY-----    -----END RSA PRIVATE KEY-----

BEGIN RSA PRIVATE KEY is PKCS#1 and is just an RSA key. It is essentially just the key object from PKCS#8, but without the version or algorithm identifier in front. 

BEGIN PRIVATE KEY is PKCS#8 and indicates that the key type is included in the key data itself.


## javascript加密

https://github.com/travist/jsencrypt
