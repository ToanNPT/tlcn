import React from "react";
import TextField from "@mui/material/TextField";
import { Button, Col, Form, Row } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { Controller, useForm } from "react-hook-form";
import CustomDatePicker from "../../../components/DatePicker/DatePicker.component";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import registerApi from "../../../api/registerApi";
import Swal from "sweetalert2";
export default function SignUp() {
  // React hook form
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    control,
    formState: { errors },
  } = useForm({
    defaultValues: { gender: "nam" },
  });
  const nav = useNavigate();
  const onSubmit = async (data) => {
    //config birthdate
    data.birthdate = new Date(data.birthdate).toISOString().slice(0, 10);
    const formData = new FormData();
    Object.keys(data).forEach((key) => {
      if (data[key] !== undefined && data[key] !== null) {
        formData.append(key, data[key]);
      }
    });

    const res = await registerApi.register(formData);
    if (res.errorCode === "") {
      Swal.fire({
        icon: "success",
        title: "Đăng ký thành công",
        showConfirmButton: false,
        timer: 1500,
      });
      nav("/signin");
    } else {
      Swal.fire({
        icon: "error",
        title: "Đăng ký thất bại",
        text: res.message,
        showConfirmButton: false,
        timer: 1500,
      });
    }
  };

  return (
    <div className="row m-0 signin__page d-flex justify-content-center align-items-center">
      <div className="col-12 signin__page-inner d-flex  justify-content-center align-items-center">
        <div className="col-12 col-md-6 d-flex  flex-column justify-content-center align-items-center">
          <h1 className="login__title mb-5 text-center">ĐĂNG KÍ</h1>
          <Form
            onSubmit={handleSubmit(onSubmit)}
            className="form__login d-flex flex-column gap-3 align-items-center"
          >
            <Row>
              <Col xs={12} md={6}>
                <TextField
                  label="Họ tên"
                  className="w-100 mb-3"
                  {...register("fullname")}
                />
                <TextField
                  label="Email"
                  className="w-100 mb-3"
                  {...register("email")}
                />
                <TextField
                  label="Số điện thoại"
                  className="w-100 mb-3"
                  {...register("phone")}
                />

                <TextField
                  label="Tên đăng nhập/ Email"
                  className="w-100 mb-3"
                  {...register("username")}
                />
              </Col>
              <Col xs={12} md={6}>
                <TextField
                  label="Mật khẩu"
                  type="password"
                  className="w-100 mb-3"
                  {...register("password")}
                />

                {/* <TextField
                  label="Nhập lại mật khẩu"
                  type="password"
                  className="w-100 mb-3"
                  {...register("repassword")}
                /> */}

                <FormControl fullWidth className="mb-3">
                  <InputLabel id="demo-simple-select-label">
                    Giới tính
                  </InputLabel>
                  <Controller
                    name="gender"
                    control={control}
                    render={({ field }) => (
                      <Select
                        labelId="demo-simple-select-label"
                        label="Giới tính"
                        {...field}
                      >
                        <MenuItem value={"nam"}>Nam</MenuItem>
                        <MenuItem value={"nu"}>Nữ</MenuItem>
                      </Select>
                    )}
                  />
                </FormControl>
                <Form.Group className="mb-3 w-100">
                  <CustomDatePicker control={control} field={"birthdate"} />
                </Form.Group>
              </Col>
            </Row>

            <div className="d-flex flex-row w-100 justify-content-between align-items-center">
              <Link className="link_to_signup" to="/signin">
                Bạn đã có tài khoản? Đăng nhập ngay
              </Link>
            </div>
            <Button
              onClick={handleSubmit(onSubmit)}
              className="btn_login mt-3"
              variant="outlined"
            >
              Đăng kí
            </Button>
          </Form>
        </div>
        <div className="col-12 col-md-6 d-none d-md-block ">
          <lottie-player
            src="https://assets3.lottiefiles.com/private_files/lf30_sxw84pnl.json"
            background="transparent"
            speed="1"
            loop
            autoplay
          ></lottie-player>
        </div>
      </div>
    </div>
  );
}
