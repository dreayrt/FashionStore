<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Truy cập bị từ chối</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Inter:wght@400;500;600&display=swap"
      rel="stylesheet"
    />
    <style>
      :root {
        --bg-main: #f5f5f0;
        --bg-soft: #dadada;
        --text-strong: #1c1c1c;
        --text-base: #4a4a4a;
        --card-border: #e6e6e0;
        --accent: #c9a24d;
        --accent-dark: #a48330;
      }

      * {
        box-sizing: border-box;
      }

      body {
        margin: 0;
        font-family: "Inter", system-ui, -apple-system, sans-serif;
        color: var(--text-base);
        background: radial-gradient(circle at 25% 20%, #ffffff, var(--bg-main)),
          linear-gradient(180deg, #ffffff 0%, var(--bg-main) 70%, #ededeb 100%);
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 24px;
      }

      .auth-card {
        background: #ffffff;
        border: 1px solid var(--card-border);
        border-radius: 18px;
        box-shadow: 0 16px 40px rgba(0, 0, 0, 0.08);
        max-width: 460px;
        width: 100%;
        padding: 32px;
      }

      .brand {
        font-family: "Playfair Display", serif;
        font-size: 1.6rem;
        letter-spacing: 1px;
        color: var(--text-strong);
      }

      .icon-badge {
        width: 64px;
        height: 64px;
        border-radius: 50%;
        background: #fff3f3;
        border: 1px solid #f0caca;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #c44536;
        font-weight: 700;
        font-size: 1.6rem;
        margin-bottom: 16px;
      }

      .btn-primary {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        border: 1px solid var(--accent-dark);
        color: var(--text-strong);
        border-radius: 999px;
        padding: 12px 16px;
        font-weight: 600;
      }

      .btn-primary:hover {
        background: linear-gradient(135deg, var(--accent-dark), #b48b2f);
        color: var(--text-strong);
      }

      .btn-outline {
        border-radius: 999px;
        border: 1px solid var(--card-border);
        color: var(--text-strong);
        padding: 12px 16px;
        font-weight: 600;
        background: #fff;
      }

      .btn-outline:hover {
        border-color: var(--accent);
        color: var(--accent-dark);
      }
    </style>
  </head>
  <body>
    <main class="auth-card">
      <div class="d-flex justify-content-between align-items-start mb-3">
        <div>
          <div class="brand">Dreayrt Fashion</div>
          <p class="mb-0 text-muted">Quyền truy cập bị từ chối</p>
        </div>
        <a class="text-decoration-none small" href="/">← Trang chủ</a>
      </div>

      <div class="text-center  ">
        <div class="icon-badge mx-auto ">!</div>
        <h5 class="mb-2" style="color: var(--text-strong)">
          Bạn không có đủ quyền
        </h5>
        <p class="text-muted mb-4">
          Tài khoản của bạn không đủ quyền để truy cập nội dung này. Nếu bạn
          nghĩ đây là nhầm lẫn, vui lòng đăng nhập bằng tài khoản khác hoặc liên
          hệ quản trị viên để được hỗ trợ.
        </p>

        <div class="d-grid gap-2">
          <a class="btn btn-primary" href="/pages/login"
            >Đăng nhập tài khoản khác</a
          >
          <a class="btn btn-outline" href="/">Quay lại trang chủ</a>
        </div>
      </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>






