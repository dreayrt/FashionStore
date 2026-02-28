## 🚀 Deploy Spring Boot lên VPS – Production chuẩn

### 📌 Mục tiêu

Triển khai Spring Boot theo kiến trúc production chuẩn:

- **Internet**  
  ↓  
  **Nginx** (80/443)  
  ↓  
  **Spring Boot** (`127.0.0.1:8080`)  
  ↓  
  **MySQL**

---

### 1️⃣ SSH vào VPS

Kết nối SSH:

```bash
ssh root@YOUR_SERVER_IP
```

**Cú pháp chung**:

```bash
ssh USER@IP
```

- **ssh**: Secure Shell  
- **root**: user đăng nhập  
- **IP**: địa chỉ VPS

**Ví dụ**:

```bash
ssh root@45.63.64.138
```

---

### 2️⃣ Cập nhật hệ thống

```bash
apt update
apt upgrade -y
```

- **apt update**: Cập nhật danh sách package  
- **apt upgrade -y**: Nâng cấp hệ thống

---

### 3️⃣ Cài Java 17

**Kiểm tra Java**:

```bash
java -version
```

Nếu **chưa có Java 17**:

```bash
apt install openjdk-17-jdk -y
```

Kiểm tra lại:

```bash
java -version
```

---

### 4️⃣ Build file WAR (máy local)

Trong thư mục `backend`:

```bash
mvn clean package
```

File WAR nằm trong:

```text
target/your-app.war
```

---

### 5️⃣ Upload WAR lên VPS

Từ **máy local**:

```bash
scp target/your-app.war root@YOUR_SERVER_IP:/root
```

**Cú pháp**:

```bash
scp SOURCE USER@IP:DESTINATION
```

---

### 6️⃣ Tạo thư mục production

Tạo thư mục chứa app:

```bash
mkdir -p /opt/app
```

- **mkdir**: tạo thư mục  
- **-p**: tạo nếu chưa tồn tại

Di chuyển file WAR:

```bash
mv your-app.war /opt/app/
```

---

### 7️⃣ Tạo systemd service (chuẩn production)

Tạo file service:

```bash
nano /etc/systemd/system/app.service
```

**Nội dung file**:

