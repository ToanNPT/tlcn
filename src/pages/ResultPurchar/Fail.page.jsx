import React from "react";

export default function FailPage(props) {
  return (
    <div className="container failpage d-flex justify-content-center align-items-center ">
      <div className="row">
        <div className="col-12 col-md-6">
          <div className="success__img">
            <lottie-player
              src="https://assets2.lottiefiles.com/packages/lf20_9xRnlw.json"
              background="transparent"
              speed="1"
              loop
              autoplay
            ></lottie-player>
          </div>
        </div>
        <div className="col-12 col-md-6">
          <div className="fail__content d-flex flex-column justify-content-center align-items-center h-100 text-center">
            <h1>Đặt hàng thất bại</h1>
            {/* <h3>Tên khóa học: LEARNING SPRING BOOT</h3>
            <span>Giá: 100.000 VNĐ</span>
            <h4>Học viên: Nguyễn Ngọc Trung</h4>
            <div className=" d-flex gap-3">
              <button className="fail__btn">Về trang chủ</button>
              <button className="fail__btn">Đến khóa học</button>
            </div> */}
          </div>
        </div>
      </div>
    </div>
  );
}
