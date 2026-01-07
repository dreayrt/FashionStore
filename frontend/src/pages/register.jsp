
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Đăng ký</title>
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
        max-width: 520px;
        width: 100%;
        padding: 32px;
      }

      .brand {
        font-family: "Playfair Display", serif;
        font-size: 1.6rem;
        letter-spacing: 1px;
        color: var(--text-strong);
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
          <p class="mb-0 text-muted">Tạo tài khoản mới</p>
        </div>
        <a class="helper-link small" href="/">← Trang chủ</a>
      </div>
      <jsp:useBean id="RegisterRequest"  class="com.dreayrt.fashion_store.DTOs.RegisterRequest" scope="request"/>
      <form:form action="/pages/register" method="post" modelAttribute="RegisterRequest" class="mt-3">
        <div class="row g-3">

          <c:if test="${RegisterSuccess}">
            <div class="alert alert-success" role="alert">
              Đăng ký tài khoản thành công
            </div>
              <script>
                  setTimeout(() => {
                      document.getElementById("successAlert").classList.add("fade");
                  }, 700);

                  setTimeout(() => {
                      window.location.href = "<c:url value='/pages/login'/>";
                  }, 1500);
              </script>

          </c:if>
          <c:if test="${RegisterError!=null}">
            <div class="alert alert-danger" role="alert">
              ${RegisterError}
            </div>
          </c:if>
          <div class="col-12">
            <label class="form-label" for="username">Tên đăng nhập</label>
            <form:input class="form-control" id="username" path="username" placeholder="username"  />
            <form:errors path="username" cssClass="text-danger small d-block mt-1"/>
          </div>

          <div class="col-md-6">
            <label class="form-label" for="password">Mật khẩu</label>
            <form:input type="password" class="form-control" id="password" path="password" placeholder="••••••••" />
            <form:errors path="password" cssClass="text-danger small d-block mt-1"/>
          </div>
          <div class="col-md-6">
            <label class="form-label" for="confirmPassword">Nhập lại mật khẩu</label>
            <form:input type="password" class="form-control" id="confirmPassword" path="confirmPassword" placeholder="••••••••"  />
            <form:errors path="confirmPassword" cssClass="text-danger small d-block mt-1"/>
          </div>

          <div class="col-md-6">
            <label class="form-label" for="email">Email</label>
            <form:input type="email" class="form-control" id="email" path="email" placeholder="you@example.com"  />
            <form:errors path="email" cssClass="text-danger small d-block mt-1"/>
          </div>
          <div class="col-md-6">
            <label class="form-label" for="phone">Số điện thoại</label>
            <form:input class="form-control" id="phone" path="phone" placeholder="0123456789"  />
            <form:errors path="phone" cssClass="text-danger small d-block mt-1"/>
          </div>

          <div class="col-12">
            <label class="form-label" for="diaChi">Địa chỉ</label>
            <form:input class="form-control" id="diaChi" path="address" placeholder="Số nhà, đường, quận/huyện, tỉnh/thành"  />
            <form:errors path="address" cssClass="text-danger small d-block mt-1"/>
          </div>
        </div>

        <div class="d-grid mt-4">
          <button type="submit" class="btn btn-primary">Đăng ký</button>
        </div>
      </form:form>
      <p class="text-center mt-4 mb-0">
        Đã có tài khoản?
        <a class="helper-link fw-semibold" href="/pages/login">Đăng nhập</a>
      </p>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>

