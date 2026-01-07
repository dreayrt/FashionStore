## Multi-module build (frontend + backend)

### Cấu trúc mới
- `pom.xml` (root): packaging `pom`, khai báo modules `frontend`, `backend`.
- `frontend/`: chứa mã giao diện. Maven copy JSP vào `dist/WEB-INF/jsp`, asset vào `dist/static`.
- `backend/`: Spring Boot app, phụ thuộc DB, copy output của `frontend` vào classpath khi build.

### Cách build
1) Từ thư mục gốc:
   - `mvn clean package`
   - Trình tự: Maven chạy module `frontend` (tạo `frontend/target/dist`), sau đó module `backend` copy `dist` vào `target/classes` (JSP dưới `WEB-INF/jsp`, tài nguyên tĩnh dưới `static`).

### Chạy ứng dụng
- Từ gốc: `mvn -pl backend spring-boot:run`
- Hoặc chạy JAR: `java -jar backend/target/fashion-store-backend-*.jar`

### Lưu ý JSP
- View resolver cần prefix/suffix (ví dụ trong `application.properties`):
  ```
  spring.mvc.view.prefix=/WEB-INF/jsp/
  spring.mvc.view.suffix=.jsp
  ```
- Các asset (css/js/images) được map tĩnh từ `static` -> truy cập `/...`.

### Tùy biến
- Nếu bạn build frontend bằng công cụ khác (npm/vite/webpack), thay đổi bước copy trong `frontend/pom.xml` để trỏ tới thư mục build tương ứng.
- Nếu muốn bỏ JSP, chỉ cần đặt file HTML vào `frontend/src`/`public` và controller trả về static index. Frontend plugin sẽ vẫn copy vào `static`.

