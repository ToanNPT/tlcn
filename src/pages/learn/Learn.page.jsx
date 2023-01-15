import { Box, CircularProgress, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { BsPlayCircleFill } from "react-icons/bs";
import { MdKeyboardArrowLeft, MdKeyboardArrowRight } from "react-icons/md";
import { useSelector } from "react-redux";
import {
  Link,
  useLocation,
  useNavigate,
  useSearchParams,
} from "react-router-dom";
import chapterApi from "../../api/chapterApi";
import coursesApi from "../../api/coursesApi";
import coursesVideoApi from "../../api/coursesVideoApi";
import Accordion from "react-bootstrap/Accordion";
import BackToTop from "../../components/BackToTop/BackToTop";
import FileViewer from "react-file-viewer";
import { BiNotepad } from "react-icons/bi";
import { Col, Form, Row, Tab, Tabs } from "react-bootstrap";
import { Button } from "react-bootstrap";
import noteApi from "../../api/noteApi";
import { useForm } from "react-hook-form";
import Swal from "sweetalert2";
import { AiOutlineDelete, AiOutlineEdit } from "react-icons/ai";
import { useRef } from "react";
import { formatTime } from "../../utils/MyUtils";
const LearnPage = () => {
  const { currentUser } = useSelector((state) => state.user);
  const [searchParams, setSearchParams] = useSearchParams();
  const location = useLocation();
  const courseId = location.pathname.split("/")[2];
  const [course, setCourse] = useState();
  const [listVideo, setListVideo] = useState([]);
  const [listChapters, setListChapters] = useState([]);
  const nav = useNavigate();

  //Get video course by courseId
  useEffect(() => {
    const getData = async () => {
      try {
        const res = await coursesVideoApi.getByCourseId(courseId);
        const resChapter = await chapterApi.getbycourseId(courseId);
        const resCourse = await coursesApi.get(courseId);
        setListVideo(res.data);
        setListChapters(resChapter.data);
        setCourse(resCourse.data);
      } catch (error) {
        console.log(error);
      }
    };
    getData();
  }, [courseId]);

  // Bài học hiện tại
  const [currentPicker, setCurrenPicker] = useState({
    srcVideo: null,
    title: "Intro Legacy",
    des: "Chào mừng bạn đến với khóa học trên Legacy",
    currentChapter: null,
    currentVideoOfChapter: null,
  });
  // Kiểm tra khóa học đã được đăng ký hay chưa
  const user = useSelector((state) => state.user.currentUser);

  const [wasBought, setWasBought] = useState(false);
  const [isPublicCourse, setIsPublicCourse] = useState(false);
  useEffect(() => {
    const checkRegistered = async () => {
      try {
        const res = await coursesApi.checkisPurchaseCourse(
          courseId,
          user?.username
        );
        const resCourse = await coursesApi.get(courseId);
        setIsPublicCourse(resCourse.data?.public);
        setWasBought(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    checkRegistered();
  }, [courseId, user?.username]);

  //Get video by videoId và get listnote of video
  const [listNote, setListNote] = useState([]);
  //react hook form
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm();
  const fetchDataNote = async () => {
    try {
      const res = await noteApi.getnotebyvideoid(
        currentUser?.username,
        searchParams.get("id")
      );
      console.log("resNote", res.data);
      setListNote(res.data);
    } catch (error) {
      console.log(error);
    }
  };
  const onSubmit = async (data) => {
    data.username = currentUser?.username;
    data.videoId = searchParams.get("id");
    data.atTime = formatTime(videoRef.current.currentTime);
    try {
      const res = await noteApi.addnote(data);
      if (res.errorCode === "") {
        Swal.fire({
          icon: "success",
          title: "Thêm ghi chú thành công",
          showConfirmButton: false,
          timer: 1500,
        });
        await fetchDataNote();
        setValue("content", "");
      } else {
        Swal.fire({
          icon: "error",
          title: "Thêm ghi chú thất bại",
          text: res.message,
          showConfirmButton: false,
          timer: 1500,
        });
      }
    } catch (error) {}
  };

  useEffect(() => {
    const getVideo = async () => {
      try {
        const res = await coursesVideoApi.getbyId(searchParams.get("id"));
        await fetchDataNote();

        setCurrenPicker({
          ...currentPicker,
          srcVideo: res.data?.link,
          title: res.data?.title,
          des: res.data?.description,
          currentChapter: res.data?.chapterId,
          currentVideoOfChapter: res.data?.id,
        });
      } catch (error) {
        console.log(error);
      }
    };
    getVideo();
  }, [searchParams.get("id")]);

  //
  //Handle next video không sử dụng thứ tự của videoID
  const handleNextVideo = () => {
    const currIndex = listVideo.findIndex(
      (item) => item.id == searchParams.get("id")
    );
    const nextVideo = listVideo[currIndex + 1];
    console.log(nextVideo);
    if (nextVideo) {
      setSearchParams({ id: nextVideo.id });
    }
  };
  //Handle prev video
  const handlePrevVideo = () => {
    const currIndex = listVideo.findIndex(
      (item) => item.id == searchParams.get("id")
    );
    const prevVideo = listVideo[currIndex - 1];
    if (prevVideo) {
      setSearchParams({ id: prevVideo.id });
    }
  };

  //Get current time of video
  const videoRef = useRef(null);

  const [currentTime, setCurrentTime] = useState(0);
  const handleChangeTime = (e) => {
    setCurrentTime(Math.floor(e.target.currentTime));
  };

  const handleDeleteNote = async (id) => {
    try {
      const res = await noteApi.deletenotebyid(id);
      if (res.errorCode === "") {
        Swal.fire({
          icon: "success",
          title: "Xóa ghi chú thành công",
          showConfirmButton: false,
          timer: 1500,
        });
        await fetchDataNote();
      } else {
        Swal.fire({
          icon: "error",
          title: "Xóa ghi chú thất bại",
          text: res.message,
          showConfirmButton: false,
          timer: 1500,
        });
      }
    } catch (error) {}
  };

  const listQuestion = [
    {
      id: 1,
      title:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor sit amet consectetur adipisicing elit",
      content:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit.Lorem ipsum dolor sit amet consectetur adipisicing elit.Lorem ipsum dolor sit amet consectetur adipisicing elit.Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod. Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod.",
      date_created: "2021-10-10",
      user: {
        username: "admin",
        name: "Admin",
        avatar: "https://i.pravatar.cc/150?img=1",
      },
      listReply: [
        {
          id: 1,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          content:
            "Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor sit amet consectetur adipisicing elitLorem ipsum dolor sit amet consectetur adipisicing elitQuisquam, quod.",
          date_created: "2021-10-10",
          user: {
            username: "user1",
            name: "User 1",
            avatar: "https://i.pravatar.cc/150?img=1",
          },
        },
        {
          id: 2,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          content:
            "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod.",
          date_created: "2021-10-10",
          user: {
            username: "user2",
            name: "User 2",
            avatar: "https://i.pravatar.cc/150?img=1",
          },
        },
      ],
    },
    {
      id: 2,
      title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
      content:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod. Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod.",
      date_created: "2021-10-10",
      user: {
        username: "admin12",
        name: "Admin 2",
        avatar: "https://i.pravatar.cc/150?img=1",
      },
      listReply: [
        {
          id: 5,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          content:
            "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod.",
          date_created: "2021-10-10",
          user: {
            username: "user5",
            name: "User 5",
            avatar: "https://i.pravatar.cc/150?img=1",
          },
        },
        {
          id: 6,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          content:
            "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quod.",
          date_created: "2021-10-10",
          user: {
            username: "user6",
            name: "User 6",
            avatar: "https://i.pravatar.cc/150?img=1",
          },
        },
      ],
    },
  ];
  return wasBought || isPublicCourse ? (
    <div className="learn">
      <div className="row header__tool d-flex flex-row justify-content-center align-items-center position-sticky">
        <div className="tool__container d-flex flex-row justify-content-between align-items-center gap-4 my-2">
          <span className="d-flex justify-content-between align-items-center">
            <Link
              className="navbar-brand justify-content-center align-items-center d-flex"
              to="/"
              style={{ fontSize: "14px", fontWeight: "bold", color: "#fff" }}
              onClick={() => nav(-1)}
            >
              <MdKeyboardArrowLeft
                cursor="pointer"
                size={30}
                color={"#00693e"}
              />
              <span
                style={{
                  color: "#00693e",
                  fontSize: "14px",
                  fontWeight: "bold",
                }}
              >
                Le
              </span>
              gacy
            </Link>
          </span>
          <div
            className="d-flex flex-row justify-content-center align-items-center gap-1"
            style={{
              color: "#fff",
            }}
          >
            <BiNotepad /> {course?.name}
          </div>
          <span className="d-flex flex-row justify-content-center align-items-center gap-3">
            <Box sx={{ position: "relative", display: "inline-flex" }}>
              <CircularProgress variant="determinate" value={20} />
              <Box
                sx={{
                  top: 0,
                  left: 0,
                  bottom: 0,
                  right: 0,
                  position: "absolute",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                }}
              >
                <Typography variant="caption" component="div" color="#f0f0f0">
                  {`${Math.round(20)}%`}
                </Typography>
              </Box>
            </Box>
            2/10 bài học
          </span>
        </div>
      </div>
      <div className="row my-3 mb-5">
        <div className="col-12 col-lg-9">
          <div className="">
            {!currentPicker.srcVideo ? (
              <div className="d-flex justify-content-center align-items-center flex-column">
                <img
                  src={course?.avatar}
                  alt="anhkhoahoc"
                  style={{ height: "50%" }}
                />
              </div>
            ) : (
              // <iframe
              //   width="100%"
              //   height="500"
              //   src={currentPicker.srcVideo}
              //   title={currentPicker.title}
              //   frameBorder="0"
              //   allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              //   allowFullScreen
              //   className="learn-iframe"
              // ></iframe>
              <video
                id="myVideo"
                className="learn__video"
                width="100%"
                height="500px"
                src={currentPicker.srcVideo}
                controls
                autoPlay
                onTimeUpdate={handleChangeTime}
                ref={videoRef}
              ></video>
              //React file viewer
              // <FileViewer
              //   className="learn__viewer"
              //   fileType={"mp4"}
              //   filePath={currentPicker?.srcVideo}
              //   onError={(e) => console.log(e)}
              // />
            )}

            <Tabs
              defaultActiveKey="overview"
              id="uncontrolled-tab-example"
              className="mb-3"
            >
              <Tab eventKey="overview" title="Tổng quan">
                <h4 className="video__title mt-3 text-left">
                  {currentPicker?.title}
                </h4>
                <p>{currentPicker?.des}</p>
              </Tab>
              <Tab eventKey="note" title="Note">
                {/* Form add note */}
                <div className="note__container">
                  <div className="note__form mb-4">
                    {searchParams.get("id") && (
                      <Form onSubmit={handleSubmit(onSubmit)}>
                        <Row>
                          <Col md={6} xs={12}>
                            <Form.Group className="mb-3">
                              <Form.Label>Thêm ghi chú</Form.Label>

                              <div className="time__video mb-1">
                                {formatTime(currentTime)}
                              </div>
                              <Form.Control
                                as="textarea"
                                rows={3}
                                placeholder="Nhập ghi chú"
                                {...register("content", { required: true })}
                              />
                            </Form.Group>
                            <Button
                              className="main__btn"
                              onClick={handleSubmit(onSubmit)}
                            >
                              Thêm ghi chú
                            </Button>
                          </Col>
                        </Row>
                      </Form>
                    )}
                  </div>
                  <div className=" row note__list">
                    <Form.Label>Danh sách note</Form.Label>
                    {listNote?.map((item, index) => {
                      return (
                        <div className=" note__item mb-2" key={index}>
                          <div className="note__item-header">
                            <span className="note__item-time me-3">
                              {item.atTime}
                            </span>
                            <span className="note__item-delete">
                              <AiOutlineDelete
                                size={20}
                                cursor="pointer"
                                onClick={() => handleDeleteNote(item.id)}
                              />
                            </span>
                          </div>
                          <div className="note__item-content">
                            {item.content}
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </Tab>
              <Tab eventKey="Q&A" title="Hỏi đáp">
                <div className="question__container">
                  <div className="question__form mb-4">
                    <Form>
                      <Row>
                        <Col md={6} xs={12}>
                          <Form.Group className="mb-3">
                            <Form.Label>Thêm câu hỏi</Form.Label>
                            <Form.Control
                              className="mb-3"
                              placeholder="Nhập tiêu đề"
                            ></Form.Control>
                            <Form.Control
                              as="textarea"
                              rows={3}
                              placeholder="Nhập câu hỏi"
                            ></Form.Control>
                          </Form.Group>

                          <Button
                            className="main__btn"
                            // onClick={handleSubmitQuestion(onSubmitQuestion)}
                          >
                            Thêm câu hỏi
                          </Button>
                        </Col>
                      </Row>
                    </Form>
                  </div>
                  <div className=" row question__list">
                    <Form.Label>
                      Danh sách câu hỏi ({listQuestion.length})
                    </Form.Label>
                    {listQuestion?.map((item, index) => {
                      return (
                        <div className=" question__item mb-2 row" key={index}>
                          <div className="question__left col col-12 col-md-2">
                            <div className="question__item-avatar">
                              <img
                                src={require("../../assets/img/avt.jpg")}
                                alt=""
                                className=""
                              />
                            </div>
                            <div className="question__item-name">
                              {item.user.name}
                            </div>
                            <span className="question__item-time me-3">
                              {item.date_created}
                            </span>
                          </div>
                          <div className="question__right col col-12 col-md-10">
                            <div className="question__item-header d-flex w-100">
                              <span className="question__item-title ">
                                {item.title}
                              </span>
                              <div className="question__item-tool d-flex gap-2">
                                <AiOutlineDelete size={24} cursor="pointer" />
                                <AiOutlineEdit size={24} cursor="pointer" />
                              </div>
                            </div>

                            <div className="question__item-content">
                              {item.content}
                            </div>

                            <div className="question__item-des d-flex gap-1"></div>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </Tab>
            </Tabs>
          </div>
        </div>
        <div className="col-12 col-lg-3 ">
          <div className="list__video-container">
            <h5>Nội dung khóa học</h5>

            <div className="list__chapter">
              <Accordion alwaysOpen>
                {listChapters?.map((chapter, index) => {
                  return (
                    <Accordion.Item eventKey={index} key={index}>
                      <Accordion.Header>{chapter.chapterName}</Accordion.Header>
                      <Accordion.Body>
                        <ul className="list__videos">
                          {listVideo?.map((item, index) => {
                            if (item.chapter.id === chapter.id) {
                              return (
                                <li
                                  key={index}
                                  className={
                                    item.id == searchParams.get("id")
                                      ? "video__item active ps-3 d-flex flex-row align-items-center "
                                      : "video__item  ps-3 d-flex flex-row align-items-center "
                                  }
                                >
                                  <p className="video__title m-0">
                                    {item.title}
                                  </p>

                                  <BsPlayCircleFill
                                    className="ms-3 icon__playvideo "
                                    onClick={() => {
                                      setSearchParams(
                                        { id: item.id },
                                        { replace: true }
                                      );
                                    }}
                                  />
                                </li>
                              );
                            }
                          })}
                        </ul>
                      </Accordion.Body>
                    </Accordion.Item>
                  );
                })}
              </Accordion>
            </div>
          </div>
        </div>
      </div>
      <div className="row footer__tool d-flex flex-row justify-content-center align-items-center fixed-bottom">
        <div className="tool__container d-flex flex-row justify-content-center align-items-center gap-4 my-2">
          <span onClick={handlePrevVideo}>
            <MdKeyboardArrowLeft size={30} color={"#00693e"} />
            Bài học trước
          </span>

          <span onClick={handleNextVideo}>
            Bài học kế tiếp
            <MdKeyboardArrowRight size={30} color={"#00693e"} />
          </span>
        </div>
      </div>
    </div>
  ) : (
    <div className="container">Bạn chưa mua khóa học này</div>
  );
};

export default LearnPage;
