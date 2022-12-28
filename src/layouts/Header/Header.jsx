import { Badge, FormControlLabel } from "@mui/material";
import Checkbox from "@mui/material/Checkbox";
import AOS from "aos";
import "aos/dist/aos.css";
import axios from "axios";
import Cookies from "js-cookie";
import { useRef } from "react";
import { useEffect, useState } from "react";
import { Dropdown, Form } from "react-bootstrap";
import { useForm } from "react-hook-form";
import { FaShoppingCart } from "react-icons/fa";
import { MdOutlineContactMail } from "react-icons/md";
import { SiBloglovin } from "react-icons/si";
import { useDispatch, useSelector } from "react-redux";
import { Link, matchPath, useLocation, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import coursesApi from "../../api/coursesApi";
import { handleDeleteFromCart } from "../../redux/cartRedux";
import { deleteDetailUser } from "../../redux/userRedux";
import "./Header.css";
function Header() {
  useEffect(() => {
    AOS.init({
      duration: 1500,
    });
  }, []);

  const { pathname } = useLocation();
  const { listCart, totalQuantity, totalPrice } = useSelector(
    (state) => state.cart
  );
  const user = useSelector((state) => state.user.currentUser);
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    control,
    formState: { errors },
  } = useForm({
    defaultValues: {},
  });
  const [couponCode, setCouponCode] = useState("");
  const [dataPost, setDataPost] = useState({
    username: user.username,
    couponCode: couponCode,
    paymentId: "01",
    orderDetailList: [],
  });

  const [tongTien, setTongTien] = useState(0);
  const handleCheckOut = async () => {
    try {
      const res = await axios.post(
        "http://localhost:8080/api/v1/vnpay/request-pay",
        dataPost
      );
      if (res.data.errorCode === "") {
        const linkRes = res.data.data;
        window.open(linkRes, "_self");
      } else {
        Swal.fire({
          icon: "error",
          title: "Lỗi",
          text: res.data.message,
        });
      }
    } catch (error) {
      console.log(error);
    }
    await console.log("checkout");
  };
  const dispatch = useDispatch();
  const handleSubmitDeleteFromCart = async (courseId) => {
    await handleDeleteFromCart(courseId, user?.username, dispatch);
  };
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

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

  //hàm đổi màu background header khi scroll start
  const navbarColor = document.getElementById("header");
  const logoBrandColor = document.getElementById("logo_brand");
  const logoBrandColor1 = document.getElementById("logo_brand1");
  const navItemColor = document.getElementsByClassName("nav-item");
  const navLinkColorHover = document.getElementsByClassName("nav-link");
  const cartIconColor = document.getElementById("cart__icon");
  const userIconColor = document.getElementById("user__icon");
  const changeBackground = () => {
    if (window.scrollY >= 80) {
      navbarColor?.classList.add("color-nav");
      logoBrandColor?.classList.add("color-logo");
      logoBrandColor1?.classList.add("color-logo1");
      cartIconColor?.classList.add("color-cart-icon");
      userIconColor?.classList.add("color-user-icon");
      //class
      for (let i = 0; i < navItemColor.length; i++) {
        navItemColor[i].classList.add("color-nav-item");
      }
      for (let i = 0; i < navLinkColorHover.length; i++) {
        navLinkColorHover[i].classList.add("color-nav-link");
      }
    } else {
      navbarColor?.classList.remove("color-nav");
      logoBrandColor?.classList.remove("color-logo");
      logoBrandColor1?.classList.remove("color-logo1");
      cartIconColor?.classList.remove("color-cart-icon");
      userIconColor?.classList.remove("color-user-icon");
      //class
      for (let i = 0; i < navItemColor.length; i++) {
        navItemColor[i].classList.remove("color-nav-item");
      }
      for (let i = 0; i < navLinkColorHover.length; i++) {
        navLinkColorHover[i].classList.remove("color-nav-link");
      }
    }
  };
  window.addEventListener("scroll", changeBackground);

  //hàm đổi màu background header khi scroll end

  const nav = useNavigate();
  const [doneSearch, setDoneSearch] = useState("");
  const [listSearchResult, setListSearchResult] = useState([]);
  const handleSearch = async (data) => {
    try {
      const res = await coursesApi.searchCourse(data, 0);
      setListSearchResult(res.data.content);
    } catch (error) {}
  };

  const typingTimeoutRef = useRef(null);
  const handleChangeTextSearch = (e) => {
    //Show kêt quả tìm kiếm khi nhập và ẩn khi không nhập
    const searchResult = document.getElementById("form__search__result");
    if (e.target.value !== "") {
      searchResult?.classList.add("show");
    }
    if (e.target.value === "") {
      searchResult?.classList.remove("show");
    }

    // delay 1s sau khi nhập mới gọi api
    if (typingTimeoutRef.current) {
      clearTimeout(typingTimeoutRef.current);
    }
    typingTimeoutRef.current = setTimeout(() => {
      const formValue = {
        name: {
          key: "name",
          value: e.target.value,
          operation: "MATCH",
        },
      };
      const temp = Object.values(formValue);
      if (e.target.value !== "") {
        handleSearch(temp);
      }
    }, 500);
  };
  return (
    <nav
      className="navbar navbar-expand-lg fixed-top position-sticky "
      id="header"
    >
      <div className="container align-items-center">
        <Link
          className="navbar-brand  justify-content-center align-items-center d-flex"
          to="/"
          id="logo_brand"
        >
          {/* <img
            src={require("../../assets/img/112-book-morph-outline.gif")}
            width={"auto"}
            height={40}
            alt=""
          /> */}
          <span id="logo_brand1" style={{ color: "#00693e" }}>
            Le
          </span>
          gacy
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <i className="bx bx-menu"></i>
        </button>
        <div
          className="collapse navbar-collapse grow"
          id="navbarSupportedContent"
        >
          <ul className="nav__ne navbar-nav me-auto mb-2 mb-lg-0 align-items-center">
            <li className="nav-item ">
              <Link
                className={
                  matchPath("/", pathname) ? "nav-link active" : "nav-link"
                }
                to="/"
              >
                <div className="d-flex flex-column align-items-center">
                  <i className="bx bx-home"></i>
                  <span>Home</span>
                </div>
              </Link>
            </li>
            <li className="nav-item ">
              <Link
                className={
                  matchPath("/courses", pathname)
                    ? "nav-link active"
                    : "nav-link"
                }
                to="/courses"
              >
                <div className="d-flex flex-column align-items-center">
                  <i className="bx bx-store"></i>
                  <span>Courses</span>
                </div>
              </Link>
            </li>
            <li className="nav-item">
              <Link
                className={
                  matchPath("/blog", pathname) ? "nav-link active" : "nav-link"
                }
                to="/blog"
              >
                <div className="d-flex flex-column align-items-center">
                  {/* <i className="bx bxl-blogger"></i> */}
                  <SiBloglovin />
                  <span>Blogs</span>
                </div>
              </Link>
            </li>
            <li className="nav-item">
              <Link
                className={
                  matchPath("/contact", pathname)
                    ? "nav-link active"
                    : "nav-link"
                }
                to="/contact"
              >
                <div className="d-flex flex-column align-items-center">
                  {/* <i className="bx bxs-contact"></i> */}
                  <MdOutlineContactMail />
                  <span>Contact Us</span>
                </div>
              </Link>
            </li>
          </ul>
          <div className="d-flex justify-content-center align-items-center nav__tool-user">
            <form
              className="d-flex align-items-center me-3 form__search"
              id="form__search"
              onSubmit={(e) => {
                e.preventDefault();
              }}
            >
              <input
                className="form-control me-3 nav__search"
                type="search"
                placeholder="Tìm kiếm khóa học"
                aria-label="Search"
                onChange={handleChangeTextSearch}
              />
              <i className="bx bx-search"></i>
              <div className="form__search__result" id="form__search__result">
                <ul className="list-group">
                  {listSearchResult.length > 0 ? (
                    listSearchResult.map((item) => {
                      return (
                        <li
                          className="list-group-item"
                          key={item.id}
                          onClick={() => {
                            nav(`/courses/${item.id}`);
                          }}
                        >
                          {item.name}
                        </li>
                      );
                    })
                  ) : (
                    <li className="list-group-item">Không có kết quả</li>
                  )}
                </ul>
              </div>
            </form>

            {/* <i
              className="bx bx-user-circle me-3"
              id="basic-button"
              aria-controls={open ? "basic-menu" : undefined}
              aria-haspopup="true"
              aria-expanded={open ? "true" : undefined}
              onClick={handleClick}
              style={{ cursor: "pointer" }}
            ></i> */}
            {/* <Menu
              id="basic-menu"
              anchorEl={anchorEl}
              open={open}
              onClose={handleClose}
              MenuListProps={{
                "aria-labelledby": "basic-button",
              }}
            >
              <MenuItem>
                <Link to={`/account/${user.username}`}>Profile</Link>{" "}
              </MenuItem>
              <MenuItem
                onClick={() => {
                  handleClose();
                  handleLogout();
                }}
              >
                Logout
              </MenuItem>
            </Menu> */}

            <Dropdown>
              <Dropdown.Toggle
                id="dropdown-basic"
                style={{
                  backgroundColor: "transparent",
                  border: "none",
                  cursor: "pointer",
                  margin: "0px",
                  padding: "0px",
                }}
              >
                <i
                  className="bx bx-user-circle"
                  id="user__icon"
                  style={{ cursor: "pointer", margin: "0px", padding: "0px" }}
                ></i>
              </Dropdown.Toggle>
              <Dropdown.Menu className="drop__menu">
                <Dropdown.Item onClick={() => nav(`/account/${user.username}`)}>
                  Profile
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => nav(`/mylearning/${user.username}`)}
                >
                  Khóa học của tôi
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    handleClose();
                    handleLogout();
                  }}
                >
                  Đăng xuất
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>

            <Badge badgeContent={listCart.length} showZero color="warning">
              <FaShoppingCart
                data-bs-toggle="offcanvas"
                href="#offcanvasExample"
                role="button"
                aria-controls="offcanvasExample"
                color="#00693e"
                size={20}
                id="cart__icon"
              />
            </Badge>
            {/* Show Cart */}
            <div
              className="offcanvas offcanvas-end"
              tabIndex={-1}
              id="offcanvasExample"
              aria-labelledby="offcanvasExampleLabel"
            >
              <div className="offcanvas-header">
                <h5 className="offcanvas-title" id="offcanvasExampleLabel">
                  Giỏ hàng của bạn
                </h5>
                <button
                  type="button"
                  className="btn-close text-reset"
                  data-bs-dismiss="offcanvas"
                  aria-label="Close"
                />
              </div>
              <div className="offcanvas-body">
                <div className="container">
                  <Form>
                    {listCart?.map((item, index) => {
                      return (
                        <div
                          key={index}
                          className="row cart__item d-flex justify-content-between align-items-center"
                        >
                          <div className="col-3 justify-content-center align-items-center d-flex flex-row">
                            <FormControlLabel
                              key={index}
                              control={
                                <Checkbox
                                  onChange={(e) => {
                                    console.log(e.target.checked);
                                    if (e.target.checked === true) {
                                      setDataPost({
                                        ...dataPost,
                                        orderDetailList: [
                                          ...dataPost.orderDetailList,
                                          `${item.id}`,
                                        ],
                                      });
                                      setTongTien(tongTien + item.price);
                                    } else {
                                      setDataPost({
                                        ...dataPost,
                                        orderDetailList:
                                          dataPost.orderDetailList.filter(
                                            (i) => i !== `${item.id}`
                                          ),
                                      });
                                      setTongTien(tongTien - item.price);
                                    }
                                  }}
                                />
                              }
                            />
                            <img
                              src={require("../../assets/img/backgroundLogin.gif")}
                              alt="product"
                              className="img-fluid cart__item-img"
                            />
                          </div>
                          <div className="col-7 justify-content-center  d-flex flex-column">
                            <div className="cart__item__title">
                              <h5>{item?.name}</h5>
                            </div>
                            <div className="cart__item__price">
                              <p className="m-0">
                                {item?.price.toLocaleString("vi", {
                                  currency: "VND",
                                })}{" "}
                                <span>VNĐ</span>
                              </p>
                            </div>
                          </div>
                          <div className="col-2 cart__item__delete">
                            <i
                              className="bx bx-trash btn__delete__cart"
                              onClick={() =>
                                handleSubmitDeleteFromCart(item?.id)
                              }
                            ></i>
                          </div>
                        </div>
                      );
                    })}
                  </Form>
                  {/* Apply coupon */}
                </div>
              </div>
              <div className="offcanvas-footer px-3 d-flex flex-column justify-content-between align-items-center pb-4 cart__total_price">
                <div className="d-flex justify-content-between align-items-center w-100 mb-3">
                  <input
                    type="text"
                    placeholder="Mã giảm giá"
                    className="form-control"
                    value={couponCode}
                    onChange={(e) => {
                      setCouponCode(e.target.value);
                      setDataPost({
                        ...dataPost,
                        couponCode: e.target.value,
                      });
                    }}
                  />
                </div>
                <div className="d-flex justify-content-between align-items-center w-100">
                  <p className="p-0 m-0">
                    Tổng cộng:{" "}
                    <span className="total__price">
                      {tongTien.toLocaleString("vi", {
                        currency: "VND",
                      })}{" "}
                      VNĐ
                    </span>
                  </p>
                  <button
                    className="main__btn"
                    onClick={handleCheckOut}
                    disabled={
                      dataPost.orderDetailList?.length === 0 ? true : false
                    }
                  >
                    Thanh toán ({dataPost.orderDetailList?.length})
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Header;
