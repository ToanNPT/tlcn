import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { BiLogOut } from "react-icons/bi";
import { BsArrowLeftShort } from "react-icons/bs";

import { MdDownload } from "react-icons/md";
import { RiSave3Fill } from "react-icons/ri";
import { useDispatch, useSelector } from "react-redux";
import { Link, useLocation } from "react-router-dom";
import Swal from "sweetalert2";
import userApi from "../../api/userApi";
import { deleteDetailUser } from "../../redux/userRedux";
import "./Account.css";
import AccountChangePass from "./components/AccountChangePass";
import AccountInfo from "./components/AccountInfo";
import ModalRequestBecomeTeacher from "./components/ModalRequestBecomeTeacher";
import ModalUpdateAvatar from "./components/ModalUpdateAvatar";
const Account = () => {
  const { currentUser } = useSelector((state) => state.user);

  const username = useLocation().pathname.split("/")[2];
  const [userDetail, setUserDetail] = useState();
  //get detail user

  //fix tạm cách để render lại avatar khi đổi
  const [reload, setReload] = useState(false);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await userApi.get(username);
        setUserDetail(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchUser();
  }, [username, reload]);

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
  const [showModalRequestBecomeTeacher, setShowModalRequestBecomeTeacher] =
    useState(false);

  const [showModalUpdateAvatar, setShowModalUpdateAvatar] = useState(false);
  return (
    <div className="account">
      {showModalRequestBecomeTeacher && (
        <ModalRequestBecomeTeacher
          userDetail={userDetail}
          show={showModalRequestBecomeTeacher}
          onHide={() => setShowModalRequestBecomeTeacher(false)}
        />
      )}
      {showModalUpdateAvatar && (
        <ModalUpdateAvatar
          reload={reload}
          setReload={setReload}
          userDetail={userDetail}
          show={showModalUpdateAvatar}
          onHide={() => setShowModalUpdateAvatar(false)}
        />
      )}
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
      <div className="container">
        <div className="row">
          <div className="col-12 col-md-6 ">
            <div className="account__left container d-flex  flex-column py-3 sticky-top">
              <div className="account__left__avatar ">
                <img
                  src={
                    userDetail
                      ? userDetail.avatar
                      : require("../../assets/img/avt.jpg")
                  }
                  alt=""
                  className="img-fluid"
                />
              </div>
              <button
                className="account__btn"
                onClick={() => setShowModalUpdateAvatar(true)}
                style={{ width: "fit-content", marginTop: "10px" }}
              >
                <RiSave3Fill className="me-2" /> Cập nhật ảnh đại diện
              </button>
              <div className="account__left__name">
                <h3>
                  {currentUser.role === "TEACHER"
                    ? "Teacher"
                    : currentUser.role === "ROLE_01"
                    ? "Admin"
                    : "Student"}
                  <br /> <span>{userDetail?.fullname}</span>
                </h3>
              </div>
              <div className="account__left__info">
                <p>
                  {/* I'm the Front-End Developer for #Company in Bangladesh, OR. I
                  have serious passion for UI effects, animations and creating
                  intuitive, dynamic user experiences. */}
                </p>
              </div>
              <div className="account__left__button">
                <button className="" onClick={handleLogout}>
                  <BiLogOut className="me-2" /> Đăng xuất
                </button>
                {currentUser?.role !== "TEACHER" &&
                  currentUser?.role !== "ROLE_01" && (
                    <button
                      className=""
                      onClick={() => {
                        setShowModalRequestBecomeTeacher(true);
                      }}
                    >
                      <MdDownload className="me-2" /> Yêu cầu trở thành giảng
                      viên
                    </button>
                  )}
              </div>
            </div>
          </div>
          <div className="col-12 col-md-6 account__right">
            <div className="account__right container py-3">
              <AccountInfo
                userDetail={userDetail}
                setUserDetail={setUserDetail}
              />
              <AccountChangePass />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Account;
