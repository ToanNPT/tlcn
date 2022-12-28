import React, { useEffect } from "react";
import Box from "@mui/material/Box";
import Slider from "@mui/material/Slider";
import { useState } from "react";
import { ImFilter } from "react-icons/im";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import "./coursesPage.css";
import { Link } from "react-router-dom";
import Rating from "@mui/material/Rating";
import coursesApi from "../../api/coursesApi";
import categoriescoursesApi from "../../api/categoriescoursesApi";
import { useForm } from "react-hook-form";
import { Radio, RadioGroup } from "@mui/material";
import { FaAngleDoubleDown } from "react-icons/fa";
export default function CoursesPage() {
  //Text search
  const [searchText, setSearchText] = useState({
    language: {
      key: "language",
      value: "",
      operation: "EQUAL",
    },
    category: {
      key: "category",
      value: "",
      operation: "EQUAL",
    },
    price: {
      key: "price",
      value: "",
      operation: "BETWEEN",
    },
  });
  // use hook form
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    getValues,
    formState: { errors },
  } = useForm({ searchText });
  var btnLoadmore = document.getElementById("btn__loadmore");

  //Handle search start
  const onSubmit = async (data) => {
    console.log("searchText", searchText);
    console.log("submit");
    if (paginate.page !== 0) {
      setPaginate({
        page: 0,
      });
    }
    data = Object.values(data);
    const temp = Object.values(searchText);
    console.log(data);
    try {
      const res = await coursesApi.searchCourse(temp, paginate.page);
      console.log(res.data);
      setListCourses(res.data.content);
    } catch (error) {
      console.log(error);
    }
  };

  const [priceRange, setPriceRange] = useState([0, 0]);
  const [outOfStock, setOutOfStock] = useState(false);
  const handleChange = (event, newValue) => {
    setPriceRange(newValue);
    setSearchText({
      ...searchText,
      price: {
        key: "price",
        value: newValue.join(","),
        operation: "BETWEEN",
      },
    });
    setValue("price.value", newValue.join(","));
  };
  //Handle search end

  const [paginate, setPaginate] = useState({
    page: 0,
  });

  const [listCourses, setListCourses] = useState([]);
  useEffect(() => {
    const getListCourses = async () => {
      try {
        const res = await coursesApi.searchCourse(
          Object.values(searchText),
          paginate.page
        );
        if (res.data.content.length > 0) {
          setListCourses([...listCourses, ...res.data.content]);
          if (res.data.content.length < 10) {
            setOutOfStock(true);
          }
        } else {
          setOutOfStock(true);
        }
      } catch (error) {
        console.log(error);
      }
    };
    getListCourses();
  }, [paginate]);

  const [listCategories, setListCategories] = useState([]);
  useEffect(() => {
    const paginateCategories = {
      page: 0,
    };
    const fetchData = async () => {
      const data = [];
      var res = { data: [] };
      try {
        do {
          res = await categoriescoursesApi.getAll(paginateCategories.page);
          paginateCategories.page++;
          data.push(...res.data.content);
        } while (res.data.length === 10);
        setListCategories(data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []);
  const listLanguages = [
    {
      id: 1,
      name: "English",
      value: "English",
    },
    {
      id: 2,
      name: "Vietnamese",
      value: "Vietnamese",
    },
  ];

  //toggle full width filter
  let filterCourse = document.getElementById("filter__courses");
  const toggleFilter = () => {
    filterCourse.classList.toggle("show-full");
  };

  const handleResetFilter = async () => {
    const defaultSearchText = {
      language: {
        key: "language",
        value: "",
        operation: "EQUAL",
      },
      category: {
        key: "category",
        value: "",
        operation: "EQUAL",
      },
      price: {
        key: "price",
        value: "",
        operation: "BETWEEN",
      },
    };
    const temp = Object.values(defaultSearchText);
    try {
      const res = await coursesApi.searchCourse(temp, 0);
      console.log(res.data);
      setListCourses(res.data.content);
    } catch (error) {}
  };
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-12 col-lg-2 position-relative">
          {/* <FaAngleDoubleDown
            className=""
            onClick={toggleFilter}
            style={{
              position: "absolute",
              bottom: "0px",
              right: "50%",
              fontSize: "28px",
              zIndex: "1",
              borderRadius: "50%",
              border: "1px solid #000",
              cursor: "pointer",
            }}
          /> */}
          <form
            className="filter__courses  mx-1 my-3"
            id="filter__courses"
            onSubmit={handleSubmit(onSubmit)}
          >
            <h6 className="filter__title mb-3 d-flex align-items-center">
              Bộ lọc tìm kiếm <ImFilter className="ms-1" />
            </h6>
            <Box sx={{ width: "90%", marginBottom: "30px" }}>
              <p className="filter__type ">Lọc theo giá</p>
              <Slider
                sx={{
                  color: "#000",
                  fontSize: "11px",
                }}
                getAriaLabel={() => "Temperature range"}
                value={priceRange}
                onChange={handleChange}
                valueLabelDisplay="auto"
                color="secondary"
                step={100000}
                size="small"
                min={0}
                max={10000000}
              />
              <p className="value__filter">
                {priceRange[0].toLocaleString("vi", {
                  currency: "VND",
                })}{" "}
                -{" "}
                {priceRange[1].toLocaleString("vi", {
                  currency: "VND",
                })}
              </p>
            </Box>

            <Box sx={{ width: "90%", marginBottom: "20px" }}>
              <p className="filter__type ">Lọc theo loại khóa học</p>
              <FormGroup sx={{ fontFamily: "inherit", fontSize: "11px" }}>
                {listCategories.map((item, index) => {
                  return (
                    <FormControlLabel
                      key={index}
                      control={<Checkbox />}
                      label={item.name}
                      onChange={(e) => {
                        const oldValue = watch("category.value");
                        console.log(oldValue);
                        if (e.target.checked) {
                          if (oldValue === undefined) {
                            const newValue = item.id + ",";
                            setValue("category.value", newValue);
                            setSearchText({
                              ...searchText,
                              category: {
                                key: "category",
                                value: newValue,
                                operation: "EQUAL",
                              },
                            });
                          } else {
                            const newValue = oldValue + item.id + ",";
                            setValue("category.value", newValue);
                            setSearchText({
                              ...searchText,
                              category: {
                                key: "category",
                                value: newValue,
                                operation: "EQUAL",
                              },
                            });
                          }
                        } else {
                          const newValue = oldValue.replace(item.id + ",", "");
                          setValue("category.value", newValue);
                          setSearchText({
                            ...searchText,
                            category: {
                              key: "category",
                              value: newValue,
                              operation: "EQUAL",
                            },
                          });
                        }
                      }}
                    />
                  );
                })}
              </FormGroup>
            </Box>
            <Box sx={{ width: "90%", marginBottom: "20px" }}>
              <p className="filter__type ">Lọc theo ngôn ngữ</p>
              <RadioGroup
                className=""
                sx={{ fontFamily: "inherit", fontSize: "11px" }}
                defaultValue="Vietnamese"
              >
                {listLanguages.map((item, index) => {
                  return (
                    <FormControlLabel
                      className="filter__option"
                      key={item.id}
                      value={item.value}
                      control={<Radio />}
                      label={item.name}
                      onChange={(e) => {
                        // setValue("...language.value", item.value);
                        setSearchText({
                          ...searchText,
                          language: {
                            key: "language",
                            value: item.value,
                            operation: "EQUAL",
                          },
                        });
                      }}
                    />
                  );
                })}
              </RadioGroup>
            </Box>
            <Box
              sx={{
                width: "100%",
                marginBottom: "20px",
                display: "flex",
                justifyContent: "space-evenly",
              }}
            >
              <button
                onClick={handleSubmit(onSubmit)}
                className="main__btn "
                style={{}}
              >
                Lọc
              </button>
              <button
                onClick={handleResetFilter}
                className="main__btn "
                style={{}}
                type="button"
              >
                Mặc định
              </button>
            </Box>
          </form>
        </div>
        <div className="col-12 col-lg-10 ">
          <div
            style={{ minHeight: "500px" }}
            className="courses__container mx-1 my-3 "
          >
            <div className="d-flex justify-content-evenly flex-wrap  align-items-center ">
              {listCourses
                ? listCourses?.map((item, index) => {
                    return (
                      <div
                        data-aos="flip-left"
                        key={index}
                        className="card col-12 col-md-5 py-3 d-flex flex-xl-row flex-column align-items-center  card__course-item"
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

                        <div className="card-body w-100 d-flex flex-column align-items-center  align-items-md-start">
                          <h5 className="card-title mb-2 ">{item.name}</h5>
                          <div className="d-flex   mb-2">
                            <div className="card-language me-1">
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
                    );
                  })
                : "Không có khóa học nào"}
            </div>
            <div className="d-flex align-items-center justify-content-center w-100 my-4">
              <button
                className="main__btn  text-center"
                id="btn__loadmore"
                onClick={() => {
                  setPaginate({
                    page: paginate.page + 1,
                  });
                }}
                disabled={outOfStock}
              >
                {outOfStock === false ? "Xem thêm" : "Hết khóa học"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
