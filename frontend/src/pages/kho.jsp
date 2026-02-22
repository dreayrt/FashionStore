<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Kho hàng</title>
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
        --danger: #c0392b;
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

      .page-wrap {
        min-height: 100vh;
        display: grid;
        grid-template-columns: 280px 1fr;
      }

      .sidebar {
        background: rgba(255, 255, 255, 0.9);
        border-right: 1px solid var(--card-border);
        box-shadow: 12px 0 30px rgba(0, 0, 0, 0.05);
        padding: 28px 22px;
        position: sticky;
        top: 0;
        height: 100vh;
      }

      .sidebar h5 {
        font-family: "Playfair Display", serif;
        color: var(--text-strong);
        letter-spacing: 1px;
        margin-bottom: 18px;
      }

      .nav-btn {
        width: 100%;
        border-radius: 12px;
        padding: 12px 14px;
        border: 1px solid var(--card-border);
        background: #fff;
        color: var(--text-strong);
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 12px;
        transition: all 0.2s ease;
        text-decoration: none;
      }

      .nav-btn:hover {
        border-color: var(--accent-dark);
        color: var(--accent-dark);
        box-shadow: 0 8px 18px rgba(0, 0, 0, 0.05);
        transform: translateY(-2px);
      }

      .nav-btn.primary {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        color: var(--text-strong);
        border: 1px solid var(--accent-dark);
      }

      .content {
        padding: 34px;
      }

      .header-card {
        background: rgba(255, 255, 255, 0.9);
        border: 1px solid var(--card-border);
        border-radius: 18px;
        padding: 22px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        margin-bottom: 22px;
      }

      .search-box {
        border: 1px solid var(--card-border);
        border-radius: 12px;
        padding: 10px 14px;
        background: #ffffff;
        box-shadow: 0 6px 18px rgba(0, 0, 0, 0.04);
        width: 360px;
        max-width: 100%;
        display: flex;
        align-items: center;
        gap: 10px;
      }

      .search-box input {
        border: none;
        outline: none;
        width: 100%;
        font-size: 0.95rem;
      }

      .table-wrap {
        background: rgba(255, 255, 255, 0.92);
        border: 1px solid var(--card-border);
        border-radius: 16px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        padding: 12px;
        overflow: hidden;
      }

      table thead {
        background: #f7f7f2;
      }

      th {
        text-transform: uppercase;
        font-size: 0.85rem;
        letter-spacing: 0.5px;
        color: var(--text-strong);
      }

      td,
      th {
        vertical-align: middle;
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

      .qty-ok {
        color: var(--success);
        font-weight: 700;
      }

      .qty-low {
        color: var(--danger);
        font-weight: 700;
      }

      @media (max-width: 992px) {
        .page-wrap {
          grid-template-columns: 1fr;
        }
        .sidebar {
          position: relative;
          height: auto;
          box-shadow: none;
          border-right: none;
          border-bottom: 1px solid var(--card-border);
          margin-bottom: 16px;
        }
      }
    </style>
  </head>
  <body>
    <div class="page-wrap">
      <aside class="sidebar">
        <h5>Kho hàng</h5>
        <a class="nav-btn primary" href="<c:url value='/'/>"
          >← Quay lại trang chủ</a
        >
        <a class="nav-btn" href="<c:url value='/pages/addProducts'/>"
          >＋ Thêm sản phẩm</a
        >
        <a class="nav-btn" href="<c:url value='/pages/updateProducts'/>"
          >✎ Cập nhật sản phẩm</a
        >
        <a class="nav-btn" href="<c:url value='/pages/deleteProducts'/>"
          >✕ Xóa sản phẩm</a
        >
      </aside>

      <main class="content">
        <div
          class="header-card d-flex flex-wrap align-items-center justify-content-between gap-3"
        >
          <div>
            <div class="badge-soft mb-2">Quản lý kho</div>
            <h3 class="mb-1" style="color: var(--text-strong)">
              Danh sách sản phẩm
            </h3>
            <div class="text-muted">
              Tìm kiếm, xem nhanh tồn kho, giá và size.
            </div>
          </div>
          <div class="search-box">
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
              placeholder="Tìm theo tên, mã, mô tả..."
              aria-label="Tìm kiếm sản phẩm"
            />
          </div>
        </div>

        <div class="table-wrap">
          <div class="table-responsive">
            <table class="table align-middle mb-0">
              <thead>
                <tr>
                    <th scope="col">Mã SP</th>
                    <th scope="col">Ảnh</th>
                  <th scope="col">Tên</th>
                    <th scope="col">Size</th>
                  <th scope="col">Giá</th>
                  <th scope="col">Tồn kho</th>
                  <th scope="col">Người cập nhật</th>
                    <th scope="col">Ngày cập nhật</th>
                </tr>
              </thead>
              <tbody id="stockTableBody"></tbody>
            </table>
          </div>
        </div>
      </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      const stockData = [];
      <c:forEach items="${ListSanPhamKho}" var="p">
      stockData.push({
          id: "${p.sanPhamSize.sanPham.maSanPham}",
          name: "${fn:escapeXml(p.sanPhamSize.sanPham.tenSanPham)}",
          description: "${fn:escapeXml(p.sanPhamSize.sanPham.moTa)}",
          price: Number("${p.sanPhamSize.sanPham.giaSanPham}"),
          quantity: Number("${p.soLuong}"),
          size: "${p.sanPhamSize.size}",
          dateUpdate: "${p.ngayCapNhat}",
          updatedBy: "<c:out value='${p.nguoiCapNhat.username}'/>",
          image: "<c:url value='/imageProduct/${p.sanPhamSize.sanPham.anhChinh}'/>"
      });
      </c:forEach>

      const productsData = stockData.map((p) => ({
        ...p,
        price: Number(p.price || 0),
        quantity: Number(p.quantity || 0),
      }));

      const tableBody = document.getElementById("stockTableBody");
      const searchBox = document.getElementById("searchBox");

      const formatter = new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
        maximumFractionDigits: 0,
      });

      function formatCurrency(value) {
        return formatter.format(value).replace("₫", "đ");
      }

      function render(list) {
        tableBody.innerHTML = "";
        list.forEach((p) => {
          const tr = document.createElement("tr");

          const qtyClass = p.quantity > 5 ? "qty-ok" : "qty-low";

          tr.innerHTML =

            '<td class="fw-semibold">' + p.id + "</td>" +
              '<td>' +
              '<img src="' + p.image + '" alt="' + p.name + '" width="56" height="64" style="object-fit: cover; border-radius: 10px; border: 1px solid var(--card-border);" />' +
              "</td>"+
            '<td>' +
              '<div class="fw-semibold" style="color: var(--text-strong)">' + p.name + "</div>" +
              '<div class="text-muted small">' + (p.description || "Không có mô tả") + "</div>" +
            "</td>" +
              "<td>" + (p.size || "-") + "</td>" +
            '<td class="fw-semibold">' + formatCurrency(p.price) + "</td>" +
            '<td class="' + qtyClass + '">' + p.quantity + "</td>" +
            "<td>" + (p.updatedBy || '-') + "</td>" +
              "<td>" + (p.dateUpdate || '-') + "</td>" ;
          tableBody.appendChild(tr);
        });
      }

      function applyFilter() {
        const keyword = searchBox.value.trim().toLowerCase();
        let list = [...productsData];

        if (keyword) {
          list = list.filter(
            (p) =>
              (p.name && p.name.toLowerCase().includes(keyword)) ||
              (p.description && p.description.toLowerCase().includes(keyword)) ||
              (p.id && p.id.toLowerCase().includes(keyword))
          );
        }

        render(list);
      }

      searchBox.addEventListener("input", applyFilter);
      render(productsData);
    </script>
  </body>
</html>
