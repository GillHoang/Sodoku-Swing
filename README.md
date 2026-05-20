# Project này chỉ là đồ án, mang tính học thuật

## Cấu trúc hiện tại

- `src/app`: điểm vào ứng dụng Swing (`Main`).
- `src/controller`: điều phối luồng màn hình và game (`AppController`, `GameController`).
- `src/controller/navigation`: điều hướng card layout tập trung (`AppScreen`, `CardNavigator`).
- `src/model`: domain state và luật game (`GameSession`, `GameSnapshot`).
- `src/view`: các interface view để giảm coupling giữa controller và Swing component.
- `src/view/swing`: triển khai Swing cho màn login, chọn level, gameplay.
- `src/view/swing/ending`: triển khai giao diện màn kết thúc.
- `src/common`: các thành phần dùng chung (`common.helpers`, `common.entity`, `common.listeners`).

## TODO
[ ] Khi chọn và ô bất kì thì hightlight vùng 3 *3, hàng và cột tương ứng

[ ] Thêm stats

[ ] Giới hạn 3 lần sai

[ ] Có nút back lại cho phép: chơi lại, chọn chế độ khác

[ ] ....