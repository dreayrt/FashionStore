<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Fashion Store | Thêm sản phẩm</title>
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

      .form-card {
        background: rgba(255, 255, 255, 0.92);
        border: 1px solid var(--card-border);
        border-radius: 18px;
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        padding: 22px;
        max-width: 1180px;
        margin: 0 auto;
      }

      .form-grid {
        display: grid;
        gap: 18px;
      }

      .form-layout {
        display: grid;
        grid-template-columns: minmax(360px, 1fr) minmax(320px, 0.9fr);
        gap: 18px;
      }

      .field-stack {
        display: grid;
        gap: 14px;
      }

      .field-pair {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
        gap: 14px;
      }

      .field-row-3 {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 14px;
      }

      .form-section-title {
        font-weight: 700;
        color: var(--text-strong);
        margin-bottom: 12px;
        display: flex;
        align-items: center;
        gap: 8px;
      }

      .form-section-title::before {
        content: "";
        width: 8px;
        height: 8px;
        border-radius: 50%;
        background: var(--accent);
        display: inline-block;
      }

      .form-control,
      .form-select,
      textarea {
        border-radius: 12px;
        border: 1px solid var(--card-border);
        padding: 11px 12px;
        background: #fff;
        transition: border 0.2s ease, box-shadow 0.2s ease;
      }

      .form-control:focus,
      .form-select:focus,
      textarea:focus {
        border-color: var(--accent-dark);
        box-shadow: 0 0 0 0.2rem rgba(201, 162, 77, 0.15);
      }

      textarea {
        min-height: 140px;
        resize: vertical;
      }

      .file-drop {
        border: 1.5px dashed var(--card-border);
        border-radius: 14px;
        padding: 16px;
        background: #fffdfa;
        cursor: pointer;
        transition: all 0.2s ease;
        display: flex;
        align-items: center;
        gap: 12px;
      }

      .file-drop:hover {
        border-color: var(--accent-dark);
        box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05);
        transform: translateY(-1px);
      }

      .file-drop input {
        display: none;
      }

      .file-meta {
        color: var(--text-base);
        font-size: 0.95rem;
      }

      .preview-thumb {
        width: 72px;
        height: 86px;
        border-radius: 12px;
        object-fit: cover;
        border: 1px solid var(--card-border);
        background: #f7f7f2;
      }

      .image-stack {
        display: grid;
        gap: 14px;
      }

      .actions {
        display: flex;
        gap: 12px;
        flex-wrap: wrap;
      }

      .btn-accent {
        background: linear-gradient(135deg, var(--accent), #d6b86b);
        color: var(--text-strong);
        border: 1px solid var(--accent-dark);
        font-weight: 700;
        padding: 12px 16px;
        border-radius: 12px;
      }

      .btn-outline-soft {
        border: 1px solid var(--card-border);
        color: var(--text-strong);
        background: #fff;
        border-radius: 12px;
        padding: 12px 16px;
        font-weight: 600;
      }

      .small-hint {
        color: #777;
        font-size: 0.9rem;
        margin-top: 4px;
      }

      .form-error {
        color: var(--danger);
        font-weight: 600;
        font-size: 0.95rem;
        margin: 6px 0 8px;
        display: block;
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

        .form-layout {
          grid-template-columns: 1fr;
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
            <div class="badge-soft mb-2">Kho hàng</div>
            <h3 class="mb-1" style="color: var(--text-strong)">
              Thêm sản phẩm mới
            </h3>
            <div class="text-muted">
              Nhập thông tin sản phẩm và ảnh trước khi lưu.
            </div>
          </div>
        </div>

        <div class="form-card">

          <form:form
            id="productForm"
            class="form-grid"
            method="post"
            action="/pages/addProducts"
            modelAttribute="AddProductPersisRequest"
            enctype="multipart/form-data"
          >
            <div class="form-section-title">Thông tin sản phẩm</div>

            <div class="form-layout">
              <div class="field-stack">
                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Mã sản phẩm</label
                  >
                  <form:errors
                    path="maSanPham"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:input
                    type="text"
                    class="form-control"
                    path="maSanPham"
                    placeholder="VD: AO123"
                  />
                  <div class="small-hint">
                    Mã duy nhất để quản lý và tìm kiếm.
                  </div>
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Size</label
                  >
                  <form:errors
                    path="size"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:select class="form-select" path="size">
                    <option value="" selected disabled>Chọn size</option>
                    <option value="S">S</option>
                    <option value="M">M</option>
                    <option value="L">L</option>
                    <option value="XL">XL</option>
                    <option value="XXL">XXL</option>
                  </form:select>
                  <div class="small-hint">
                    Có thể chỉnh lại danh sách size cho phù hợp.
                  </div>
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Số lượng nhập kho</label
                  >
                  <form:errors
                    path="soLuong"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:input
                    type="number"
                    step="1"
                    class="form-control"
                    path="soLuong"
                    placeholder="VD: 50"
                  />
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Tag</label
                  >
                  <form:errors
                    path="tag"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:input
                    type="text"
                    class="form-control"
                    path="tag"
                    placeholder="basic, summer, sale..."
                  />
                  <div class="small-hint">
                    Thêm từ khóa để lọc / tìm kiếm nhanh.
                  </div>
                </div>
              </div>

              <div class="field-stack">
                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Tên sản phẩm</label
                  >
                  <form:errors
                    path="tenSanPham"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:input
                    type="text"
                    class="form-control"
                    path="tenSanPham"
                    placeholder="Áo sơ mi linen, quần tây slim..."
                  />
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Giá bán</label
                  >
                  <form:errors
                    path="giaSanPham"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:input
                    type="number"
                    class="form-control"
                    path="giaSanPham"
                    placeholder="VD: 350000"
                  />
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Loại</label
                  >
                  <form:errors
                    path="loai"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:select class="form-select" path="loai">
                    <option value="" selected disabled>Chọn loại</option>
                    <option value="AO">Áo</option>
                    <option value="QUAN">Quần</option>
                    <option value="DAM_VAY">Đầm/Váy</option>
                    <option value="PHU_KIEN">Phụ kiện</option>
                    <option value="KHAC">Khác</option>
                  </form:select>
                </div>

                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Giới tính</label
                  >
                  <form:errors
                    path="gioiTinh"
                    cssClass="form-error"
                    element="div"
                  />
                  <form:select class="form-select" path="gioiTinh">
                    <option value="" selected disabled>Chọn</option>
                    <option value="1">Nam</option>
                    <option value="2">Nữ</option>
                    <option value="3">Unisex</option>
                  </form:select>
                </div>
              </div>
            </div>

            <div>
              <label class="form-label fw-semibold text-uppercase small"
                >Mô tả sản phẩm</label
              >
              <form:errors path="moTa" cssClass="form-error" element="div" />
              <form:textarea
                class="form-control"
                path="moTa"
                placeholder="Chất liệu, form dáng, ưu điểm, hướng dẫn bảo quản..."
              ></form:textarea>
            </div>

            <div class="field-stack">
              <div class="form-section-title">Hình ảnh</div>
              <div class="image-stack">
                <div>
                  <label class="form-label fw-semibold text-uppercase small"
                    >Ảnh chính</label
                  >
                  <form:errors
                    path="anhChinh"
                    cssClass="form-error"
                    element="div"
                  />
                  <label class="file-drop">
                    <form:input type="file" path="anhChinh" accept="image/*" />
                    <img
                      src=""
                      alt="preview"
                      class="preview-thumb"
                      id="previewMain"
                    />
                    <div class="file-meta">
                      <div class="fw-semibold text-dark">
                        Chọn hoặc kéo thả ảnh chính
                      </div>
                      <div class="small-hint mb-0">
                        Tối ưu 800x1000px, định dạng JPG/PNG.
                      </div>
                    </div>
                  </label>
                </div>

                <div class="field-pair">
                  <div>
                    <label class="form-label fw-semibold text-uppercase small"
                      >Ảnh chi tiết 1</label
                    >
                    <form:errors
                      path="anhChiTiet1"
                      cssClass="form-error"
                      element="div"
                    />
                    <label class="file-drop">
                      <form:input
                        type="file"
                        path="anhChiTiet1"
                        accept="image/*"
                      />
                      <img
                        src=""
                        alt="preview"
                        class="preview-thumb"
                        id="previewDetail1"
                      />
                      <div class="file-meta">
                        <div class="fw-semibold text-dark">
                          Thêm ảnh góc cận/chi tiết
                        </div>
                        <div class="small-hint mb-0">
                          Không bắt buộc nhưng nên có.
                        </div>
                      </div>
                    </label>
                  </div>

                  <div>
                    <label class="form-label fw-semibold text-uppercase small"
                      >Ảnh chi tiết 2</label
                    >
                    <form:errors
                      path="anhChiTiet2"
                      cssClass="form-error"
                      element="div"
                    />
                    <label class="file-drop">
                      <form:input
                        type="file"
                        path="anhChiTiet2"
                        accept="image/*"
                      />
                      <img
                        src=""
                        alt="preview"
                        class="preview-thumb"
                        id="previewDetail2"
                      />
                      <div class="file-meta">
                        <div class="fw-semibold text-dark">
                          Ảnh phụ khác (mặt sau, phối đồ)
                        </div>
                        <div class="small-hint mb-0">Tùy chọn.</div>
                      </div>
                    </label>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
                <c:if test="${AddProductPersisRequestFailed !=null}">
                    <div class="col-6">
                        <div class="alert alert-danger">
                                Thao Tác Thất Bại
                        </div>
                    </div>
                </c:if>
                <c:if test="${AddProductPersisRequestSuccess !=null}">
                    <div class="col-6">
                        <div class="alert alert-success">
                            Thao Tác Thành Công
                        </div>
                    </div>
                </c:if>
              <div class="col-6">
                  <div class="actions justify-content-end">
                      <button type="reset" class="btn btn-outline-soft">
                          Làm mới
                      </button>
                      <button type="submit" class="btn btn-accent">
                          Lưu sản phẩm
                      </button>
                  </div>
              </div>
            </div>
          </form:form>
        </div>
      </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      const maInput = document.querySelector('input[name="maSanPham"]');
      const tenInput = document.querySelector('input[name="tenSanPham"]');
      let maLookupTimer = null;

      const setTenEditable = () => {
        if (!tenInput) return;
        tenInput.disabled = false;
        tenInput.classList.remove("bg-light");
      };

      const setTenFromServer = (tenSanPham) => {
        if (!tenInput) return;
        tenInput.value = tenSanPham || "";
        tenInput.readOnly = true;
        tenInput.classList.add("bg-light");
      };

      const doLookup = async () => {
        if (!maInput || !tenInput) return;
        const ma = maInput.value.trim();
        if (!ma) {
          setTenEditable();
          tenInput.value = "";
          return;
        }

        try {
          // Tránh JSP EL trong template literal, dùng cộng chuỗi
          const res = await fetch("/api/products/" + encodeURIComponent(ma));
          if (res.ok) {
            const data = await res.json();
            setTenFromServer(data.tenSanPham);
          } else if (res.status === 404) {
            // Mã chưa tồn tại: cho phép nhập tên mới
            setTenEditable();
            // Không xoá giá trị tên để người dùng tiếp tục gõ nếu đã nhập
          } else {
            setTenEditable();
            console.error("Không kiểm tra được mã sản phẩm", res.status);
          }
        } catch (err) {
          setTenEditable();
          console.error("Lỗi gọi API mã sản phẩm", err);
        }
      };

      if (maInput && tenInput) {
        // Lookup khi nhập (debounce) và khi blur
        maInput.addEventListener("input", () => {
          setTenEditable(); // mở khoá để gõ nếu mã mới
          if (maLookupTimer) clearTimeout(maLookupTimer);
          maLookupTimer = setTimeout(doLookup, 400);
        });

        maInput.addEventListener("blur", () => {
          if (maLookupTimer) clearTimeout(maLookupTimer);
          doLookup();
        });
      }

      const previewMap = {
        anhChinh: "previewMain",
        anhChiTiet1: "previewDetail1",
        anhChiTiet2: "previewDetail2",
      };

      Object.keys(previewMap).forEach((field) => {
        const input = document.querySelector('input[name="' + field + '"]');
        const img = document.getElementById(previewMap[field]);

        if (!input || !img) return;

        input.addEventListener("change", (e) => {
          const file = e.target.files && e.target.files[0];
          if (!file) {
            img.src = "";
            img.style.display = "none";
            return;
          }
          const reader = new FileReader();
          reader.onload = (event) => {
            img.src = event.target?.result || "";
            img.style.display = "block";
          };
          reader.readAsDataURL(file);
        });

        // Ẩn preview trước khi chọn
        img.style.display = "none";
      });
    </script>
  </body>
</html>
