import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { BsArrowLeftShort } from "react-icons/bs";

import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import { useDispatch } from "react-redux";
import { Link, useLocation } from "react-router-dom";
import Swal from "sweetalert2";
import userApi from "../../api/userApi";
import { deleteDetailUser } from "../../redux/userRedux";

import coursesApi from "../../api/coursesApi";
import { Rating } from "@mui/material";
import ProgressComponent from "../../components/Progress/Progress";
const MyLearning = () => {
  const username = useLocation().pathname.split("/")[2];
  const [userDetail, setUserDetail] = useState();
  //get detail user

  const [listCourseBought, setListCourseBought] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await userApi.get(username);
        const resPurchase = await coursesApi.getlistpurchased(username);
        setUserDetail(res.data);
        setListCourseBought(resPurchase.data.content);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [username]);

  const dispatch = useDispatch();
  const handleLogout = async () => {
    Swal.fire({
      title: "Bạn chắc chắn muốn đăng xuất?",
      denyButtonText: "Hủy",
      showDenyButton: true,
      confirmButtonText: `Đăng xuất`,
    }).then(async (result) => {
      if (result.isConfirmed) {
        await Cookies.remove("token");
        await Cookies.remove("username");
        await dispatch(deleteDetailUser());
        Swal.fire("Đăng xuất thành công!", "", "success");
      }
    });
  };
  return (
    <div className="mylearning">
      <header className="account__header position-sticky top-0">
        <div className="account__header__container container-fluid d-flex justify-content-between">
          <Link to="/">
            <div className="logo d-flex align-items-center">
              <BsArrowLeftShort size={15} cursor={"pointer"} />

              <h2>Trở về trang chủ</h2>
            </div>
          </Link>
        </div>
      </header>
      <div className="container-fluid">
        <div className="row m-0 p-0">
          <Tabs defaultActiveKey="coursebought" className="mb-3 ">
            <Tab
              style={{
                overflow: "auto",
              }}
              eventKey="coursebought"
              title="Khóa học đã mua"
            >
              <div className="row m-0 p-0 justify-content-evenly  align-items-center gap-1">
                {listCourseBought.map((item, index) => {
                  return (
                    <div
                      key={index}
                      className="card col-12 col-md-5  m-0 py-3 d-flex flex-lg-row flex-column  align-items-center card__course-item"
                    >
                      <img
                        src={
                          item.avatar
                            ? item.avatar
                            : require("../../assets/img/no-image-1771002-1505134.png")
                        }
                        className="card-img-top img-fluid mb-2"
                        alt="..."
                      />
                      <div>
                        <div className="card-body">
                          <h5 className="card-title mb-2">{item.name}</h5>

                          <div className="d-flex  gap-1 mb-2">
                            <div className="card-language ">
                              Language: {item ? item.language : ""}
                            </div>
                            <div className="card-language">
                              Số lượng học sinh: {item ? item.numStudents : ""}
                            </div>
                          </div>
                          <div className="d-flex gap-1 align-items-center">
                            <p className="card-text m-0">
                              Giá :{" "}
                              <span>
                                {item.price === 0
                                  ? "Miễn phí"
                                  : item.price.toLocaleString("vi", {
                                      currency: "VND",
                                    }) + " VND"}
                              </span>
                            </p>
                            <Rating
                              name="read-only"
                              value={5}
                              size="small"
                              readOnly
                            />
                          </div>
                          <ProgressComponent value={90} />

                          <div className="card__layer">
                            <div>
                              <Link
                                to={`/courses/${item.id}`}
                                className="btn btn-primary"
                              >
                                Xem khóa học
                              </Link>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </Tab>
            <Tab eventKey="wishlist" title="Khóa học yêu thích">
              Chức năng này chưa hoàn thiện
            </Tab>
          </Tabs>
        </div>
      </div>
    </div>
  );
};

export default MyLearning;
