<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Cập nhật sản phẩm</title>
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

      .search-box {
        background: rgba(255, 255, 255, 0.9);
        border: 1px solid var(--card-border);
        border-radius: 18px;
        padding: 22px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        margin-bottom: 22px;
      }

      .search-input {
        border-radius: 12px;
        border: 1px solid var(--card-border);
        padding: 12px 16px;
        background: #fff;
        transition: border 0.2s ease, box-shadow 0.2s ease;
        width: 100%;
        font-size: 1rem;
      }

      .search-input:focus {
        border-color: var(--accent-dark);
        box-shadow: 0 0 0 0.2rem rgba(201, 162, 77, 0.15);
        outline: none;
      }

      .products-container {
        background: rgba(255, 255, 255, 0.92);
        border: 1px solid var(--card-border);
        border-radius: 18px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        padding: 22px;
        max-width: 1180px;
        margin: 0 auto;
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
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
        transform: translateY(-2px);
        border-color: var(--accent-dark);
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
        min-width: 200px;
      }

      .product-name {
        font-weight: 600;
        color: var(--text-strong);
        font-size: 1.1rem;
        margin-bottom: 4px;
        word-wrap: break-word;
      }

      .btn-update {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        color: var(--text-strong);
        border: 1px solid var(--accent-dark);
        font-weight: 700;
        padding: 12px 24px;
        border-radius: 12px;
        transition: all 0.2s ease;
        flex-shrink: 0;
        text-decoration: none;
        display: inline-block;
        cursor: pointer;
      }

      .btn-update:hover {
        box-shadow: 0 6px 16px rgba(201, 162, 77, 0.3);
        transform: translateY(-1px);
        color: var(--text-strong);
      }

      .no-products {
        text-align: center;
        padding: 60px 20px;
        color: var(--text-base);
      }

      .no-products-icon {
        font-size: 3rem;
        margin-bottom: 16px;
        opacity: 0.5;
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

        .product-row {
          flex-direction: column;
          align-items: flex-start;
        }

        .product-image {
          width: 100%;
          height: 200px;
        }

        .btn-update {
          width: 100%;
          text-align: center;
        }
      }
    </style>
  </head>
  <body>
    <div class="page-wrap">
      <aside class="sidebar">
        <h5>Kho hàng</h5>
        <a class="nav-btn primary" href="<c:url value='/pages/kho'/>"
          >← Quay lại kho</a
        >
        <a class="nav-btn" href="<c:url value='/'/>">Trang chủ</a>
        <a class="nav-btn" href="<c:url value='/pages/addProducts'/>"
          >+ Thêm sản phẩm</a
        >
        <a class="nav-btn" href="<c:url value='/pages/product/delete'/>"
          >✕ Xóa sản phẩm</a
        >
      </aside>

      <main class="content">
        <div class="header-card">
          <div>
            <div class="badge-soft mb-2">Kho hàng</div>
            <h3 class="mb-1" style="color: var(--text-strong)">
              Cập nhật sản phẩm
            </h3>
            <div class="text-muted">
              Tìm kiếm và cập nhật thông tin sản phẩm.
            </div>
          </div>
        </div>

        <div class="search-box">
          <input
            type="text"
            id="searchBox"
            class="search-input"
            placeholder="Tìm kiếm sản phẩm theo tên hoặc mã sản phẩm..."
          />
        </div>

        <div class="products-container">
          <div id="productsList">
            <!-- Products will be rendered here by JavaScript -->
          </div>
        </div>
          <div class="modal fade" id="updateModal" tabindex="-1">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h5 class="modal-title">Thông Tin Sản Phẩm</h5>
                          <button type="button" class="btn-close" id="closeModal"  data-bs-dismiss="modal"
                                  aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                          <p>Modal body text goes here.</p>
                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                          <button type="button" class="btn btn-primary">Xác Nhận</button>
                      </div>
                  </div>
              </div>
          </div>
      </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const updateModal = new bootstrap.Modal(
            document.getElementById('updateModal')
        );

        document.addEventListener("click", function(e){
            if(e.target.classList.contains("btn-update")){
                updateModal.show();
            }
        });
    </script>
    <script>
      // Khởi tạo productData với xử lý null safety
      <c:choose>
        <c:when test="${empty lstSanPham}">
          var productData = [];
        </c:when>
        <c:otherwise>
          var productData = [
            <c:forEach items="${lstSanPham}" var="p" varStatus="i">
            {
                id: "<c:out value='${p.maSanPham}' default=''/>",
                name: "<c:out value='${p.tenSanPham}' default='Không có tên'/>",
                imageRaw: "<c:out value='${p.anhChinh}' default=''/>"
            }<c:if test="${!i.last}">,</c:if>
            </c:forEach>
          ];
        </c:otherwise>
      </c:choose>

      // Chuẩn hóa dữ liệu và tạo URL ảnh
      const productsData = (productData || []).map((p) => {
        const imageUrl = p.imageRaw && p.imageRaw.trim() !== '' 
          ? '/imageProduct/' + p.imageRaw 
          : '';
        return {
          id: p.id || '',
          name: p.name || 'Không có tên',
          image: imageUrl
        };
      });

      console.log('Loaded products:', productsData.length);
      console.log('Products data:', productsData);

      const productsListEl = document.getElementById("productsList");
      const searchBoxEl = document.getElementById("searchBox");

      console.log('productsListEl found:', !!productsListEl);
      console.log('searchBoxEl found:', !!searchBoxEl);

      if (!productsListEl) {
        console.error('Element productsList not found!');
      }

      function renderProducts(list) {
        console.log('renderProducts called with list length:', list ? list.length : 0);
        
        if (!productsListEl) {
          console.error('Cannot render: productsListEl is null');
          return;
        }
        
        if (!list || list.length === 0) {
          console.log('Rendering empty state');
          productsListEl.innerHTML = `
            <div class="no-products">
              <div class="no-products-icon">🔍</div>
              <div style="font-weight: 600; color: var(--text-strong); margin-bottom: 8px;">
                Không tìm thấy sản phẩm
              </div>
              <div>Thử tìm kiếm với từ khóa khác</div>
            </div>
          `;
          return;
        }

        console.log('Rendering', list.length, 'products');
        
        try {
          // Sử dụng DOM API để tránh vấn đề escape trong template string
          productsListEl.innerHTML = '';
          
          list.forEach((product) => {
            const row = document.createElement('div');
            row.className = 'product-row';
            
            // Tạo ảnh
            const img = document.createElement('img');
            img.src = product.image || '';
            img.alt = product.name || 'Không có tên';
            img.className = 'product-image';
            img.onerror = function() {
              this.src = 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'100\' height=\'120\'%3E%3Crect fill=\'%23f7f7f2\' width=\'100\' height=\'120\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'%23999\' font-size=\'12\'%3ENo Image%3C/text%3E%3C/svg%3E';
            };
            row.appendChild(img);
            
            // Tạo thông tin sản phẩm
            const productInfo = document.createElement('div');
            productInfo.className = 'product-info';
            
            const productName = document.createElement('div');
            productName.className = 'product-name';
            productName.textContent = product.name || 'Không có tên';
            productInfo.appendChild(productName);
            row.appendChild(productInfo);
            
            // Tạo nút cập nhật
            const btn = document.createElement('button');
            btn.className = 'btn-update';
            btn.textContent = 'Cập nhật';
            btn.onclick = function() {
              handleUpdate(product.id || '');
            };
            row.appendChild(btn);
            
            productsListEl.appendChild(row);
          });
          
          console.log('Rendering completed successfully');
          console.log('Total elements rendered:', productsListEl.children.length);
        } catch (error) {
          console.error('Error rendering products:', error);
          console.error('Error details:', error.message, error.stack);
        }
      }

      function applyFilter() {
        if (!searchBoxEl) return;
        
        const keyword = searchBoxEl.value.trim().toLowerCase();
        let list = [...productsData];

        if (keyword) {
          list = list.filter(
            (p) =>
              (p.name && p.name.toLowerCase().includes(keyword)) ||
              (p.id && p.id.toLowerCase().includes(keyword))
          );
        }

        renderProducts(list);
      }

      function handleUpdate(productId) {
        if (!productId) {
          console.warn('Product ID is empty');
          return;
        }
        console.log('Cập nhật sản phẩm:', productId);
        // Ví dụ: window.location.href = '/pages/product/edit?maSanPham=' + encodeURIComponent(productId);
      }

      // Đảm bảo DOM đã sẵn sàng và render ngay
      console.log('Document readyState:', document.readyState);
      
      function init() {
        console.log('Initializing...');
        if (searchBoxEl) {
          searchBoxEl.addEventListener("input", applyFilter);
          console.log('Search box event listener added');
        }
        renderProducts(productsData);
      }

      if (document.readyState === 'loading') {
        console.log('Waiting for DOMContentLoaded...');
        document.addEventListener('DOMContentLoaded', init);
      } else {
        console.log('DOM already ready, initializing immediately');
        init();
      }
    </script>
  </body>
</html>
