import React from "react";
import { useNavigate } from "react-router-dom";

export default function NotFound() {
  const nav = useNavigate();
  return (
    <div className="row notfoundpage">
      <div className="col-12 col-md-6">
        <lottie-player
          src="https://assets8.lottiefiles.com/packages/lf20_yzBQmubL2J.json"
          background="transparent"
          speed="2"
          loop
          autoplay
        ></lottie-player>
      </div>
      <div className="col-12 col-md-6">
        <div className="d-flex justify-content-center align-items-center h-100 flex-column">
          <h2>Page not found</h2>
          <button className="main__btn" onClick={() => nav("/")}>
            Trở về trang chủ
          </button>
        </div>
      </div>
    </div>
  );
}
