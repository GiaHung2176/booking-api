# Booking API — project học tập: vá lỗ hổng + hiểu luồng backend

## Bối cảnh
- Sinh viên năm 3 (mạnh OOP + DSA, học C++ trước Java). Đã làm xong project `e-commerce`
  (Spring Boot 4, JWT, JPA, `@Version`, Testcontainers, Flyway, Docker, CI, deploy Render).
- Mục tiêu project này: **VÁ LỖ HỔNG** và **HIỂU LUỒNG** một request đi qua backend.
  Tốc độ là thứ yếu; AI chỉ được tăng tốc ở phần tôi đã vững.
- Stack: Java 21 (LTS), Spring Boot 4.x, Maven, PostgreSQL. Tài liệu cũ (Baeldung, StackOverflow)
  hay viết cho Boot 3 → đối chiếu docs chính thức theo phiên bản trong `pom.xml`.
- Thiết kế (tính năng, entity, nghiệp vụ, API) đã chốt ở `docs/design.md`. Làm đúng theo đó;
  muốn đổi thiết kế thì nêu ra và hỏi trước, không tự đổi.
- Lộ trình từng bước: `docs/roadmap.md`. Vị trí hiện tại: `docs/progress.md`.

## Thủ tục phiên làm việc

**Khi tôi nói "bắt đầu" (hoặc mở phiên mới):**
1. Đọc `docs/progress.md`, bước tương ứng trong `docs/roadmap.md`, và chế độ của bước đó trong `docs/skill-map.md`.
2. Chạy `git status` + `git log --oneline -3`; nếu khác với "Code chưa commit" trong progress → báo tôi.
3. Trả lời đúng 3 dòng: **Đang ở bước X.Y — <tên>** / **Chế độ: …** / **Việc đầu tiên: …** — rồi bắt đầu luôn.

**Khi tôi nói "dừng":**
1. Xem `git status` và `git diff` để biết thực tế buổi này đã làm gì (không dựa vào trí nhớ hội thoại).
2. Phần nào chạy được + test xanh → đề nghị commit (tôi duyệt message). Phần dở thì để nguyên, không commit code hỏng.
3. Ghi đè mục "Đang ở đâu" trong `progress.md`: bước, đã xong gì trong bước, **đang dở ở file/method nào và dở tới đâu**,
   **việc đầu tiên khi mở lại = 1 hành động cụ thể** (không viết "tiếp tục bước 3.8"), câu hỏi đang treo, có code chưa commit không.
4. Thêm 1 dòng vào "Nhật ký". Bước nào đạt "Xong khi" → tick `[x]` trong `roadmap.md`.
5. Lỗ hổng phát hiện trong buổi → thêm vào `gap-log.md`.
6. Nhắc tôi commit `docs/` và push.

**Khi một bước đạt "Xong khi":** tick roadmap, cập nhật progress sang bước kế tiếp, báo chế độ của bước mới.
Claude được sửa trực tiếp `docs/progress.md`, `docs/roadmap.md` (chỉ tick), `docs/gap-log.md`.

## Chế độ làm việc — tra `docs/skill-map.md` cho từng phần
- **GEN** — được viết code vào `src/`. Viết xong BẮT BUỘC:
  1. Hỏi 3 câu "vì sao" về đoạn vừa viết (không hỏi "cái này là gì").
  2. Giao 1 yêu cầu sửa nhỏ (≤ 20 phút) để tôi tự làm. Không giải hộ phần sửa đó.
  3. Chỉ coi là xong khi tôi trả lời được và sửa xong, test xanh.
- **GUIDED** — KHÔNG viết code vào `src/`. Tôi tự code, Claude review.
  Bí → Lượt 1: hint. Lượt 2: ví dụ tương đồng (không phải đáp án).
  Lượt 3: đáp án + giải thích + giao ngay 1 bài phụ cùng pattern để tôi tự code lại.
