# 创建keystore和密钥对
keytool.exe -genkey -alias login -keyalg RSA -keystore keystore.jks -keysize 2048

# 查看私钥
keytool.exe -list  -rfc -keystore keystore.jks -storepass boying

# 导出证书
keytool.exe -export -alias login -file login.crt -keystore keystore.jks -storepass boying

# 查看公钥
