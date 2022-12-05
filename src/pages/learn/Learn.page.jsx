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
import coursesApi from "../../api/coursesApi";
import coursesVideoApi from "../../api/coursesVideoApi";

const LearnPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const location = useLocation();
  const courseId = location.pathname.split("/")[2];
  const [listVideo, setListVideo] = useState([]);
  const currentVideoId = listVideo[2]?.id;
  //Get video course by courseId
  useEffect(() => {
    const getVideoCourse = async () => {
      try {
        const res = await coursesVideoApi.getByCourseId(courseId);
        setListVideo(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    getVideoCourse();
  }, [courseId]);
  useEffect(() => {
    if (!searchParams.get("id")) {
      console.log(searchParams.get("id"));
      setSearchParams(
        { id: currentVideoId ? currentVideoId : 1 },
        { replace: true }
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const [srcVideo, setSrcVideo] = useState(
    "https://toannpt-onlinecourses.s3.amazonaws.com/toanpt-C_01-Introduce%20Spring%20boot-How%20to%20learn%20Spring%20Boot%20fast%20and%20deep%3F"
  );
  const nav = useNavigate();
  const [title, setTitle] = useState("");
  const [des, setDes] = useState("");

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
        console.log(resCourse, "hahahah");
        setIsPublicCourse(resCourse.data?.public);
        setWasBought(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    checkRegistered();
  }, [courseId, user?.username]);

  //Get video by videoId
  useEffect(() => {
    const getVideo = async () => {
      try {
        const res = await coursesVideoApi.getById(searchParams.get("id"));
        setSrcVideo(res.data.video);
        setTitle(res.data.title);
        setDes(res.data.description);
        console.log("A");
      } catch (error) {
        console.log(error);
      }
    };
    getVideo();
  }, [searchParams.get("id")]);
  //Handle next video
  const handleNextVideo = () => {
    const nextVideo = listVideo.find((item) => item.id > currentVideoId);
    if (nextVideo) {
      nav(`/learn/${courseId}?id=${nextVideo.id}`);
    }
  };
  //Handle prev video
  const handlePrevVideo = () => {
    const prevVideo = listVideo.find((item) => item.id < currentVideoId);
    if (prevVideo) {
      nav(`/learn/${courseId}?id=${prevVideo.id}`);
    }
  };
  return wasBought || isPublicCourse ? (
    <div>
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
                color={"#005fb7"}
              />
              <span
                style={{
                  color: "#005fb7",
                  fontSize: "14px",
                  fontWeight: "bold",
                }}
              >
                Le
              </span>
              gacy
            </Link>
          </span>

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
      <div className="row my-3 ">
        <div className="col-12 col-lg-9">
          <div>
            <iframe
              width="100%"
              height="530"
              src={srcVideo}
              title="[Engsub/Vietsub] 清空 (Qing Kong) || Thanh Trừ - Vương Hân Thần & Tô Tinh Tiệp) || Original & Remix"
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
            <h4 className="video__title mt-3 text-left">{title}</h4>
            <p>{des}</p>
          </div>
        </div>
        <div className="col-12 col-lg-3 ">
          <div className="list__video-container">
            <h5>Nội dung khóa học</h5>
            <ul className="list__videos">
              {listVideo?.map((item, index) => (
                <li
                  key={index}
                  className="video__item ps-3 d-flex flex-row align-items-center "
                >
                  <p className="video__title m-0">{item.title}</p>
                  <BsPlayCircleFill
                    className="ms-3 icon__playvideo "
                    onClick={() => {
                      setSrcVideo(item.link);
                      setTitle(item.title);
                      setSearchParams({ id: item.id }, { replace: true });
                      setDes(item.description);
                      // Thay đổi id trong url sẽ gọi lại useEffect call api lấy dữ liệu của video bài giảng và không cần set 2 state ở trên
                    }}
                  />
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      <div className="row footer__tool d-flex flex-row justify-content-center align-items-center fixed-bottom">
        <div className="tool__container d-flex flex-row justify-content-center align-items-center gap-4 my-2">
          <span onClick={handleNextVideo}>
            <MdKeyboardArrowLeft size={30} color={"#e6c511"} />
            Bài học trước
          </span>

          <span onClick={handlePrevVideo}>
            Bài học kế tiếp
            <MdKeyboardArrowRight size={30} color={"#e6c511"} />
          </span>
        </div>
      </div>
    </div>
  ) : (
    <div className="container">Bạn chưa mua khóa học này</div>
  );
};

export default LearnPage;
