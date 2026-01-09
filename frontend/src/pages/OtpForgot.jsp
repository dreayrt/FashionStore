<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Xác thực OTP</title>
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

      .otp-grid {
        display: grid;
        grid-template-columns: repeat(6, 1fr);
        gap: 10px;
      }

      .otp-box {
        height: 58px;
        text-align: center;
        font-size: 1.5rem;
        font-weight: 700;
        letter-spacing: 2px;
      }

      .muted {
        color: #7a7a7a;
      }
    </style>
  </head>
  <body>
    <main class="auth-card">
      <div class="d-flex justify-content-between align-items-start mb-3">
        <div>
          <div class="brand">Dreayrt Fashion</div>
          <p class="mb-0 text-muted">Nhập mã OTP để đặt lại mật khẩu</p>
        </div>
        <a class="helper-link small" href="/pages/forgotPassword">← Quay lại</a>
      </div>

      <c:if test="${otpFailed!=null}">
        <div class="alert alert-danger" role="alert">
          ${otpFailed}
        </div>
      </c:if>
      <c:if test="${successMessage!=null}">
        <div class="alert alert-success" role="alert">${successMessage}</div>
          <script>
              // Mờ dần sau 0.7s
              setTimeout(function () {
                  document.getElementById("successAlert").classList.add("fade");
              }, 700);
              // Redirect sau 1.5s
              setTimeout(function () {
                  window.location.href = "<c:url value='/pages/login'/>";
              }, 1500);
          </script>
      </c:if>
      <jsp:useBean
        id="OtpForgotRequest"
        class="com.dreayrt.fashion_store.DTOs.OtpForgotRequest"
        scope="request"
      />
      <form:form
        action="/pages/OtpForgot"
        method="post"
        modelAttribute="OtpForgotRequest"
        class="mt-3"
      >
        <form:hidden path="email" value="${sessionScope.emailForgot}" />
        <div class="mb-3">
          <label class="form-label" for="otp-1">Mã OTP</label>
          <div class="otp-grid">
            <input
              class="form-control otp-box"
              id="otp-1"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
            <input
              class="form-control otp-box"
              id="otp-2"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
            <input
              class="form-control otp-box"
              id="otp-3"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
            <input
              class="form-control otp-box"
              id="otp-4"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
            <input
              class="form-control otp-box"
              id="otp-5"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
            <input
              class="form-control otp-box"
              id="otp-6"
              type="text"
              inputmode="numeric"
              maxlength="1"
              pattern="[0-9]*"
            />
          </div>
          <input type="hidden" name="otp" id="otp-value" />
          <div class="d-flex justify-content-between mt-2">
            <small class="muted">Mã hết hạn sau 5 phút.</small>
            <a class="helper-link small" href="/pages/otpForgot/resend"
              >Gửi lại mã</a
            >
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label" for="new-password">Mật khẩu mới</label>
          <form:errors path="password" cssClass="text-danger" />
          <form:input
            type="password"
            class="form-control"
            id="new-password"
            path="password"
            placeholder="••••••••"
          />
        </div>
        <div class="mb-4">
          <label class="form-label" for="confirm-password"
            >Nhập lại mật khẩu</label
          >
          <form:errors path="ComfirmPassword" cssClass="text-danger" />
          <form:input
            type="password"
            class="form-control"
            id="confirm-password"
            path="ComfirmPassword"
            placeholder="••••••••"
          />
        </div>

        <div class="mb-4">
          <div class="hint-badge d-flex align-items-start gap-3">
            <div class="hint-icon">i</div>
            <div>
              <div class="fw-semibold mb-1">Lưu ý bảo mật</div>
              <div class="small mb-0">
                Không chia sẻ mã OTP cho bất kỳ ai. <br />
                Mật khẩu nên dài tối thiểu 6 ký tự, có chữ hoa, số và ký hiệu.
              </div>
            </div>
          </div>
        </div>

        <div class="d-grid">
          <button type="submit" class="btn btn-primary">
            Xác nhận &amp; đổi mật khẩu
          </button>
        </div>
      </form:form>

      <p class="text-center mt-4 mb-0">
        Nhớ lại mật khẩu?
        <a class="helper-link fw-semibold" href="/pages/login">Đăng nhập</a>
      </p>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      const otpInputs = Array.from(document.querySelectorAll(".otp-box"));
      const otpValue = document.getElementById("otp-value");

      otpInputs.forEach((input, index) => {
        input.addEventListener("input", () => {
          const value = input.value.replace(/[^0-9]/g, "").slice(0, 1);
          input.value = value;
          if (value && index < otpInputs.length - 1) {
            otpInputs[index + 1].focus();
          }
          updateOtp();
        });

        input.addEventListener("keydown", (e) => {
          if (e.key === "Backspace" && !input.value && index > 0) {
            otpInputs[index - 1].focus();
          }
        });
      });

      function updateOtp() {
        otpValue.value = otpInputs.map((el) => el.value).join("");
      }
    </script>
  </body>
</html>
