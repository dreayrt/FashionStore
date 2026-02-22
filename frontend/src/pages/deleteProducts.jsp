<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Fashion Store | Xóa sản phẩm</title>
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
            --text-strong: #1c1c1c;
            --text-base: #4a4a4a;
            --card-border: #e6e6e0;
            --accent: #c9a24d;
            --accent-dark: #a48330;
            --danger: #c0392b;
            --danger-dark: #9f2419;
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
            border: 1px solid var(--accent-dark);
        }

        .content {
            padding: 34px;
        }

        .header-card,
        .search-box,
        .products-container {
            background: rgba(255, 255, 255, 0.92);
            border: 1px solid var(--card-border);
            border-radius: 18px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
            margin-bottom: 22px;
        }

        .header-card,
        .search-box,
        .products-container {
            padding: 22px;
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
            display: inline-block;
        }

        .search-input {
            border-radius: 12px;
            border: 1px solid var(--card-border);
            padding: 12px 16px;
            width: 100%;
            font-size: 1rem;
        }

        .search-input:focus {
            border-color: var(--accent-dark);
            box-shadow: 0 0 0 0.2rem rgba(201, 162, 77, 0.15);
            outline: none;
        }

        .product-row {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 18px;
            border: 1px solid var(--card-border);
            border-radius: 14px;
            background: #fff;
            margin-bottom: 14px;
            transition: all 0.2s ease;
        }

        .product-row:hover {
            border-color: var(--accent-dark);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
            transform: translateY(-2px);
        }

        .product-image {
            width: 100px;
            height: 120px;
            object-fit: cover;
            border-radius: 12px;
            border: 1px solid var(--card-border);
            background: #f7f7f2;
            flex-shrink: 0;
        }

        .product-info {
            flex: 1;
            min-width: 220px;
        }

        .product-name {
            font-weight: 600;
            color: var(--text-strong);
            font-size: 1.1rem;
            margin-bottom: 6px;
        }

        .product-meta {
            font-size: 0.95rem;
            color: #616161;
            margin-bottom: 2px;
        }

        .stock-badge {
            display: inline-flex;
            align-items: center;
            border-radius: 999px;
            border: 1px solid #d9e6d9;
            background: #eef8ee;
            color: #2d7c3f;
            font-weight: 600;
            font-size: 0.85rem;
            padding: 5px 10px;
            margin-top: 8px;
        }

        .btn-delete {
            background: linear-gradient(135deg, var(--danger), #dd4b39);
            color: #fff;
            border: 1px solid var(--danger-dark);
            font-weight: 700;
            padding: 11px 22px;
            border-radius: 12px;
            transition: all 0.2s ease;
            cursor: pointer;
            flex-shrink: 0;
        }

        .btn-delete:hover {
            box-shadow: 0 8px 16px rgba(192, 57, 43, 0.3);
            transform: translateY(-1px);
        }

        .no-products {
            text-align: center;
            padding: 56px 20px;
        }

        .no-products-icon {
            font-size: 2.8rem;
            opacity: 0.5;
            margin-bottom: 10px;
        }

        .delete-note {
            background: #fff4f2;
            border: 1px solid #ffd3cc;
            border-radius: 12px;
            padding: 12px 14px;
            margin-bottom: 16px;
            color: #9f2419;
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
            }

            .product-row {
                flex-direction: column;
                align-items: flex-start;
            }

            .product-image {
                width: 100%;
                height: 220px;
            }

            .btn-delete {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="page-wrap">
    <aside class="sidebar">
        <h5>Kho hàng</h5>
        <a class="nav-btn primary" href="<c:url value='/pages/kho'/>">← Quay lại kho</a>
        <a class="nav-btn" href="<c:url value='/'/>">Trang chủ</a>
        <a class="nav-btn" href="<c:url value='/pages/addProducts'/>">+ Thêm sản phẩm</a>
        <a class="nav-btn" href="<c:url value='/pages/updateProducts'/>">✎ Cập nhật sản phẩm</a>
    </aside>

    <main class="content">
        <div class="header-card">
            <div class="badge-soft mb-2">Kho hàng</div>
            <h3 class="mb-1" style="color: var(--text-strong)">Xóa sản phẩm</h3>
            <div class="text-muted">Hiển thị sản phẩm trong kho và chọn sản phẩm cần xóa.</div>
        </div>

        <div class="search-box">
            <input
                    type="text"
                    id="searchBox"
                    class="search-input"
                    placeholder="Tìm theo tên sản phẩm hoặc mã sản phẩm..."
            />
        </div>

        <div class="products-container">
            <div id="productsList"></div>
        </div>
    </main>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <form method="post" action="<c:url value='/pages/deleteProducts'/>">
            <div class="modal-content border-0 rounded-4 shadow-lg">

                <div class="modal-header">
                    <h5 class="modal-title">Xác nhận xóa sản phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body">
                    <div class="delete-note">
                        Hành Động Này Không Thể Hoàn Tác.
                    </div>

                    <div><strong>Mã sản phẩm:</strong>
                        <span id="deleteProductId">-</span>
                    </div>

                    <div class="mt-1"><strong>Tên sản phẩm:</strong>
                        <span id="deleteProductName">-</span>
                    </div>

                    <div class="mt-1"><strong>Size:</strong>
                        <span id="deleteProductSize">-</span>
                    </div>

                    <input type="hidden" name="maSanPham" id="deleteInputId"/>
                    <input type="hidden" name="size" id="deleteInputSize"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-danger">Xóa sản phẩm</button>
                </div>

            </div>
        </form>
    </div>

</div>
<c:if test="DeleteError != null">
    <div class="alert alert-danger">
        Xóa Sản Phẩm Không Thành Công
    </div>
</c:if>
<c:if test="DeleteSuccess != null">
    <div class="alert alert-success">
        Xóa Sản Phẩm Thành Công
    </div>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    <c:choose>
    <c:when test="${empty lstSize}">
    const productData = [];
    </c:when>
    <c:otherwise>
    const productData = [
        <c:forEach items="${lstSize}" var="s" varStatus="i">
        {
            id: "<c:out value='${s.sanPham.maSanPham}'/>",
            name: "<c:out value='${s.sanPham.tenSanPham}'/>",
            size: "<c:out value='${s.size}'/>",
            soLuong: <c:out value="${s.kho != null ? s.kho.soLuong : 0}" default="0"/>,
            anhChinh: "<c:out value='${s.sanPham.anhChinh}'/>"
        }<c:if test="${!i.last}">, </c:if>
        </c:forEach>
    ];
    </c:otherwise>
    </c:choose>

    function normalizeText(value, fallback) {
        const text = (value || "").toString().trim();
        return text ? text : fallback;
    }

    const productsData = (productData || []).map((p) => ({
        id: normalizeText(p.id, "-"),
        name: normalizeText(p.name, "Không có tên"),
        size: normalizeText(p.size, "-"),
        soLuong: Number.isFinite(Number(p.soLuong)) ? Number(p.soLuong) : 0,
        image: normalizeText(p.anhChinh, "") ? "/imageProduct/" + normalizeText(p.anhChinh, "") : ""
    }));

    const productsListEl = document.getElementById("productsList");
    const searchBoxEl = document.getElementById("searchBox");
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));

    function renderProducts(list) {
        if (!list.length) {
            productsListEl.innerHTML = `
                <div class="no-products">
                    <div class="no-products-icon">🔍</div>
                    <div style="font-weight:600; color: var(--text-strong); margin-bottom: 8px;">Không có sản phẩm phù hợp</div>
                    <div>Thử từ khóa khác để tìm sản phẩm trong kho.</div>
                </div>
            `;
            return;
        }

        productsListEl.innerHTML = "";
        list.forEach((product) => {
            const row = document.createElement("div");
            row.className = "product-row";

            const image = document.createElement("img");
            image.className = "product-image";
            image.src = product.image || "";
            image.alt = product.name;
            image.onerror = function () {
                this.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='120'%3E%3Crect fill='%23f7f7f2' width='100' height='120'/%3E%3Ctext x='50%25' y='50%25' text-anchor='middle' dy='.35em' fill='%23999' font-size='12'%3ENo Image%3C/text%3E%3C/svg%3E";
            };
            row.appendChild(image);

            const info = document.createElement("div");
            info.className = "product-info";

            const productName = document.createElement("div");
            productName.className = "product-name";
            productName.textContent = product.name;

            const productId = document.createElement("div");
            productId.className = "product-meta";
            productId.textContent = "Mã: " + product.id;

            const productSize = document.createElement("div");
            productSize.className = "product-meta";
            productSize.textContent = "Size: " + product.size;

            const stockBadge = document.createElement("span");
            stockBadge.className = "stock-badge";
            stockBadge.textContent = "Tồn kho: " + product.soLuong;

            info.appendChild(productName);
            info.appendChild(productId);
            info.appendChild(productSize);
            info.appendChild(stockBadge);
            row.appendChild(info);

            const btn = document.createElement("button");
            btn.type = "button";
            btn.className = "btn-delete";
            btn.textContent = "Xóa";
            btn.onclick = () => openDeleteModal(product);
            row.appendChild(btn);

            productsListEl.appendChild(row);
        });
    }

    function openDeleteModal(product) {
        document.getElementById("deleteProductId").textContent = product.id;
        document.getElementById("deleteProductName").textContent = product.name;
        document.getElementById("deleteProductSize").textContent = product.size;

        document.getElementById("deleteInputId").value = product.id;
        document.getElementById("deleteInputSize").value = product.size;

        deleteModal.show();
    }

    function applyFilter() {
        const keyword = searchBoxEl.value.trim().toLowerCase();
        const filtered = productsData.filter((p) => {
            return p.name.toLowerCase().includes(keyword) || p.id.toLowerCase().includes(keyword);
        });
        renderProducts(filtered);
    }

    searchBoxEl.addEventListener("input", applyFilter);
    renderProducts(productsData);
</script>
<script>
    setTimeout(function () {
        const alerts = document.querySelectorAll(".alert");
        alerts.forEach(function (alert) {
            alert.style.transition = "opacity 0.5s";
            alert.style.opacity = "0";
            setTimeout(() => alert.remove(), 500);
        });
    }, 3000);
</script>
</body>
</html>

