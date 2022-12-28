import React from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { formatDateDisplay } from "../../utils/MyUtils";

export default function SuccessPage(props) {
  //Lấy dữ liệu từ URL trả về
  // URL: http://localhost:3000/purcharse/success?orderId=05249207&totalPrice=2000000.0&paymentPrice=2000000.0&discountPrice=0.0&orderDate=2022%2F12%2F21&paymentType=PAYPAL&orderInfo=%5B%7B%22courseId%22%3A%22COU010%22%2C%22name%22%3A%22Learn+JavaScript+ES6+and+TypeScript+%22%2C%22author%22%3A%22namtt%22%2C%22price%22%3A2000000.0%7D%5D

  const query = new URLSearchParams(window.location.search);
  const orderId = query.get("orderId");
  const totalPrice = query.get("totalPrice");
  const paymentPrice = query.get("paymentPrice");
  const discountPrice = query.get("discountPrice");
  const orderDate = query.get("orderDate");
  const paymentType = query.get("paymentType");
  const orderInfo = query.get("orderInfo");

  const { currentUser } = useSelector((state) => state.user);
  const temp = JSON.parse(orderInfo);
  console.log("current user:", temp);

  const nav = useNavigate();
  return (
    <div className="container successpage d-flex justify-content-center align-items-center ">
      <div className="row">
        <div className="col-12 col-md-6">
          <div className="success__img">
            {/* <img
              src={require("../../assets/img/backgroundLogin.gif")}
              alt=""
              className="img-fluid"
            /> */}
            <lottie-player
              src="https://assets3.lottiefiles.com/packages/lf20_lc7svuzc.json"
              background="transparent"
              speed="1"
              autoplay
            ></lottie-player>
          </div>
        </div>
        <div className="col-12 col-md-6">
          <div className="success__content d-flex flex-column justify-content-center align-items-center h-100 text-center">
            <h1>Mua khóa học thành công</h1>

            <h3>
              <ul>
                Tên khóa học:{" "}
                {temp.map((item, index) => {
                  return <li key={index}>{index + 1 + " - " + item.name}</li>;
                })}
              </ul>
            </h3>
            <span>Mã đơn hàng: {orderId}</span>

            <span>Ngày mua: {formatDateDisplay(orderDate)}</span>
            <span>
              Tổng tiền:{" "}
              {Number(totalPrice).toLocaleString("vi", {
                currency: "VND",
              })}{" "}
              VNĐ
            </span>

            <span>
              Giảm giá:{" "}
              {Number(discountPrice).toLocaleString("vi", {
                currency: "VND",
              })}{" "}
              VNĐ
            </span>
            <span>
              Tổng số tiền thanh toán:{" "}
              {Number(paymentPrice).toLocaleString("vi", {
                currency: "VND",
              })}{" "}
              VNĐ
            </span>
            <h4>Học viên: {currentUser?.fullname}</h4>
            <div className=" d-flex gap-3">
              <button className="success__btn" onClick={() => nav("/")}>
                Về trang chủ
              </button>
              <button
                className="success__btn"
                onClick={() => nav(`/course/${temp[0]?.courseId}`)}
              >
                Đến khóa học
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
