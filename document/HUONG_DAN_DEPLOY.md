# Hướng Dẫn Deploy Web Fashion Store Lên Internet

## 📋 Mục Lục
1. [Chuẩn Bị](#chuẩn-bị)
2. [Cấu Hình Database](#cấu-hình-database)
3. [Build Ứng Dụng](#build-ứng-dụng)
4. [Các Phương Thức Deploy](#các-phương-thức-deploy)
5. [Cấu Hình Production](#cấu-hình-production)
6. [Bảo Mật](#bảo-mật)
7. [Kiểm Tra và Monitoring](#kiểm-tra-và-monitoring)

---

## 🎯 Chuẩn Bị

### Yêu Cầu Hệ Thống
- **Java**: JDK 21 hoặc cao hơn
- **Maven**: 3.6+ (hoặc sử dụng Maven Wrapper có sẵn)
- **Database**: SQL Server (có thể dùng Azure SQL, AWS RDS, hoặc VPS)
- **Bộ nhớ**: Tối thiểu 512MB RAM cho ứng dụng

### Kiểm Tra Trước Khi Deploy
```bash
# Kiểm tra Java version
java -version
s
# Kiểm tra Maven
mvn -version

# Build thử trên local
mvn clean package -DskipTests
```

---

## 🗄️ Cấu Hình Database

### Tùy Chọn 1: Azure SQL Database (Khuyến nghị cho Production)

1. **Tạo Azure SQL Database:**
   - Đăng nhập [Azure Portal](https://portal.azure.com)
   - Tạo SQL Database mới
   - Lưu lại connection string

2. **Cấu hình Firewall:**
   - Thêm IP của server deploy vào firewall rules
   - Hoặc bật "Allow Azure services" nếu deploy trên Azure

### Tùy Chọn 2: AWS RDS SQL Server

1. **Tạo RDS Instance:**
   - Vào AWS Console → RDS
   - Tạo SQL Server instance
   - Lưu endpoint và credentials

2. **Cấu hình Security Group:**
   - Mở port 1433 cho SQL Server
   - Chỉ cho phép IP của application server

### Tùy Chọn 3: VPS với SQL Server

1. **Cài đặt SQL Server trên VPS:**
   ```bash
   # Trên Ubuntu/Debian
   sudo apt update
   sudo apt install mssql-server
   ```

2. **Cấu hình SQL Server:**
   - Mở port 1433
   - Tạo database và user
   - Backup database từ local và restore lên server

---

## 🔨 Build Ứng Dụng

### Bước 1: Cập nhật application.properties cho Production

Tạo file `application-prod.properties` trong `backend/src/main/resources/`:

```properties
spring.application.name=FashionStore

# Database Production
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://YOUR_DB_SERVER:1433;databaseName=QL_WebBanQuanAoCaNhan;encrypt=true;trustServerCertificate=true
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl

# View
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

# Server
server.port=8080

# Mail SMTP (Sử dụng biến môi trường)
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Logging
logging.level.root=INFO
logging.level.com.dreayrt=DEBUG
```

### Bước 2: Build JAR File

```bash
# Từ thư mục gốc của project
cd backend

# Build với profile production
mvn clean package -Pprod -DskipTests

# File JAR sẽ được tạo tại:
# backend/target/fashion-store-backend-0.0.1-SNAPSHOT.jar
```

---

## 🚀 Các Phương Thức Deploy

### Phương Thức 1: Deploy Lên VPS (DigitalOcean, Linode, Vultr, etc.)

#### Bước 1: Chuẩn Bị VPS
- **OS**: Ubuntu 22.04 LTS (khuyến nghị)
- **RAM**: Tối thiểu 1GB (2GB khuyến nghị)
- **CPU**: 1 core trở lên

#### Bước 2: Cài Đặt Java và Maven
```bash
# SSH vào VPS
ssh root@your-server-ip

# Cập nhật hệ thống
sudo apt update && sudo apt upgrade -y

# Cài đặt Java 21
sudo apt install openjdk-21-jdk -y

# Kiểm tra
java -version
```

#### Bước 3: Upload JAR File
```bash
# Từ máy local, upload file JAR
scp backend/target/fashion-store-backend-0.0.1-SNAPSHOT.jar root@your-server-ip:/opt/fashion-store/

# Hoặc sử dụng Git
git clone your-repo-url
cd fashion-store
mvn clean package -DskipTests
```

#### Bước 4: Tạo Systemd Service
```bash
# Tạo file service
sudo nano /etc/systemd/system/fashion-store.service
```

Nội dung file:
```ini
[Unit]
Description=Fashion Store Spring Boot Application
After=network.target

[Service]
Type=simple
User=www-data
WorkingDirectory=/opt/fashion-store
ExecStart=/usr/bin/java -jar -Dspring.profiles.active=prod /opt/fashion-store/fashion-store-backend-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal
SyslogIdentifier=fashion-store

# Environment variables
Environment="MAIL_HOST=smtp.gmail.com"
Environment="MAIL_PORT=587"
Environment="MAIL_USERNAME=your-email@gmail.com"
Environment="MAIL_PASSWORD=your-app-password"

[Install]
WantedBy=multi-user.target
```

#### Bước 5: Khởi Động Service
```bash
# Reload systemd
sudo systemctl daemon-reload

# Bật service tự động khởi động
sudo systemctl enable fashion-store

# Khởi động service
sudo systemctl start fashion-store

# Kiểm tra status
sudo systemctl status fashion-store

# Xem logs
sudo journalctl -u fashion-store -f
```

#### Bước 6: Cấu Hình Nginx (Reverse Proxy)
```bash
# Cài đặt Nginx
sudo apt install nginx -y

# Tạo file config
sudo nano /etc/nginx/sites-available/fashion-store
```

Nội dung config:
```nginx
server {
    listen 80;
    server_name your-domain.com www.your-domain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

```bash
# Tạo symbolic link
sudo ln -s /etc/nginx/sites-available/fashion-store /etc/nginx/sites-enabled/

# Test config
sudo nginx -t

# Restart Nginx
sudo systemctl restart nginx
```

#### Bước 7: Cài Đặt SSL với Let's Encrypt
```bash
# Cài đặt Certbot
sudo apt install certbot python3-certbot-nginx -y

# Lấy SSL certificate
sudo certbot --nginx -d your-domain.com -d www.your-domain.com

# Certbot sẽ tự động cấu hình Nginx và renew certificate
```

---

### Phương Thức 2: Deploy Lên Railway

1. **Đăng ký tài khoản:** [railway.app](https://railway.app)

2. **Kết nối GitHub Repository:**
   - Tạo project mới trên Railway
   - Connect với GitHub repo của bạn

3. **Cấu hình:**
   - **Build Command**: `cd backend && mvn clean package -DskipTests`
   - **Start Command**: `java -jar backend/target/fashion-store-backend-0.0.1-SNAPSHOT.jar`
   - **Port**: Railway tự động set biến `PORT`

4. **Thêm Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=prod
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=587
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```

5. **Thêm Database:**
   - Railway có thể tạo PostgreSQL/MySQL
   - Nếu cần SQL Server, dùng Azure SQL hoặc AWS RDS

---

### Phương Thức 3: Deploy Lên Heroku

1. **Cài đặt Heroku CLI:**
   ```bash
   # Windows
   # Download từ https://devcenter.heroku.com/articles/heroku-cli
   ```

2. **Tạo Procfile:**
   Tạo file `Procfile` ở thư mục gốc:
   ```
   web: java -jar backend/target/fashion-store-backend-0.0.1-SNAPSHOT.jar
   ```

3. **Tạo app.json:**
   ```json
   {
     "name": "Fashion Store",
     "description": "Fashion Store E-commerce",
     "repository": "https://github.com/your-username/fashion-store",
     "buildpacks": [
       {
         "url": "heroku/java"
       }
     ]
   }
   ```

4. **Deploy:**
   ```bash
   heroku login
   heroku create fashion-store-app
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   heroku config:set MAIL_HOST=smtp.gmail.com
   # ... thêm các biến môi trường khác
   git push heroku main
   ```

---

### Phương Thức 4: Deploy Lên AWS EC2

1. **Tạo EC2 Instance:**
   - Chọn Ubuntu 22.04 LTS
   - Security Group: Mở port 22 (SSH), 80 (HTTP), 443 (HTTPS), 8080 (App)

2. **Cài đặt giống như VPS:**
   - Làm theo các bước từ "Phương Thức 1"

3. **Sử dụng AWS RDS cho Database:**
   - Tạo RDS SQL Server instance
   - Cấu hình Security Group cho phép EC2 instance truy cập

---

### Phương Thức 5: Deploy Lên Azure App Service

1. **Tạo App Service:**
   - Vào Azure Portal → App Services → Create
   - Chọn Java 21, Linux
   - Chọn pricing plan

2. **Cấu hình Deployment:**
   - **Deployment Center**: Kết nối với GitHub
   - **Build**: Maven
   - **Startup Command**: `java -jar backend/target/fashion-store-backend-0.0.1-SNAPSHOT.jar`

3. **Cấu hình Application Settings:**
   - Thêm các biến môi trường trong Configuration → Application settings

4. **Kết nối Azure SQL:**
   - Tạo Azure SQL Database
   - Cấu hình connection string trong Application settings

---

## ⚙️ Cấu Hình Production

### 1. Cập Nhật application.properties

Sử dụng biến môi trường thay vì hardcode:

```properties
# Database
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

# Mail
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

### 2. Tối Ưu JVM

Thêm vào startup command:
```bash
java -Xms512m -Xmx1024m \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -jar fashion-store-backend-0.0.1-SNAPSHOT.jar
```

### 3. Cấu Hình Logging

Tạo `logback-spring.xml` trong `backend/src/main/resources/`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <springProfile name="prod">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>logs/fashion-store.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>logs/fashion-store-%d{yyyy-MM-dd}.log</fileNamePattern>
                <maxHistory>30</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
            </encoder>
        </appender>
        <root level="INFO">
            <appender-ref ref="FILE" />
        </root>
    </springProfile>
</configuration>
```

---

## 🔒 Bảo Mật

### 1. Ẩn Thông Tin Nhạy Cảm
- ✅ **KHÔNG** commit `application.properties` có password
- ✅ Sử dụng biến môi trường
- ✅ Sử dụng secrets management (AWS Secrets Manager, Azure Key Vault)

### 2. Cấu Hình Spring Security
- Kiểm tra và cập nhật security config
- Sử dụng HTTPS
- Cấu hình CORS đúng cách

### 3. Database Security
- Sử dụng strong password
- Giới hạn IP truy cập database
- Enable encryption

### 4. Firewall
```bash
# Chỉ mở các port cần thiết
sudo ufw allow 22/tcp   # SSH
sudo ufw allow 80/tcp   # HTTP
sudo ufw allow 443/tcp  # HTTPS
sudo ufw enable
```

---

## 📊 Kiểm Tra và Monitoring

### 1. Health Check
Spring Boot Actuator đã được cấu hình, truy cập:
- `http://your-domain.com/actuator/health`
- `http://your-domain.com/actuator/info`

### 2. Monitoring với PM2 (Tùy chọn)
```bash
# Cài đặt PM2
npm install -g pm2

# Chạy ứng dụng với PM2
pm2 start java --name "fashion-store" -- -jar fashion-store-backend-0.0.1-SNAPSHOT.jar

# Các lệnh hữu ích
pm2 status
pm2 logs fashion-store
pm2 restart fashion-store
pm2 save
pm2 startup
```

### 3. Log Monitoring
```bash
# Xem logs real-time
sudo journalctl -u fashion-store -f

# Xem logs của Nginx
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log
```

---

## 🐛 Troubleshooting

### Ứng dụng không khởi động
```bash
# Kiểm tra logs
sudo journalctl -u fashion-store -n 100

# Kiểm tra port đã được sử dụng chưa
sudo netstat -tulpn | grep 8080

# Kiểm tra Java
java -version
```

### Database connection failed
- Kiểm tra firewall rules
- Kiểm tra connection string
- Kiểm tra credentials
- Test connection từ server: `telnet db-host 1433`

### Ứng dụng chạy chậm
- Tăng JVM heap size
- Kiểm tra database query performance
- Enable caching nếu có thể

---

## 📝 Checklist Trước Khi Deploy

- [ ] Đã test build thành công trên local
- [ ] Đã cấu hình database production
- [ ] Đã cập nhật tất cả biến môi trường
- [ ] Đã ẩn thông tin nhạy cảm (password, API keys)
- [ ] Đã cấu hình domain name (nếu có)
- [ ] Đã cài đặt SSL certificate
- [ ] Đã cấu hình firewall
- [ ] Đã test ứng dụng sau khi deploy
- [ ] Đã setup backup cho database
- [ ] Đã cấu hình monitoring/logging

---

## 🔗 Tài Liệu Tham Khảo

- [Spring Boot Deployment](https://spring.io/guides/gs/spring-boot-for-azure/)
- [Nginx Configuration](https://nginx.org/en/docs/)
- [Let's Encrypt](https://letsencrypt.org/)
- [Railway Docs](https://docs.railway.app/)
- [Heroku Java Guide](https://devcenter.heroku.com/articles/deploying-java)

---

## 💡 Lưu Ý Quan Trọng

1. **Backup Database**: Luôn backup database trước khi deploy
2. **Test trên Staging**: Nên có môi trường staging để test trước
3. **Version Control**: Luôn commit code vào Git trước khi deploy
4. **Monitoring**: Setup monitoring để theo dõi ứng dụng
5. **Security Updates**: Thường xuyên cập nhật dependencies và OS

---

**Chúc bạn deploy thành công! 🚀**

