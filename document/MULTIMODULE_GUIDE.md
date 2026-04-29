## Multi-module & thực tế hiện tại

Project này hiện đang chạy **một module backend duy nhất** trong repo `fashion-store`:

- `backend/`: Spring Boot + JSP, serve luôn giao diện người dùng và static (ảnh, logo…) từ `src/main/webapp`.
- Không còn thư mục `frontend/` trong repo này; nếu có project frontend riêng thì đó là repo khác (vd. `fashion-store-frontend`), không tham gia build Maven ở đây.

Nếu sau này bạn muốn quay lại mô hình multi‑module (backend + frontend chung một repo) thì có thể tham khảo phần “Gợi ý cấu trúc multi‑module” bên dưới.

---

## Gợi ý cấu trúc multi‑module (tùy chọn trong tương lai)

### Cấu trúc đề xuất
- `pom.xml` (root): packaging `pom`, khai báo modules `frontend`, `backend`.
- `frontend/`: mã giao diện (React/Vite hoặc HTML/CSS/JS thuần).
- `backend/`: Spring Boot app, phụ thuộc DB, có thể copy output của `frontend` vào `webapp` hoặc `static` khi build.

### Cách build (nếu có nhiều module)
1) Từ thư mục gốc:
   - `mvn clean package`
   - Trình tự điển hình: Maven build module `frontend` (tạo thư mục build, ví dụ `frontend/dist`), sau đó module `backend` copy output cần thiết vào:
     - `backend/src/main/webapp/WEB-INF/jsp` (nếu dùng JSP),
     - hoặc `backend/src/main/resources/static` (nếu phục vụ static SPA).

### Chạy ứng dụng (hiện tại)
- Từ gốc: `mvn -pl backend spring-boot:run`
- Hoặc chạy JAR: `java -jar backend/target/*.jar`

### Lưu ý JSP (hiện tại)
- View resolver (ví dụ trong `backend/src/main/resources/application.properties`):
  ```
  spring.mvc.view.prefix=/WEB-INF/jsp/
  spring.mvc.view.suffix=.jsp
  ```
- Asset tĩnh cho JSP được đặt trực tiếp trong `backend/src/main/webapp`, ví dụ:
  - `backend/src/main/webapp/Logo/...`  → truy cập `/Logo/...`
  - `backend/src/main/webapp/imageProduct/...` → truy cập `/imageProduct/...`

### Tùy biến cho tương lai
- Nếu sau này dùng bundler (npm/vite/webpack) cho frontend:
  - Build frontend ra `frontend/dist`.
  - Thêm bước Maven ở module `backend` để copy `dist` vào `webapp` hoặc `static`.
- Nếu tách hoàn toàn frontend (SPA riêng + API backend):
  - Không cần multi‑module; chỉ cần để hai repo độc lập và cấu hình CORS / reverse proxy (Nginx) phù hợp.
