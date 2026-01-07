## Project Structure Guide (fashion-store)

Stack: SQL Server (database), Spring Boot (backend), HTML/CSS/JavaScript (frontend).

### Goals

- Rõ ràng 3 tầng: `frontend/`, `backend/`, `infrastructure/`.
- Thuận tiện mở rộng test, CI/CD, scripts.
- Tách cấu hình theo môi trường với `.env` và profiles Spring.

### Proposed Layout

```
fashion-store/
├─ README.md
├─ .gitignore
├─ .env.example                 # Biến môi trường mẫu (backend, DB)
├─ docker-compose.yml           # Tùy chọn: DB + app (nếu dùng Docker)
│
├─ frontend/
│  ├─ public/                   # Asset tĩnh (favicon, logo, hình ảnh)
│  ├─ src/
│  │  ├─ assets/                # CSS/JS vendor, fonts, images dùng chung
│  │  ├─ styles/                # CSS/SCSS tách module (layout, components)
│  │  ├─ scripts/               # JS thuần (services, utils, components)
│  │  │  ├─ api/                # Gọi API tới backend (fetch/axios)
│  │  │  ├─ modules/            # Tính năng: cart, auth, product
│  │  │  └─ main.js             # Entry JS
│  │  ├─ pages/                 # HTML page-level (home.html, product.html,…)
│  │  └─ index.html             # Trang chính
│  ├─ tests/                    # Test E2E/UI (Playwright/Cypress nếu cần)
│  └─ package.json              # Nếu dùng tooling (vite/parcel) hoặc lint
│
├─ backend/
│  ├─ build.gradle | pom.xml    # Chọn Gradle/Maven
│  ├─ settings.gradle           # (Gradle) nếu cần
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ java/com/example/fashionstore/
│  │  │  │  ├─ FashionStoreApplication.java
│  │  │  │  ├─ config/          # CORS, Security, Swagger, Datasource
│  │  │  │  ├─ controller/      # REST controllers
│  │  │  │  ├─ dto/             # Request/response DTO
│  │  │  │  ├─ entity/          # JPA entities
│  │  │  │  ├─ repository/      # Spring Data JPA repositories
│  │  │  │  ├─ service/         # Business logic
│  │  │  │  ├─ mapper/          # MapStruct hoặc converter
│  │  │  │  └─ util/            # Helpers, constants
│  │  │  └─ resources/
│  │  │     ├─ application.yml  # Cấu hình mặc định
│  │  │     ├─ application-dev.yml
│  │  │     └─ application-prod.yml
│  │  └─ test/
│  │     ├─ java/...            # Unit & integration tests
│  │     └─ resources/          # Test data, schema test
│  └─ scripts/                  # Flyway/Liquibase, seed, maintenance
│
├─ infrastructure/
│  ├─ db/
│  │  ├─ sqlserver-init.sql     # Tạo DB, user, seed tối thiểu
│  │  ├─ migrations/            # Flyway/Liquibase scripts
│  │  └─ backups/               # (tùy chọn) dump/backup
│  └─ ops/
│     ├─ nginx.conf             # (tùy chọn) reverse proxy
│     └─ deploy/                # CI/CD, k8s manifests, ansible scripts
│
└─ scripts/
   ├─ dev.ps1 | dev.sh          # Chạy frontend + backend (hot reload)
   ├─ lint.ps1 | lint.sh        # Lint/test nhanh
   └─ tools/                    # Tiện ích: format, check env
```

### Environment Variables (`.env.example`)

- `SPRING_DATASOURCE_URL=jdbc:sqlserver://localhost:1433;databaseName=fashion_store`
- `SPRING_DATASOURCE_USERNAME=...`
- `SPRING_DATASOURCE_PASSWORD=...`
- `SPRING_PROFILES_ACTIVE=dev`

### Backend Notes (Spring Boot)

- Bật CORS cho origin frontend (ví dụ `http://localhost:3000`).
- Dùng profiles (`application-dev.yml`, `application-prod.yml`) để tách cấu hình.
- Quản lý schema qua Flyway/Liquibase trong `backend/scripts` hoặc `infrastructure/db/migrations`.

### Database Notes (SQL Server)

- Docker: dùng `mcr.microsoft.com/mssql/server` trong `docker-compose.yml`, mount `sqlserver-init.sql`.
- On-prem: chạy script `infrastructure/db/sqlserver-init.sql` để tạo DB/user/schema.

### Frontend Notes (HTML/CSS/JS)

- Giữ JS modular: `scripts/modules/` cho tính năng, `scripts/api/` cho gọi backend.
- Nếu không dùng bundler, build artifacts có thể đặt trong `frontend/public/`.

### Testing

- Backend: JUnit/MockMvc + integration tests với profile `test`.
- Frontend: E2E nhẹ (Playwright/Cypress) đặt trong `frontend/tests/`.

### CI/CD (tùy chọn)

- Lint + test backend và frontend.
- Build Spring Boot jar (`bootJar`), publish image nếu dùng Docker.

### Quick Start Checklist

1. Tạo thư mục chính: `frontend/`, `backend/`, `infrastructure/db/`.
2. Thêm `.env.example`, `README.md`, `.gitignore`.
3. Khởi tạo Spring Boot (Web, JPA, SQL Server driver).
4. Tạo `application.yml` + profile `dev` trỏ SQL Server local.
5. Viết `sqlserver-init.sql` để tạo DB/user/schema gốc.
6. Khung frontend: `frontend/src/index.html`, `styles/`, `scripts/main.js`.
7. (Tùy chọn) `docker-compose.yml` để chạy SQL Server + backend.