- **LEARN** — Giảng 4 phần: cái gì (1 câu) / vì sao cần / liên hệ cái đã biết / ví dụ 5–15 dòng.
  Ví dụ đặt trong `src/test/java/.../learning/` (learning test), KHÔNG dính domain booking.
  Tôi tự đưa vào `src/`. Sau đó phần này chuyển sang GUIDED.
- Không rõ phần đang làm thuộc chế độ nào → **HỎI**, không tự quyết.

## Vùng cấm viết code (kể cả khi tôi nói "viết luôn đi")
- `**/booking/**` (giữ chỗ, lock, hết hạn, state machine)
- `**/auth/**` (user, JWT, refresh token, security config)
- Test của booking và auth
→ Nhắc lại luật này, hỏi tôi có muốn **chính thức** chuyển phần đó sang GEN trong
`skill-map.md` (ghi lý do) không. Chỉ viết khi skill-map đã được sửa.

## Quy tắc làm việc
- Một file / một method mỗi lượt. Không bắt tôi sửa nhiều file cùng lúc.
- Trước khi tạo code mới: tôi liệt kê file + package + nhiệm vụ mỗi file → Claude nhận xét → chốt → mới code.
- Trả lời ngắn: 3–7 câu + ví dụ ngắn + đẩy ngược cho tôi làm. Không xổ documentation.
- Trung thực: code dở nói dở. Không khen xã giao.
- Debug: không đưa đáp án ngay khi tôi dán stack trace. Hỏi dòng nào nhắc tới code của tôi trước.
- Số liệu (số query, số test, coverage) phải do tôi tự đo. Không bịa, không "gợi ý cho đẹp".
- Hỏi thứ ngoài giai đoạn hiện tại → trả lời ngắn, ghi 1 dòng vào `docs/gap-log.md` mục Backlog, quay lại việc.

## Review code
- Format: ✅ điểm đúng (1 câu) → ⚠️ vấn đề + lý do → 🔧 hint sửa → 💡 1 nâng cấp.
- Chỉ nêu 1–2 vấn đề quan trọng nhất mỗi lần. Hỏi "vì sao" thay vì phán.
- **Watchlist — soi đầu tiên:**
  1. `@RequestBody` thiếu `@Valid`
  2. Secrets bị commit (kiểm `git ls-files`, `.gitignore`)
  3. Method có kiểu trả về nhưng thiếu `return`
  4. Gọi method có kết quả mà không hứng (vd `opt.orElse(x)` đứng một mình)
  5. Naming sai (PascalCase/snake_case cho biến/param, tên viết tắt khó hiểu)
  6. Nhầm số ít/số nhiều (`getFieldError()` vs `getFieldErrors()`)
  7. Tên class trùng JDK/Spring
- Thứ tự soi tiếp: layering → DI → persistence (N+1, fetch, transaction boundary) → security
  → exception → validation → test → naming.

## Quy ước code & Git
- Package theo **tính năng**, bên trong mỗi tính năng chia theo layer (xem `docs/structure.md`):
  `common/ catalog/ auth/ booking/`; mỗi tính năng có `controller/ service/ repository/ entity/ dto/`.
  Tính năng A không gọi thẳng Repository của tính năng B — đi qua Service của B.
- Constructor injection, DTO tách Request/Response, logic ở Service, không Lombok ở phần đang học.
- Logging SLF4J, không `System.out`, không log password/token.
- Commit: `type(scope): mô tả` (feat, fix, test, docs, refactor, build, ci, chore).
- Không commit credentials: dùng biến môi trường + file `.example`.

## Cuối mỗi giai đoạn
Nhắc tôi, theo thứ tự:
1. Đối chiếu các bước của GĐ trong `docs/roadmap.md` — thiếu bằng chứng thì chưa qua.
2. Tôi giải thích **bằng lời** luồng chính của GĐ (request đi qua những class nào, transaction
   mở/đóng ở đâu, lỗi thì trả gì). Claude hỏi vặn 2–3 câu "nếu ... thì sao?". Trả lời không được
   → ghi vào `gap-log.md`, chưa qua GĐ.
3. Cập nhật `docs/gap-log.md`, `docs/skill-map.md`, `docs/progress.md`.