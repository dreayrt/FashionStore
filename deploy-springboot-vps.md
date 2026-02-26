# 🚀 Deploy Spring Boot lên VPS (Ubuntu)

---

## 1️⃣ SSH vào VPS

```bash
ssh root@YOUR_SERVER_IP
```

**Ví dụ:**
```bash
ssh root@45.63.64.138
```

---

## 2️⃣ Mở firewall port 8080

```bash
ufw allow 8080
ufw allow 22
ufw enable
ufw status
```

---

## 3️⃣ Kiểm tra Java đã cài chưa

```bash
java -version
```

**Nếu chưa có:**

```bash
apt update
apt install openjdk-17-jdk -y
```

---

## 4️⃣ Build file WAR trên máy local

Trong thư mục `backend`:

```bash
mvn clean package
```

File sẽ nằm trong: `backend/target/`

---

## 5️⃣ Upload WAR lên VPS

**Từ máy local:**

```bash
scp target/fashion-store-backend-0.0.1-SNAPSHOT.war root@YOUR_SERVER_IP:/root
```

**Nếu lỗi connection reset:**

```bash
scp -o ServerAliveInterval=60 target/fashion-store-backend-0.0.1-SNAPSHOT.war root@YOUR_SERVER_IP:/root
```

---

## 6️⃣ Kiểm tra file trên VPS

SSH vào VPS:

```bash
ls
```

Phải thấy: `fashion-store-backend-0.0.1-SNAPSHOT.war`

---

## 7️⃣ Chạy ứng dụng

### Cách tạm thời (không nên dùng lâu dài)
```bash
java -jar fashion-store-backend-0.0.1-SNAPSHOT.war
```

### Cách đúng: chạy nền 24/7
```bash
nohup java -jar fashion-store-backend-0.0.1-SNAPSHOT.war > app.log 2>&1 &
```

---

## 8️⃣ Kiểm tra app đang chạy

```bash
ps aux | grep java
```

Hoặc:

```bash
ss -tulnp | grep 8080
```

---

## 9️⃣ Truy cập trên trình duyệt

```
http://YOUR_SERVER_IP:8080
```

**Ví dụ:** `http://45.63.64.138:8080`

---

## 🔟 Cách kill app

```bash
ps aux | grep java
kill -9 PID
```

---

## 1️⃣1️⃣ Tạo Service để tự chạy sau reboot (PRODUCTION)

### Bước 1: Tạo file

```bash
nano /etc/systemd/system/fashion.service
```

### Bước 2: Nội dung file

```ini
[Unit]
Description=Fashion Spring Boot App
After=network.target

[Service]
User=root
ExecStart=/usr/bin/java -jar /root/fashion-store-backend-0.0.1-SNAPSHOT.war
SuccessExitStatus=143
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

Lưu lại (Ctrl+O, Enter, Ctrl+X).

### Bước 3: Kích hoạt service

```bash
systemctl daemon-reload
systemctl enable fashion
systemctl start fashion
```

### Bước 4: Kiểm tra trạng thái

```bash
systemctl status fashion
```

### Restart app

```bash
systemctl restart fashion
```

---

## 📌 Khi nào app vẫn chạy?

| Hành động              | App có chạy không |
|------------------------|-------------------|
| Tắt máy cá nhân        | ✅ Có             |
| Đóng SSH (không nohup) | ❌ Không          |
| Reboot VPS             | ❌ Không          |
| Dùng systemctl         | ✅ Có             |

---

## 🛠 Debug log

**Xem log (chạy bằng nohup):**
```bash
tail -f app.log
```

**Hoặc nếu dùng service:**
```bash
journalctl -u fashion -f
```

---

## ✅ Checklist hoàn chỉnh

- [ ] VPS chạy
- [ ] Port 8080 mở
- [ ] Java cài đặt
- [ ] WAR upload thành công
- [ ] App chạy
- [ ] Service enable