```ini
[Unit]
Description=Spring Boot Production App
After=network.target

[Service]
User=root
WorkingDirectory=/opt/app
ExecStart=/usr/bin/java -jar /opt/app/your-app.war
SuccessExitStatus=143
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

**Giải thích các dòng quan trọng**:

- **Description**: Tên service  
- **After=network.target**: Chạy sau khi có mạng  
- **User=root**: User chạy app  
- **WorkingDirectory**: Thư mục làm việc  
- **ExecStart**: Lệnh chạy app  
- **Restart=always**: Tự restart khi crash  
- **WantedBy=multi-user.target**: Tự chạy khi reboot

**Kích hoạt service**:

Reload cấu hình systemd:

```bash
systemctl daemon-reload
```

Enable auto start:

```bash
systemctl enable app
```

Start service:

```bash
systemctl start app
```

Kiểm tra trạng thái:

```bash
systemctl status app
```

---

### 8️⃣ Ẩn port 8080 (chuẩn production)

Mở file config:

```bash
nano /opt/app/application.properties
```

Thêm cấu hình:

```properties
server.address=127.0.0.1
```

Restart app:

```bash
systemctl restart app
```

---

### 9️⃣ Cài Nginx (reverse proxy)

Cài Nginx:

```bash
apt install nginx -y
```

Tạo file config:

```bash
nano /etc/nginx/sites-available/app
```

**Nội dung**:

```nginx
server {
    listen 80;
    server_name YOUR_SERVER_IP;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

Enable config:

```bash
ln -s /etc/nginx/sites-available/app /etc/nginx/sites-enabled/
```

Kiểm tra:

```bash
nginx -t
```

Restart:

```bash
systemctl restart nginx
```

---

### 🔟 Cấu hình firewall chuẩn

Cho phép SSH, HTTP, HTTPS:

```bash
ufw allow 22
ufw allow 80
ufw allow 443
ufw enable
```

Xóa rule mở port 8080 (nếu có):

```bash
ufw delete allow 8080
```

Kiểm tra:

```bash
ufw status
```

---

### 1️⃣1️⃣ Cài HTTPS (nếu có domain)

Cài certbot:

```bash
apt install certbot python3-certbot-nginx -y
certbot --nginx
```

Sau đó làm theo hướng dẫn để cấp SSL miễn phí (Let's Encrypt).

---

### 📊 Kiến trúc cuối cùng

- **Internet**  
  ↓  
  **Nginx** (80 / 443)  
  ↓  
  **Spring Boot** (`127.0.0.1:8080`)  
  ↓  
  **MySQL**

---

### 📌 Debug & log

Xem log app:

```bash
journalctl -u app -f
```

Restart app:

```bash
systemctl restart app
```

Restart Nginx:

```bash
systemctl restart nginx
```

---

### ✅ Checklist production

- Java cài đặt  
- WAR upload  
- systemd service  
- Auto start khi reboot  
- Nginx reverse proxy  
- Ẩn port 8080  
- Firewall chuẩn  
- HTTPS (nếu có domain)

🚀 DEPLOY SPRING BOOT LÊN VPS – PRODUCTION CHUẨN
📌 MỤC TIÊU

Triển khai Spring Boot theo kiến trúc production chuẩn:

Internet
   ↓
Nginx (80/443)
   ↓
Spring Boot (127.0.0.1:8080)
   ↓
MySQL
1️⃣ SSH VÀO VPS
ssh root@YOUR_SERVER_IP
📖 Cú pháp
ssh USER@IP
Thành phần	Ý nghĩa
ssh	Secure Shell
root	user đăng nhập
IP	địa chỉ VPS

Ví dụ:

ssh root@45.63.64.138
2️⃣ CẬP NHẬT HỆ THỐNG
apt update
apt upgrade -y
Lệnh	Ý nghĩa
apt update	Cập nhật danh sách package
apt upgrade -y	Nâng cấp hệ thống
3️⃣ CÀI JAVA 17

Kiểm tra:

java -version

Nếu chưa có:

apt install openjdk-17-jdk -y

Kiểm tra lại:

java -version
4️⃣ BUILD FILE WAR (MÁY LOCAL)

Trong thư mục backend:

mvn clean package

File nằm trong:

target/your-app.war
5️⃣ UPLOAD WAR LÊN VPS

Từ máy local:

scp target/your-app.war root@YOUR_SERVER_IP:/root
📖 Cú pháp
scp SOURCE USER@IP:DESTINATION
6️⃣ TẠO THƯ MỤC PRODUCTION
mkdir -p /opt/app
Giải thích:

mkdir → tạo thư mục

-p → tạo nếu chưa tồn tại

Di chuyển file:

mv your-app.war /opt/app/
7️⃣ TẠO SYSTEMD SERVICE (CHUẨN PRODUCTION)
Tạo file service
nano /etc/systemd/system/app.service
Nội dung file
[Unit]
Description=Spring Boot Production App
After=network.target

[Service]
User=root
WorkingDirectory=/opt/app
ExecStart=/usr/bin/java -jar /opt/app/your-app.war
SuccessExitStatus=143
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
Giải thích các dòng quan trọng
Dòng	Ý nghĩa
Description	Tên service
After=network.target	Chạy sau khi có mạng
User=root	User chạy app
WorkingDirectory	Thư mục làm việc
ExecStart	Lệnh chạy app
Restart=always	Tự restart khi crash
WantedBy=multi-user.target	Tự chạy khi reboot
Kích hoạt service

Reload systemd:

systemctl daemon-reload

Enable auto start:

systemctl enable app

Start:

systemctl start app

Kiểm tra:

systemctl status app
8️⃣ ẨN PORT 8080 (CHUẨN PRODUCTION)

Mở file config:

nano /opt/app/application.properties

Thêm:

server.address=127.0.0.1

Restart app:

systemctl restart app
9️⃣ CÀI NGINX (REVERSE PROXY)
apt install nginx -y
Tạo file config
nano /etc/nginx/sites-available/app

Nội dung:

server {
    listen 80;
    server_name YOUR_SERVER_IP;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
Enable config
ln -s /etc/nginx/sites-available/app /etc/nginx/sites-enabled/

Kiểm tra:

nginx -t

Restart:

systemctl restart nginx
🔟 CẤU HÌNH FIREWALL CHUẨN

Cho phép:

ufw allow 22
ufw allow 80
ufw allow 443
ufw enable

Xóa port 8080:

ufw delete allow 8080

Kiểm tra:

ufw status
1️⃣1️⃣ CÀI HTTPS (NẾU CÓ DOMAIN)
apt install certbot python3-certbot-nginx -y
certbot --nginx

Làm theo hướng dẫn để cấp SSL miễn phí.

📊 KIẾN TRÚC CUỐI CÙNG
Internet
   ↓
Nginx (80 / 443)
   ↓
Spring Boot (127.0.0.1:8080)
   ↓
MySQL
📌 DEBUG & LOG
Xem log app
journalctl -u app -f
Restart app
systemctl restart app
Restart nginx
systemctl restart nginx
✅ CHECKLIST PRODUCTION

 Java cài đặt

 WAR upload

 systemd service

 Auto start khi reboot

 Nginx reverse proxy

 Ẩn port 8080

 Firewall chuẩn

 HTTPS (nếu có domain)