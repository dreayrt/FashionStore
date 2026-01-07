<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Đăng nhập</title>
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
        max-width: 420px;
        width: 100%;
        padding: 32px;
      }

      .brand {
        font-family: "Playfair Display", serif;
        font-size: 1.6rem;
        letter-spacing: 1px;
        color: var(--text-strong);
      }

      .lead {
        color: var(--text-base);
      }

      .form-label {
        font-weight: 600;
        color: var(--text-strong);
      }

      .form-control {
        border-radius: 12px;
        border: 1px solid var(--card-border);
        padding: 12px 14px;
      }

      .form-control:focus {
        border-color: var(--accent);
        box-shadow: 0 0 0 0.2rem rgba(201, 162, 77, 0.25);
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

      .helper-link {
        color: var(--text-base);
      }

      .helper-link:hover {
        color: var(--accent);
      }
    </style>
  </head>
  <body>
    <main class="auth-card">
      <div class="d-flex justify-content-between align-items-start mb-3">
        <div>
          <div class="brand">Dreayrt Fashion</div>
          <p class="mb-0 text-muted">Đăng nhập để tiếp tục</p>
        </div>
        <a class="helper-link small" href="/">← Trang chủ</a>
      </div>

      <jsp:useBean id="loginRequest" class="com.dreayrt.fashion_store.DTOs.LoginRequest" scope="request" />
      <form:form action="/pages/login" method="post" modelAttribute="loginRequest" class="mt-3">
        <div class="mb-3">
            <c:if test="${loginError !=null}">
                <div class="alert alert-danger">
                    ${loginError}
                </div>
            </c:if>
          <label for="email" class="form-label">Tên Đăng Nhập</label>
          <form:input
            class="form-control"
            id="username"
            path="username"
            placeholder="username"
            value="${savedUsername}"
          />
            <form:errors path="username" cssClass="text-danger"/>
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Mật khẩu</label>
            <form:input
            type="password"
            class="form-control"
            id="password"
            path="password"
            placeholder="••••••••"

          />
            <form:errors path="password" cssClass="text-danger"/>

        </div>
        <div class="d-flex justify-content-between align-items-center mb-3">
          <div class="form-check">
            <input
              class="form-check-input"
              type="checkbox"
              value="true"
              id="remember"
              name="remember"
            />
            <label class="form-check-label" for="remember">Nhớ tôi</label>
          </div>
          <a class="helper-link small" href="/pages/forgotPassword">Quên mật khẩu?</a>
        </div>
        <div class="d-grid">
          <button type="submit" class="btn btn-primary">Đăng nhập</button>
        </div>
      </form:form>

      <p class="text-center mt-4 mb-0">
        Chưa có tài khoản?
        <a class="helper-link fw-semibold" href="/pages/register">Đăng ký ngay</a>
      </p>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
