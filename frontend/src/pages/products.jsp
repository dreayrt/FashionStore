<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Sản phẩm</title>
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
        --success: #1b8f5a;
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

      .banner {
        position: relative;
        overflow: hidden;
        padding: 120px 0 80px;
      }

      .banner::before {
        content: "";
        position: absolute;
        inset: 0;
        background: linear-gradient(
          135deg,
          #ffffff 0%,
          #f4efe4 50%,
          #e9dec6 100%
        );
        z-index: 0;
      }

      .banner .container {
        position: relative;
        z-index: 1;
      }

      .banner-card {
        background: rgba(255, 255, 255, 0.85);
        border: 1px solid var(--card-border);
        border-radius: 18px;
        box-shadow: 0 16px 40px rgba(0, 0, 0, 0.08);
        padding: 32px;
      }

      .banner h1 {
        font-family: "Playfair Display", serif;
        font-size: clamp(2.4rem, 3.4vw, 3.4rem);
        color: var(--text-strong);
        letter-spacing: 1px;
      }

      .banner .lead {
        color: var(--text-base);
        max-width: 680px;
      }

      .badge-soft {
        background: #fff7e6;
        color: var(--accent-dark);
        border: 1px solid #f0e0bc;
        border-radius: 999px;
        padding: 6px 12px;
        font-weight: 600;
        font-size: 0.9rem;
        letter-spacing: 0.5px;
      }

      .product-page {
        margin-top: 20px;
        padding-top: 32px;
        padding-bottom: 80px;
        scroll-margin-top: 120px;
      }

      .filter-card {
        background: #ffffff;
        border: 1px solid var(--card-border);
        border-radius: 16px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        padding: 20px;
        position: sticky;
        top: 90px;
      }

      .filter-card h6 {
        font-weight: 700;
        color: var(--text-strong);
      }

      .filter-card .form-label {
        font-weight: 600;
        color: var(--text-base);
      }

      .product-grid {
        margin-top: 12px;
      }

      .glass-card {
        background: #ffffff;
        border: 1px solid var(--card-border);
        border-radius: 16px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        overflow: hidden;
        transition: transform 0.25s ease, box-shadow 0.25s ease;
        height: 100%;
        display: flex;
        flex-direction: column;
      }

      .glass-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 16px 40px rgba(0, 0, 0, 0.1);
      }

      .product-frame {
        position: relative;
        width: 100%;
        aspect-ratio: 3 / 4;
        background: #f9f9f5;
        overflow: hidden;
      }

      .product-frame img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        transition: opacity 0.4s ease;
      }

      .product-meta {
        padding: 16px 16px 18px;
        flex: 1;
      }

      .product-title {
        font-weight: 700;
        color: var(--text-strong);
        margin-bottom: 6px;
      }

      .product-desc {
        color: #6b6b6b;
        font-size: 0.95rem;
        min-height: 44px;
      }

      .price {
        font-weight: 700;
        color: var(--text-strong);
      }

      .qty {
        color: #5e5e5e;
        font-size: 0.9rem;
      }

      .tag-badge {
        background: #eef2ff;
        color: #4a56c4;
        border-radius: 999px;
        padding: 4px 10px;
        font-size: 0.85rem;
        font-weight: 600;
      }

      .tag-badge.sale {
        background: #fff3e6;
        color: var(--accent-dark);
        border: 1px solid #f0e0bc;
      }

      .tag-badge.trend {
        background: #e7f5ef;
        color: var(--success);
        border: 1px solid #cfeadb;
      }

      .overlay-actions {
        position: absolute;
        inset: 0;
        display: flex;
        align-items: flex-end;
        justify-content: center;
        gap: 10px;
        padding: 18px;
        background: linear-gradient(
          180deg,
          rgba(0, 0, 0, 0) 0%,
          rgba(0, 0, 0, 0.38) 100%
        );
        opacity: 0;
        transform: translateY(8px);
        transition: opacity 0.25s ease, transform 0.25s ease;
      }

      .glass-card:hover .overlay-actions {
        opacity: 1;
        transform: translateY(0);
      }

      .btn-primary {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        border: 1px solid var(--accent-dark);
        color: var(--text-strong);
        border-radius: 12px;
        padding: 10px 16px;
        font-weight: 600;
      }

      .btn-primary:hover {
        background: linear-gradient(135deg, var(--accent-dark), #b48b2f);
        color: var(--text-strong);
      }

      .btn-outline-light {
        color: #fff;
        border: 1px solid #fff;
        border-radius: 12px;
        padding: 10px 16px;
        font-weight: 600;
      }

      .search-box {
        border: 1px solid var(--card-border);
        border-radius: 12px;
        padding: 8px 12px;
        background: #ffffff;
        box-shadow: 0 6px 18px rgba(0, 0, 0, 0.04);
        position: relative;
        z-index: 2;
      }

      .search-box input {
        border: none;
        outline: none;
        width: 260px;
        max-width: 100%;
        font-size: 0.95rem;
      }

      .sort-pill {
        border: 1px solid var(--card-border);
        border-radius: 999px;
        padding: 4px 10px;
        background: #fff;
        cursor: pointer;
      }

      .sort-pill input {
        accent-color: var(--accent);
        margin-right: 6px;
      }

      .range-value {
        font-weight: 700;
        color: var(--accent-dark);
      }

      .helper-text {
        color: #7a7a7a;
        font-size: 0.9rem;
      }

      footer {
        border-top: 1px solid #e0e0dc;
        color: var(--text-base);
        background: #f0f0ea;
      }

      @media (max-width: 992px) {
        .banner {
          padding: 80px 0 40px;
        }
        .product-page {
          margin-top: 0;
          padding-top: 10px;
        }
        .filter-card {
          position: static;
          margin-bottom: 16px;
        }
        .search-box input {
          width: 100%;
        }
      }
    </style>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
      <div class="container">
        <a class="navbar-brand text-uppercase" href="/">Dreayrt Fashion</a>
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
              <a class="nav-link" href="/">Trang chủ</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/#featured">Nổi bật</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/#about">Chúng tôi</a>
            </li>
            <li class="nav-item">
              <a class="nav-link active" href="/pages/products">Sản phẩm</a>
            </li>
          </ul>
          <c:if test="${sessionScope.user != null}">
            <div class="d-flex align-items-center ms-lg-auto gap-2 gap-lg-3">
              <img
                src="<c:url value='/Logo/user.png'/>"
                alt="User"
                width="32"
                height="32"
                style="cursor: pointer"
                title="${sessionScope.user.username}"
              />
              <img
                src="<c:url value='/Logo/Cart.png'/>"
                alt="Cart"
                width="32"
                height="32"
                style="cursor: pointer"
                title="Giỏ hàng"
              />
              <a
                class="nav-link mt-2 mt-lg-0 navLog"
                href="<c:url value='/pages/logout'/>"
                >Đăng xuất</a
              >
            </div>
          </c:if>
          <c:if test="${sessionScope.user == null}">
            <a
              class="nav-link ms-lg-auto mt-2 mt-lg-0 navLog"
              href="<c:url value='/pages/login'/>"
              >Đăng ký/Đăng nhập</a
            >
          </c:if>
        </div>
      </div>
    </nav>

    <section class="banner">
      <div class="container">
        <div class="row align-items-center g-4">
          <div class="col-lg-12">
            <div class="badge-soft mb-3">New season drop</div>
            <h1 class="mb-3">Bộ sưu tập chọn lọc cho bạn</h1>
            <p class="lead mb-4">
              Lọc theo ngân sách, giới tính, ưu đãi và xu hướng chỉ trong một
              chạm. Xem nhanh chi tiết hoặc mua ngay với hiệu ứng xem ảnh xoay
              vòng cho từng sản phẩm.
            </p>
            <div class="d-flex gap-3">
              <a class="btn btn-primary" href="#list">Bắt đầu chọn</a>
              <a class="btn btn-outline-light" href="/#featured">Xem nổi bật</a>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="list" class="product-page">
      <div class="container">
        <div class="row g-4">
          <div class="col-lg-3">
            <div class="filter-card">
              <div
                class="d-flex align-items-center justify-content-between mb-3"
              >
                <h6 class="mb-0">Bộ lọc</h6>
                <span class="helper-text">Tinh chỉnh nhanh</span>
              </div>
              <div class="mb-4">
                <div class="fw-semibold mb-2">Sắp xếp giá</div>
                <div class="d-flex flex-column gap-2">
                  <label class="sort-pill">
                    <input type="radio" name="sortPrice" value="desc" checked />
                    Cao xuống thấp
                  </label>
                  <label class="sort-pill">
                    <input type="radio" name="sortPrice" value="asc" />
                    Thấp lên cao
                  </label>
                </div>
              </div>

              <div class="mb-4">
                <div class="fw-semibold mb-2">Danh mục</div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    value="nam"
                    id="genderMale"
                  />
                  <label class="form-check-label" for="genderMale">Nam</label>
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    value="nu"
                    id="genderFemale"
                  />
                  <label class="form-check-label" for="genderFemale">Nữ</label>
                </div>
              </div>

              <div class="mb-4">
                <div class="fw-semibold mb-2">Tùy chọn</div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    value="sale"
                    id="saleOff"
                  />
                  <label class="form-check-label" for="saleOff">Sale off</label>
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    value="trend"
                    id="trending"
                  />
                  <label class="form-check-label" for="trending"
                    >Xu hướng</label
                  >
                </div>
              </div>

              <div class="mb-1">
                <div
                  class="d-flex justify-content-between align-items-center mb-2"
                >
                  <div class="fw-semibold">Khoảng giá</div>
                  <span class="range-value" id="priceValue">2.000.000đ</span>
                </div>
                <input
                  type="range"
                  class="form-range"
                  min="400000"
                  max="2000000"
                  step="50000"
                  id="priceRange"
                  value="2000000"
                />
                <div class="d-flex justify-content-between helper-text">
                  <span>400.000đ</span>
                  <span>2.000.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-9">
            <div
              class="search-row d-flex flex-wrap justify-content-between align-items-center gap-3 mb-3"
            >
              <div class="search-box d-flex align-items-center gap-2">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="18"
                  fill="currentColor"
                  class="bi bi-search text-muted"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85Zm-5.242 1.4a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11Z"
                  />
                </svg>
                <input
                  type="text"
                  id="searchBox"
                  placeholder="Tìm theo tên, mô tả..."
                />
              </div>
            </div>

            <div class="row g-4 product-grid" id="productGrid"></div>
          </div>
        </div>
      </div>
    </section>

    <footer class="py-4 text-center">
      <div class="container">
        <div class="text-muted">
          © 2026 Dreayrt Fashion. Săn đồ đẹp mỗi ngày.
        </div>
      </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      const productData=[
          <c:forEach items="${sanPhamList}" var="p" varStatus="i">
          {
              id:"${p.maSanPham}",
              name:"${fn:escapeXml(p.tenSanPham)}",
              description:"${fn:escapeXml(p.moTa)}",
              price:Number("${p.giaSanPham}"),
              quantity:Number("${p.soLuong}"),
              size:"${p.size}",
              tags: "${fn:escapeXml(p.tag)}"
                  ? "${fn:escapeXml(p.tag)}".split(",").map(t => t.trim().toLowerCase())
                  : [],
              gender:${p.gioiTinh == null ? 'null' : p.gioiTinh},
              images:{
                  main:
                      [
                          "<c:url value='/imageProduct/${p.anhChinh}'/>",
                      ],
                  detail:[
                      "<c:url value='/imageProduct/${p.anhChiTiet1}'/>",
                      "<c:url value='/imageProduct/${p.anhChiTiet2}'/>"
                  ]
              }

          }${!i.last ? ',' : ''}
          </c:forEach>
      ]

      // Chuẩn hóa dữ liệu sản phẩm để tránh lỗi JS khi render/filter
      const productsData = productData.map((p) => ({
        ...p,
        price: Number(p.price || 0),
        tags: p.tags || [],
        gender: p.gender || null,
        images: p.images || { main: [], detail: [] },
      }));

      
      const productGridEl = document.getElementById("productGrid");
      const searchBoxEl = document.getElementById("searchBox");
      const priceRangeEl = document.getElementById("priceRange");
      const priceValueEl = document.getElementById("priceValue");
      const sortRadios = Array.from(
        document.querySelectorAll("input[name='sortPrice']")
      );
      const genderFilters = [
        document.getElementById("genderMale"),
        document.getElementById("genderFemale"),
      ];
      const saleOffEl = document.getElementById("saleOff");
      const trendingEl = document.getElementById("trending");

      const hoverTimers = new Map();

      const formatter = new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
        maximumFractionDigits: 0,
      });

      function formatCurrency(value) {
        return formatter.format(value).replace("₫", "đ");
      }

      const filters = {
        sort: "desc",
        genders: new Set(),
        sale: false,
        trend: false,
        maxPrice: Number(priceRangeEl.value),
        search: "",
      };

      // Cập nhật trần giá theo dữ liệu thực tế để không lọc hết sản phẩm
      const maxPriceFromData = productsData.length
        ? Math.max(...productsData.map((p) => Number(p.price) || 0))
        : Number(priceRangeEl.value || priceRangeEl.max || 0);
      if (maxPriceFromData > Number(priceRangeEl.max || 0)) {
        priceRangeEl.max = maxPriceFromData;
      }
      filters.maxPrice = maxPriceFromData;
      priceRangeEl.value = maxPriceFromData;
      priceValueEl.textContent = formatCurrency(maxPriceFromData);

      function getAllImages(images) {
        return [...images.main, ...images.detail];
      }

      function createCard(product) {
        const col = document.createElement("div");
        col.className = "col-md-6 col-lg-4";

        const card = document.createElement("div");
        card.className = "glass-card h-100";

        const frame = document.createElement("div");
        frame.className = "product-frame";
        const img = document.createElement("img");
        const imageList = getAllImages(product.images);
        img.src = imageList[0];
        img.alt = product.name;
        frame.appendChild(img);

        if (product.tags.includes("sale")) {
          const badge = document.createElement("div");
          badge.className = "tag-badge sale position-absolute m-3";
          badge.textContent = "Sale off";
          frame.appendChild(badge);
        }

        if (product.tags.includes("trend")) {
          const badge = document.createElement("div");
          badge.className = "tag-badge trend position-absolute m-3";
          badge.style.right = "0";
          badge.textContent = "Xu hướng";
          frame.appendChild(badge);
        }

        const overlay = document.createElement("div");
        overlay.className = "overlay-actions";
        const buyBtn = document.createElement("button");
        buyBtn.className = "btn btn-primary btn-sm";
        buyBtn.textContent = "Mua ngay";
        const detailBtn = document.createElement("button");
        detailBtn.className = "btn btn-outline-light btn-sm";
        detailBtn.textContent = "Xem chi tiết";
        overlay.appendChild(buyBtn);
        overlay.appendChild(detailBtn);
        frame.appendChild(overlay);

        attachHover(frame, img, imageList);

        const meta = document.createElement("div");
        meta.className = "product-meta";

        const titleRow = document.createElement("div");
        titleRow.className =
          "d-flex justify-content-between align-items-start mb-1";
        const title = document.createElement("div");
        title.className = "product-title";
        title.textContent = product.name;
        const price = document.createElement("div");
        price.className = "price";
        price.textContent = formatCurrency(product.price);
        titleRow.append(title, price);

        const desc = document.createElement("p");
        desc.className = "product-desc mb-2";
        desc.textContent = product.description;

        const metaRow = document.createElement("div");
        metaRow.className =
          "d-flex justify-content-between align-items-center mt-2";
        const qty = document.createElement("span");
        qty.className = "qty";
        qty.textContent = "Số lượng: " + product.quantity;

        const tagWrap = document.createElement("div");
        if (product.tags.length === 0) {
          const chip = document.createElement("span");
          chip.className = "tag-badge";
          chip.textContent = "New in";
          tagWrap.appendChild(chip);
        } else {
          product.tags.forEach((tag) => {
            const chip = document.createElement("span");
            chip.className = "tag-badge " + (tag === "sale" ? "sale" : "trend");
            chip.textContent = tag === "sale" ? "Sale off" : "Xu hướng";
            chip.style.marginLeft = "6px";
            tagWrap.appendChild(chip);
          });
        }

        metaRow.append(qty, tagWrap);

        meta.append(titleRow, desc, metaRow);

        card.append(frame, meta);
        col.appendChild(card);
        return col;
      }

      function attachHover(frame, imgEl, imageList) {
        frame.addEventListener("mouseenter", () => {
          let index = 1;
          const timer = setInterval(() => {
            imgEl.src = imageList[index % imageList.length];
            index += 1;
          }, 900);
          hoverTimers.set(frame, timer);
        });

        frame.addEventListener("mouseleave", () => {
          const timer = hoverTimers.get(frame);
          if (timer) {
            clearInterval(timer);
            hoverTimers.delete(frame);
          }
          imgEl.src = imageList[0];
        });
      }

      function applyFilters() {
        let list = [...productsData];

        if (filters.genders.size > 0) {
          list = list.filter((p) => filters.genders.has(p.gender));
        }

        if (filters.sale) {
          list = list.filter((p) => p.tags.includes("sale"));
        }

        if (filters.trend) {
          list = list.filter((p) => p.tags.includes("trend"));
        }

        list = list.filter((p) => p.price <= filters.maxPrice);

        if (filters.search.trim()) {
          const keyword = filters.search.trim().toLowerCase();
          list = list.filter(
            (p) =>
              p.name.toLowerCase().includes(keyword) ||
              p.description.toLowerCase().includes(keyword)
          );
        }

        list.sort((a, b) =>
          filters.sort === "desc" ? b.price - a.price : a.price - b.price
        );

        renderProducts(list);
      }

      function renderProducts(list) {
        productGridEl.innerHTML = "";
        list.forEach((product) => {
          productGridEl.appendChild(createCard(product));
        });
      }

      sortRadios.forEach((radio) => {
        radio.addEventListener("change", () => {
          filters.sort = radio.value;
          applyFilters();
        });
      });

      genderFilters.forEach((checkbox) => {
          checkbox.addEventListener("change", () => {
              filters.genders.clear();

              if (document.getElementById("genderMale").checked) {
                  filters.genders.add(1); // Nam
              }
              if (document.getElementById("genderFemale").checked) {
                  filters.genders.add(2); // Nữ
              }

              applyFilters();
          });
      });

      [saleOffEl, trendingEl].forEach((checkbox) => {
        checkbox.addEventListener("change", () => {
          filters.sale = saleOffEl.checked;
          filters.trend = trendingEl.checked;
          applyFilters();
        });
      });

      priceRangeEl.addEventListener("input", (e) => {
        const value = Number(e.target.value);
        filters.maxPrice = value;
        priceValueEl.textContent = formatCurrency(value);
        applyFilters();
      });

      searchBoxEl.addEventListener("input", (e) => {
        filters.search = e.target.value;
        applyFilters();
      });

      // Khởi tạo
      priceValueEl.textContent = formatCurrency(filters.maxPrice);
      applyFilters();
    </script>
  </body>
</html>
