<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <title>Fashion Store | Trang chủ</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap"
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
        background: radial-gradient(circle at 20% 20%, #ffffff, var(--bg-main)),
          linear-gradient(180deg, #ffffff 0%, var(--bg-main) 65%, #ededeb 100%);
        min-height: 100vh;
      }

      .navbar {
        background: rgba(245, 245, 240, 0.9);
        backdrop-filter: blur(12px);
        border-bottom: 1px solid #e0e0dc;
      }

      .navbar-brand {
        font-family: "Playfair Display", serif;
        letter-spacing: 2px;
        font-weight: 700;
        color: var(--text-strong) !important;
      }

      .nav-link {
        color: var(--text-base) !important;
        font-weight: 500;
      }

      .nav-link:hover {
        color: var(--accent) !important;
      }

      .hero {
        padding: 140px 0 120px;
      }

      .hero h1 {
        font-family: "Playfair Display", serif;
        font-size: clamp(2.5rem, 4vw, 3.5rem);
        letter-spacing: 1px;
        color: var(--text-strong);
      }

      .hero .lead {
        color: var(--text-base);
        max-width: 680px;
      }

      .btn-primary {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        border: 1px solid var(--accent-dark);
        padding: 12px 26px;
        border-radius: 999px;
        color: var(--text-strong);
        box-shadow: 0 10px 25px rgba(28, 28, 28, 0.12);
      }

      .btn-primary:hover {
        background: linear-gradient(135deg, var(--accent-dark), #b48b2f);
        color: var(--text-strong);
      }

      .btn-outline-light {
        color: var(--text-base);
        border: 1px solid var(--text-base);
        border-radius: 999px;
        padding: 12px 26px;
      }

      .btn-outline-light:hover {
        background: var(--text-base);
        color: #fff;
      }

      .section-title {
        font-family: "Playfair Display", serif;
        letter-spacing: 1px;
        color: var(--text-strong);
      }

      .glass-card {
        background: #ffffff;
        border: 1px solid var(--card-border);
        border-radius: 16px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        overflow: hidden;
        display: flex;
        flex-direction: column;
      }

      .hero-visual {
        position: relative;
        border-radius: 18px;
        overflow: hidden;
        background: linear-gradient(135deg, #ffffff, #f5f5f0);
        border: 1px solid var(--card-border);
      }

      .media-frame {
        --frame-ratio: 4 / 5;
        position: relative;
        width: 100%;
        aspect-ratio: var(--frame-ratio);
        display: flex;
        align-items: center;
        justify-content: center;
        background: #f9f9f5;
        border-radius: 12px;
        overflow: hidden;
      }

      .media-frame.hero-frame {
        --frame-ratio: 3 / 4;
      }

      .media-frame.wide {
        --frame-ratio: 16 / 9;
      }

      .media-frame img,
      .glass-card img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        display: block;
      }

      .glass-card img {
        border-radius: 0;
      }

      .glass-card .p-3 {
        flex: 1;
      }

      .carousel .media-frame {
        border-radius: 18px;
      }

      .carousel-inner img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        background: #f9f9f5;
      }

      .product-card .media-frame {
        --frame-ratio: 3 / 4;
      }

      .carousel-control-prev,
      .carousel-control-next {
        width: 56px;
        height: 56px;
        top: 50%;
        transform: translateY(-50%);
        opacity: 1;
      }

      .carousel-control-prev,
      .carousel-control-next {
        background: rgba(255, 255, 255, 0.9);
        border: 1px solid var(--card-border);
        border-radius: 50%;
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
      }

      .carousel-control-prev-icon,
      .carousel-control-next-icon {
        filter: invert(1);
      }

      .carousel-control-prev:hover,
      .carousel-control-next:hover {
        background: rgba(255, 255, 255, 1);
      }

      .overlay-gradient {
        position: absolute;
        inset: 0;
        background: linear-gradient(
          180deg,
          rgba(255, 255, 255, 0.2),
          rgba(218, 218, 218, 0.65)
        );
        border-radius: 18px;
        z-index: 2;
        pointer-events: none;
      }

      .hero img {
        position: relative;
        z-index: 1;
      }

      .blur-backdrop {
        background: rgba(255, 255, 255, 0.8);
        border: 1px solid var(--card-border);
        border-radius: 16px;
        color: var(--text-base);
        box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05);
      }

      .footer {
        border-top: 1px solid #e0e0dc;
        color: var(--text-base);
        background: #f0f0ea;
      }

      @media (max-width: 992px) {
        .hero {
          padding: 100px 0 80px;
        }
        .carousel-inner img {
          height: 300px;
        }
      }
    </style>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
      <div class="container">
        <a class="navbar-brand text-uppercase" href="#home">Dreayrt Fashion</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mainNav"
          aria-controls="mainNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div
          class="collapse navbar-collapse d-lg-flex align-items-center w-100"
          id="mainNav"
        >
          <ul class="navbar-nav mx-auto mb-2 mb-lg-0 gap-lg-3 text-center">
            <li class="nav-item">
              <a class="nav-link" href="#home">Trang chủ</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#featured">Trang nổi bật</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#about">Chúng tôi</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/pages/products">Sản phẩm</a>
            </li>
          </ul>
            <c:if test="${sessionScope.user != null}">
                <div class="d-flex align-items-center ms-lg-auto gap-2 gap-lg-3">
                    <img src="<c:url value='/Logo/user.png'/>" alt="User"
                         width="32"
                         height="32"
                         style="cursor:pointer"
                         title="${sessionScope.user.username}">
                    <img src="<c:url value='/Logo/Cart.png'/>" alt="Cart"
                         width="32"
                         height="32"
                         style="cursor:pointer"
                         title="Giỏ hàng">
                    <a class="nav-link mt-2 mt-lg-0 navLog" href="<c:url value='/pages/logout'/>"
                    >Đăng xuất</a
                    >
                </div>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <a class="nav-link ms-lg-auto mt-2 mt-lg-0 navLog" href="<c:url value='/pages/login'/>"
                >Đăng ký/Đăng nhập</a
                >
            </c:if>
        </div>
      </div>
    </nav>

    <section id="home" class="hero container">
      <div class="row align-items-center g-4">
        <div class="col-lg-6">
          <p
            class="text-uppercase fw-semibold small mb-3"
            style="letter-spacing: 4px"
          >
            luxury essentials
          </p>
          <h1 class="mb-4">Tinh hoa thời trang hiện đại</h1>
          <p class="lead mb-4">
            Bộ sưu tập giới hạn kết hợp chất liệu cao cấp, đường nét tinh xảo và
            bảng màu hoàng hôn trầm sang trọng, tôn vinh cá tính và sự tự tin
            của bạn.
          </p>
          <div class="d-flex gap-3">
            <a class="btn btn-primary" href="#products">Khám phá ngay</a>
            <a class="btn btn-outline-light" href="#featured">Xem nổi bật</a>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="hero-visual shadow-lg">
            <div class="media-frame hero-frame mb-0">
              <img src="/imageProduct/ao8.jpg" alt="Featured look" />
            </div>
            <div class="overlay-gradient"></div>
          </div>
        </div>
      </div>
    </section>

    <section id="featured" class="py-5">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <p
              class="text-uppercase small fw-semibold text-muted mb-2"
              style="letter-spacing: 4px"
            >
              highlight
            </p>
            <h2 class="section-title mb-0">Trang nổi bật</h2>
          </div>
        </div>
        <div
          id="featuredCarousel"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner">
            <div class="carousel-item active">
              <div class="media-frame wide">
                <img
                  src="/imageProduct/ao14.jpg"
                  class="d-block w-100"
                  alt="Look 1"
                />
              </div>
            </div>
            <div class="carousel-item">
              <div class="media-frame wide">
                <img
                  src="/imageProduct/ao15.jpg"
                  class="d-block w-100"
                  alt="Look 2"
                />
              </div>
            </div>
            <div class="carousel-item">
              <div class="media-frame wide">
                <img
                  src="/imageProduct/quan12.jpg"
                  class="d-block w-100"
                  alt="Look 3"
                />
              </div>
            </div>
            <div class="carousel-item">
              <div class="media-frame wide">
                <img
                  src="/imageProduct/quan17.jpg"
                  class="d-block w-100"
                  alt="Look 4"
                />
              </div>
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#featuredCarousel"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#featuredCarousel"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </section>

    <section id="about" class="py-5">
      <div class="container">
        <div class="row g-4 align-items-center">
          <div class="col-lg-6">
            <div class="blur-backdrop p-4 p-lg-5 h-100">
              <p
                class="text-uppercase small fw-semibold text-muted mb-2"
                style="letter-spacing: 4px"
              >
                chúng tôi
              </p>
              <h2 class="section-title mb-3">Nghệ thuật từ chất liệu</h2>
              <p class="mb-3">
                Mỗi thiết kế được tạo ra với sự chuẩn xác trong từng đường may,
                lựa chọn chất liệu tinh tuyển và quy trình sản xuất bền vững.
                Chúng tôi hướng tới trải nghiệm cao cấp, vừa sang trọng vừa
                thoải mái.
              </p>
              <div class="row text-muted g-3">
                <div class="col-6">
                  <h4
                    class="fw-semibold mb-0"
                    style="color: var(--text-strong)"
                  >
                    50+
                  </h4>
                  <small>Bản phối giới hạn</small>
                </div>
                <div class="col-6">
                  <h4
                    class="fw-semibold mb-0"
                    style="color: var(--text-strong)"
                  >
                    1000+
                  </h4>
                  <small>Khách hàng hài lòng</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-6">
            <div class="row g-3">
              <div class="col-6">
                <div class="glass-card">
                  <div class="media-frame">
                    <img
                      src="/imageProduct/ao11.jpg"
                      class="w-100"
                      alt="Craft detail"
                    />
                  </div>
                  <div class="p-3">
                    <h6 class="mb-1">Form hoàn hảo</h6>
                    <small class="text-muted">Cắt may tinh xảo, ôm dáng.</small>
                  </div>
                </div>
              </div>
              <div class="col-6">
                <div class="glass-card">
                  <div class="media-frame">
                    <img
                      src="/imageProduct/ao16.jpg"
                      class="w-100"
                      alt="Material detail"
                    />
                  </div>
                  <div class="p-3">
                    <h6 class="mb-1">Chất liệu thượng hạng</h6>
                    <small class="text-muted"
                      >Lụa, cotton hữu cơ, len merino.</small
                    >
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="glass-card">
                  <div class="media-frame wide">
                    <img
                      src="/imageProduct/quan10.jpg"
                      class="w-100"
                      alt="Statement look"
                    />
                  </div>
                  <div class="p-3">
                    <h6 class="mb-1">Phối màu cảm hứng hoàng hôn</h6>
                    <small class="text-muted"
                      >Tone ấm, chiều sâu và tĩnh lặng.</small
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="products" class="py-5">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <p
              class="text-uppercase small fw-semibold text-muted mb-2"
              style="letter-spacing: 4px"
            >
              sản phẩm
            </p>
            <h2 class="section-title mb-0">Chọn lựa tinh tế</h2>
          </div>
          <a class="btn btn-outline-light btn-sm" href="#">Xem tất cả</a>
        </div>
        <div class="row g-4">
          <div class="col-md-6 col-lg-3">
            <div class="glass-card product-card h-100">
              <div class="media-frame">
                <img src="/imageProduct/ao12.jpg" class="w-100" alt="Áo 12" />
              </div>
              <div class="p-3">
                <h6 class="mb-1">Áo lụa dusk</h6>
                <small class="text-muted d-block mb-2"
                  >Gam màu beige - mauve</small
                >
                <div class="d-flex justify-content-between align-items-center">
                  <span class="fw-semibold">1.250.000₫</span>
                  <button class="btn btn-primary btn-sm">Thêm vào giỏ</button>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="glass-card product-card h-100">
              <div class="media-frame">
                <img src="/imageProduct/ao13.jpg" class="w-100" alt="Áo 13" />
              </div>
              <div class="p-3">
                <h6 class="mb-1">Áo satin twilight</h6>
                <small class="text-muted d-block mb-2">Phom rũ, cổ chữ V</small>
                <div class="d-flex justify-content-between align-items-center">
                  <span class="fw-semibold">1.450.000₫</span>
                  <button class="btn btn-primary btn-sm">Thêm vào giỏ</button>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="glass-card product-card h-100">
              <div class="media-frame">
                <img
                  src="/imageProduct/quan15.jpg"
                  class="w-100"
                  alt="Quần 15"
                />
              </div>
              <div class="p-3">
                <h6 class="mb-1">Quần tailored night</h6>
                <small class="text-muted d-block mb-2"
                  >Phom straight, navy sâu</small
                >
                <div class="d-flex justify-content-between align-items-center">
                  <span class="fw-semibold">980.000₫</span>
                  <button class="btn btn-primary btn-sm">Thêm vào giỏ</button>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="glass-card product-card h-100">
              <div class="media-frame">
                <img
                  src="/imageProduct/quan20.jpg"
                  class="w-100"
                  alt="Quần 20"
                />
              </div>
              <div class="p-3">
                <h6 class="mb-1">Quần wool dusk</h6>
                <small class="text-muted d-block mb-2">Sọc mảnh, êm nhẹ</small>
                <div class="d-flex justify-content-between align-items-center">
                  <span class="fw-semibold">1.180.000₫</span>
                  <button class="btn btn-primary btn-sm">Thêm vào giỏ</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <footer class="footer py-4 text-center">
      <div class="container">
        <div class="text-muted">
          © 2026 Dreayrt Fashion. Sang trọng đến từ chi tiết.
        </div>
      </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
