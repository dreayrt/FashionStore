<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Quên mật khẩu</title>
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

      .hint-badge {
        background: linear-gradient(145deg, #f9f4e8, #f2e7cf);
        color: var(--text-strong);
        border: 1px solid #e7d9b8;
        border-radius: 14px;
        padding: 12px 14px;
        font-size: 0.95rem;
        box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05);
      }

      .hint-icon {
        width: 34px;
        height: 34px;
        border-radius: 50%;
        background: var(--accent);
        color: var(--text-strong);
        display: inline-flex;
        align-items: center;
        justify-content: center;
        font-weight: 700;
        box-shadow: inset 0 -3px 0 rgba(0, 0, 0, 0.08);
      }
    </style>
  </head>
  <body>
    <main class="auth-card">
      <div class="d-flex justify-content-between align-items-start mb-3">
        <div>
          <div class="brand">Dreayrt Fashion</div>
          <p class="mb-0 text-muted">Khôi phục mật khẩu của bạn</p>
        </div>
        <a class="helper-link small" href="/">← Trang chủ</a>
      </div>

      <c:if test="${not empty successMessage}">
        <div class="alert alert-success" role="alert">${successMessage}</div>
      </c:if>
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">${errorMessage}</div>
      </c:if>

      <form:form action="/pages/forgotPassword" modelAttribute="ForgotPasswordRequest" method="post" class="mt-3">
        <div class="mb-3">
          <label class="form-label" for="email">Email đã đăng ký</label>
          <form:errors path="email" cssClass="text-danger"/>
          <c:if test="${forgotPasswordError !=null}" >
              <div class="alert alert-danger">
                      ${forgotPasswordError}
              </div>
          </c:if>

            <c:if test="${SuccessMessage != null}">
                <div id="successAlert" class="alert alert-success">
                        ${SuccessMessage}
                </div>
                <script>
                    // Mờ dần sau 0.7s
                    setTimeout(function () {
                        document.getElementById("successAlert").classList.add("fade");
                    }, 700);
                    // Redirect sau 1.5s
                    setTimeout(function () {
                        window.location.href = "<c:url value='/pages/OtpForgot'/>";
                    }, 1500);
                </script>
            </c:if>

            <form:input
            type="email"
            class="form-control"
            id="email"
            path="email"
            placeholder="you@example.com"
          />
        </div>
        <div class="mb-4">
          <div class="hint-badge d-flex align-items-start gap-3">
            <div class="hint-icon">i</div>
            <div>
              <div class="fw-semibold mb-1">Hướng dẫn</div>
              <div class="small mb-0">
                Nhập email để nhận liên kết đặt lại mật khẩu.
                <br />
                Kiểm tra cả hộp thư rác nếu không thấy email.
              </div>
            </div>
          </div>
        </div>
        <div class="d-grid">
          <button type="submit" class="btn btn-primary">
            Gửi liên kết đặt lại
          </button>
        </div>
      </form:form>

      <p class="text-center mt-4 mb-0">
        Nhớ lại mật khẩu?
        <a class="helper-link fw-semibold" href="/pages/login">Đăng nhập</a>
      </p>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
