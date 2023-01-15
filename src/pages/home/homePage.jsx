import React from "react";
import Banner from "../../components/Banner/Banner";
import HotCourse from "./HotCourse/NewCourse";
import NewCourse from "./NewCourse/NewCourse";

export default function HomePage() {
  //Hàm hiển thị nút back to top khi scroll

  return (
    <>
      <div className="container-fluid">
        <Banner />
        <div className="container">
          <NewCourse />
          <HotCourse />
          <section className="user__created">
            <div className="user__created__title">
              <h2>Người tạo website</h2>
            </div>
            <div className="row">
              <div className="col-12 col-md-6" data-aos="flip-left">
                <div className="user__created__box p-5">
                  <img
                    src={require("../../assets/img/trung.jpg")}
                    alt=""
                    className="user__avt"
                  />
                  <div className="user__created__info">
                    <h3 className="user__created__name">Nguyễn Ngọc Trung</h3>
                    <p className="user__created__job">Front-end Developer</p>
                    <p className="user__created__desc"></p>
                  </div>
                </div>
              </div>
              <div className="col-12 col-md-6 " data-aos="flip-right">
                <div className="user__created__box p-5">
                  <img
                    src={require("../../assets/img/toan.jpg")}
                    alt=""
                    className="user__avt"
                  />
                  <div className="user__created__info">
                    <h3 className="user__created__name">
                      Nguyễn Phúc Thanh Toàn
                    </h3>
                    <p className="user__created__jobBE">Back-end Developer</p>
                    <p className="user__created__desc"></p>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </div>
      </div>
    </>
  );
}
